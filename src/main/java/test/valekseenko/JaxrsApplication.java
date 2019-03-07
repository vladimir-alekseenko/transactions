package test.valekseenko;

import com.google.inject.Guice;
import com.google.inject.Injector;
import test.valekseenko.endpoints.TransactionEndpoint;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class JaxrsApplication extends Application {

    private Injector injector = Guice.createInjector(new GuiceModule());

    @Override
    public Set<Object> getSingletons() {
        Set<Object> objects = new HashSet<>();
        objects.add(injector.getInstance(TransactionEndpoint.class));
        return objects;
    }

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(TransactionExceptionMapper.class);
        return classes;
    }

}
