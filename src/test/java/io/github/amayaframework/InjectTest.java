package io.github.amayaframework;

import io.github.amayaframework.core.config.AmayaConfig;
import io.github.amayaframework.core.config.ConfigProvider;
import io.github.amayaframework.core.contexts.AbstractHttpRequest;
import io.github.amayaframework.core.contexts.HttpRequest;
import io.github.amayaframework.core.wrapping.InjectPacker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

public class InjectTest extends Assertions {
    private static final Map<String, List<String>> HEADERS;

    static {
        List<String> a = Arrays.asList("a_a", "b_b", "c_c");
        List<String> b = Arrays.asList("a_a", "b_b", "c_c");
        List<String> c = Arrays.asList("a_a", "b_b", "c_c");
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("a", a);
        headers.put("b", b);
        headers.put("c", c);
        HEADERS = Collections.unmodifiableMap(headers);
    }

    @BeforeAll
    public static void config() {
        AmayaConfig config = ConfigProvider.getConfig();
        config.setUseNativeNames(true);
        config.setRoutePacker(new InjectPacker());
    }

    public HttpRequest makeRequest() {
        return new AbstractHttpRequest() {
            @Override
            public List<String> getHeaders(String key) {
                return HEADERS.getOrDefault(key, Collections.emptyList());
            }
        };
    }

    @Test
    public void test() {

    }
}
