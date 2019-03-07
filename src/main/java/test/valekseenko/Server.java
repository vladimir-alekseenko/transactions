package test.valekseenko;

import io.undertow.Undertow;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;

public class Server {

    public static void main( String[] args ) {
        UndertowJaxrsServer server = new UndertowJaxrsServer()
                .start(Undertow.builder().addHttpListener(8080,"localhost"));
        server.deploy(JaxrsApplication.class);
    }

}
