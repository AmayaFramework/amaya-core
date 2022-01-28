package io.github.amayaframework.core.pipelines.debug;

import com.github.romanqed.jutils.structs.pipeline.PipelineResult;
import io.github.amayaframework.core.contexts.HttpResponse;
import io.github.amayaframework.core.pipelines.PipelineAction;
import io.github.amayaframework.core.util.ParseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>An input action which outputs information about the input pipeline result.</p>
 * <p>Receives: {@link PipelineResult}</p>
 * <p>Returns: {@link PipelineResult}</p>
 */
public class InputResultDebugAction extends PipelineAction<PipelineResult, PipelineResult> {
    private static final Logger logger = LoggerFactory.getLogger(InputResultDebugAction.class);

    @Override
    public PipelineResult apply(PipelineResult result) {
        String message = "The input pipeline is completed\n" +
                "Interrupted: " + result.isInterrupted() + "\n" +
                "Exception: " + result.getException() + "\n";
        Object toCheck = result.getResult();
        if (toCheck != null) {
            message += "Result: " + result.getResult().getClass().getSimpleName() + "\n";
        }
        if (toCheck instanceof HttpResponse) {
            message += ParseUtil.getResponseData((HttpResponse) toCheck);
        }
        logger.debug(message, result.getException());
        return result;
    }
}
