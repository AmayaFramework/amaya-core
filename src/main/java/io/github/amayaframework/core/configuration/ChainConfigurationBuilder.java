package io.github.amayaframework.core.configuration;

import java.util.HashMap;
import java.util.Objects;

public class ChainConfigurationBuilder extends AbstractConfigurationProvider {

    private KeyProvider linkProviders() {
        var iterator = providers.iterator();
        var ret = new ChainProvider(iterator.next());
        while (iterator.hasNext()) {
            var next = new ChainProvider(iterator.next());
            ret.setNext(next);
            ret = next;
        }
        return ret;
    }

    @Override
    protected Configuration checkedBuild() throws Throwable {
        Objects.requireNonNull(scheme);
        if (providers.isEmpty()) {
            throw new IllegalStateException("The builder must contain at least one key provider");
        }
        var provider = linkProviders();
        var map = new HashMap<Key<?>, Object>();
        for (var key : scheme) {
            var object = provider.invoke(key);
            var type = key.getType().getRawType();
            if (type != null && !type.isInstance(object)) {
                throw new IllegalArgumentException(
                        "An object of " + type + " was expected, " +
                                "but an object of type " + object.getClass() + " was received"
                );
            }
            map.put(key, object);
        }
        return new MapConfiguration(map);
    }
}
