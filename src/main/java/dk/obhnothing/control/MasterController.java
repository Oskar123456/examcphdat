package dk.obhnothing.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import dk.obhnothing.security.controllers.AccessController;
import dk.obhnothing.security.exceptions.ApiException;
import dk.obhnothing.security.exceptions.NotAuthorizedException;
import dk.obhnothing.security.routes.SecurityRoutes;
import dk.obhnothing.utilities.Utils;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpResponseException;
import io.javalin.http.UnauthorizedResponse;
import io.javalin.json.JavalinJackson;

public class MasterController
{

    private static Logger logger = LoggerFactory.getLogger(MasterController.class);

    public static Javalin start(int port)
    {
        Javalin jav = setup();
        jav.start(port);
        return jav;
    }

    private static Javalin setup()
    {
        Javalin jav = Javalin.create(config -> {
            /* GENERAL */
            config.jsonMapper(new JavalinJackson().updateMapper(mapper -> {
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // Ignore unknown properties in JSON
                mapper.registerModule(new JavaTimeModule()); // Serialize and deserialize java.time objects
                mapper.writer(new DefaultPrettyPrinter());
                mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                mapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
            }));
            config.showJavalinBanner = false;
            config.router.contextPath = "/api";
            config.bundledPlugins.enableRouteOverview("/routes");
            config.router.apiBuilder(SecurityRoutes.getSecuredRoutes());
            config.router.apiBuilder(SecurityRoutes.getSecurityRoutes());
            config.requestLogger.http((ctx, ms) -> {
                generalLogger(ctx, ms);
            });
            /* APP-SPECIFIC */
            config.router.apiBuilder(PokemonController.getPokemonRoutes());
        });
        /* EXCEPTIONS */
        AccessController accessController = new AccessController();
        jav.beforeMatched(accessController::accessHandler);
        jav.exception(ApiException.class, MasterController::apiExceptionHandler);
        /* SECURITY */
        jav.exception(NotAuthorizedException.class, MasterController::notAuthExceptionHandler);
        jav.exception(UnauthorizedResponse.class, MasterController::unauthExceptionHandler);
        /* UNCAUGHT */
        jav.exception(HttpResponseException.class, MasterController::httpErrorExceptionHandler);
        jav.exception(Exception.class, MasterController::generalExceptionHandler);
        /* CORS */
        jav.before(MasterController::corsHeaders);
        jav.options("/*", MasterController::corsHeadersOptions);

        return jav;
    }

    private static void generalLogger(Context ctx, float ms)
    {
        logger.info("from {}: {} {} completed in {} ms (r: {})",
                ctx.ip(), ctx.req().getMethod(), ctx.path(), ms, ctx.statusCode());
    }

    private static void generalExceptionHandler(Exception e, Context ctx)
    {
        logger.error("Internal server error: {}", e.getMessage());
        ctx.status(500);
        ctx.json(Utils.convertToJsonMessage(ctx, "message", e.getMessage()));
    }

    private static void httpErrorExceptionHandler(HttpResponseException e, Context ctx)
    {
        if (e.getStatus() == 429)
            logger.warn("Rate limit exceeded: {} ({})", e.getMessage(), ctx.ip());
        else
            logger.info("HttpResponseException: {}", e.getMessage());
        ctx.status(e.getStatus());
        ctx.json(Utils.convertToJsonMessage(ctx, "message", e.getMessage()));
    }

    private static void notAuthExceptionHandler(Exception e, Context ctx)
    {
        logger.info("NOT AUTHORIZED: {}", e.getMessage());
        ctx.status(403);
        ctx.json(Utils.convertToJsonMessage(ctx, "message", e.getMessage()));
    }

    private static void unauthExceptionHandler(Exception e, Context ctx)
    {
        logger.info("UNAUTHORIZED: {}", e.getMessage());
        ctx.status(401);
        ctx.json(Utils.convertToJsonMessage(ctx, "message", e.getMessage()));
    }

    private static void apiExceptionHandler(ApiException e, Context ctx)
    {
        ctx.status(e.getCode());
        logger.info("An API exception occurred: Code: {}, Message: {}", e.getCode(), e.getMessage());
        ctx.json(Utils.convertToJsonMessage(ctx, "message", e.getMessage()));
    }

    private static void corsHeaders(Context ctx) {
        ctx.header("Access-Control-Allow-Origin", "*");
        ctx.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        ctx.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
        ctx.header("Access-Control-Allow-Credentials", "true");
    }

    private static void corsHeadersOptions(Context ctx) {
        ctx.header("Access-Control-Allow-Origin", "*");
        ctx.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        ctx.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
        ctx.header("Access-Control-Allow-Credentials", "true");
        ctx.status(204);
    }

}





























