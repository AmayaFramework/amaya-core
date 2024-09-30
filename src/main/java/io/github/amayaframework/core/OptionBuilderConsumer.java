package io.github.amayaframework.core;

import com.github.romanqed.jfunc.Runnable1;

public interface OptionBuilderConsumer extends Runnable1<OptionSetBuilder> {

    @Override
    void run(OptionSetBuilder builder) throws Throwable;
}
