package io.github.amayaframework;

import io.github.amayaframework.core.actions.InputStage;
import io.github.amayaframework.core.configurators.Configurator;
import io.github.amayaframework.core.configurators.ConfiguratorWrapper;
import io.github.amayaframework.core.configurators.DirectAccess;
import io.github.amayaframework.core.configurators.InsertPolicy;
import io.github.amayaframework.core.controllers.Controller;
import io.github.amayaframework.core.handlers.EventManager;
import io.github.amayaframework.core.handlers.PipelineHandler;
import io.github.amayaframework.core.pipeline.NamedPipeline;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConfiguratorTest extends Assertions {
    private static PipelineHandler handler;

    public static void createHandler(Configurator configurator) throws Throwable {
        PipelineHandler handler = new PipelineHandler(false, new EventManager());
        handler.getInput().put(InputStage.INVOKE_CONTROLLER, (Integer e) -> e * 5);
        handler.getOutput().put("mul", (Integer e) -> e * 2);
        ConfiguratorWrapper wrapper = new ConfiguratorWrapper(configurator);
        wrapper.configure(handler, null);
        ConfiguratorTest.handler = handler;
    }

    @Test
    public void testInput() throws Throwable {
        TestConf conf = new TestConf();
        TestConf1 conf1 = new TestConf1();
        createHandler(conf);
        assertEquals(handler.getInput().execute(0), 10);
        createHandler(conf1);
        assertEquals(handler.getInput().execute(0), 2);
    }

    @Test
    public void testOutput() throws Throwable {
        TestConf conf = new TestConf();
        TestConf1 conf1 = new TestConf1();
        createHandler(conf);
        assertEquals(handler.getOutput().execute(0), 2);
        createHandler(conf1);
        assertEquals(handler.getOutput().execute(0), 1);
    }

    @Test
    public void testPackStrategy() throws Throwable {
        NamedPipeline pipeline = new NamedPipeline();
        pipeline.put("a", (Integer e) -> e + 1);
        pipeline.put("b", (Integer e) -> e + 1);
        pipeline.put("c", (Integer e) -> e + 1);
        NamedPipeline p = InsertPolicy.PACK.execute(pipeline);
        p.put("a", (Integer e) -> e * 2);
        assertAll(
                () -> assertEquals(6, p.execute(0)),
                () -> assertEquals(2, p.size())
        );
    }

    @Test
    public void testPlainStrategy() {
        NamedPipeline pipeline = new NamedPipeline();
        pipeline.put("a", (Integer e) -> e + 1);
        pipeline.put("b", (Integer e) -> e + 1);
        pipeline.put("c", (Integer e) -> e + 1);
        assertAll(
                () -> assertEquals(3, pipeline.execute(0)),
                () -> assertEquals(3, pipeline.size())
        );
    }

    @Test
    public void testUncloseStrategy() throws Throwable {
        NamedPipeline b = new NamedPipeline();
        b.put("c", (Integer e) -> e * 2);
        b.put("d", (Integer e) -> e + 1);
        NamedPipeline a = new NamedPipeline();
        a.put("a", (Integer e) -> e + 1);
        a.put("c", b);
        a.put("b", (Integer e) -> e + 1);
        NamedPipeline res = InsertPolicy.UNCLOSE.execute(a);
        assertAll(
                () -> assertEquals(4, res.execute(0)),
                () -> assertEquals(4, res.size())
        );
    }

    public static class TestConf1 implements Configurator {

        @Override
        public void configureController(Controller controller) {
        }

        @Override
        @DirectAccess
        public void configureInput(NamedPipeline input) {
            input.put("a", (Integer e) -> e + 1);
            input.put("b", (Integer e) -> e + 1);
        }

        @Override
        @DirectAccess
        public void configureOutput(NamedPipeline output) {
            output.put("a", (Integer e) -> e + 1);
        }
    }

    public static class TestConf implements Configurator {

        @Override
        public void configureController(Controller controller) {
        }

        @Override
        public void configureInput(NamedPipeline input) {
            input.put("a", (Integer e) -> e + 1);
            input.put("b", (Integer e) -> e + 1);
        }

        @Override
        public void configureOutput(NamedPipeline output) {
            output.put("a", (Integer e) -> e + 1);
        }
    }
}
