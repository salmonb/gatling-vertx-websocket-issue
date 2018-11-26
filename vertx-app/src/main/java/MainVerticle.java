import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.LoggerHandler;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;

/**
 * @author Bruno Salmon
 */
public class MainVerticle extends AbstractVerticle {

    @Override
    public void start() {
        // Creating web server and its router
        HttpServerOptions httpServerOptions = new HttpServerOptions()
                .setPort(80) // web port
                ;
        HttpServer server = vertx.createHttpServer(httpServerOptions);
        Router router = Router.router(vertx);

        // Logging web requests
        router.route("/*").handler(LoggerHandler.create());

        // SockJS event bus bridge
        router.route("/eventbus/*").handler(SockJSHandler.create(vertx)
                .bridge(new BridgeOptions()
                                .addInboundPermitted(new PermittedOptions(new JsonObject()))
                                .addOutboundPermitted(new PermittedOptions(new JsonObject()))
                )
        );

        vertx.eventBus().consumer("myAddress").handler(message -> {
            System.out.println("Received: " + message.body());
            message.reply("myResponse");
        });

        // Starting the web server
        server.requestHandler(router::accept).listen();
    }

    public static void main(String[] args) {
        VertxRunner.runVerticle(MainVerticle.class);
    }
}
