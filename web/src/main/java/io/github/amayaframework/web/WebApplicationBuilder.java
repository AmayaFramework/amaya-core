package io.github.amayaframework.web;

import com.github.romanqed.jfunc.Runnable1;
import io.github.amayaframework.application.Application;
import io.github.amayaframework.application.ApplicationBuilder;
import io.github.amayaframework.application.ServiceManagerBuilder;
import io.github.amayaframework.context.HttpContext;
import io.github.amayaframework.di.ServiceProvider;
import io.github.amayaframework.di.ServiceProviderBuilder;
import io.github.amayaframework.options.GroupOptionSet;

public interface WebApplicationBuilder extends ApplicationBuilder<HttpContext> {

//    @Override
//    WebApplicationBuilder configure(Runnable1<WebApplicationBuilder> runnable1);

    @Override
    WebApplicationBuilder setOptions(GroupOptionSet groupOptionSet);

    @Override
    WebApplicationBuilder configureOptions(Runnable1<GroupOptionSet> runnable1);

//    @Override
//    WebApplicationBuilder setEnvironmentFactory(EnvironmentFactory environmentFactory);

    @Override
    ApplicationBuilder<HttpContext> setEnvironmentName(String s);

    @Override
    ApplicationBuilder<HttpContext> configureManager(Runnable1<ServiceManagerBuilder> runnable1);

    @Override
    ApplicationBuilder<HttpContext> configureProviderBuilder(Runnable1<ServiceProviderBuilder> runnable1);

    @Override
    ApplicationBuilder<HttpContext> configureProvider(Runnable1<ServiceProvider> runnable1);

    @Override
    ApplicationBuilder<HttpContext> configureApplication(Runnable1<Application<HttpContext>> runnable1);

    @Override
    Application<HttpContext> build();
}
