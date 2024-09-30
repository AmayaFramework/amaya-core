package io.github.amayaframework.core;

import com.github.romanqed.jfunc.Runnable1;
import io.github.amayaframework.options.GroupOptionSet;

public interface OptionSetHandler extends Runnable1<GroupOptionSet> {

    @Override
    void run(GroupOptionSet groupOptionSet) throws Throwable;
}
