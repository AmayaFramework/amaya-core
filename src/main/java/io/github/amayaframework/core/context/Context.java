package io.github.amayaframework.core.context;

public interface Context<RQ, RP> extends Attributable<String> {

    RQ getRequest();

    RP getResponse();

    Context<?, ?> getOriginContext();
}
