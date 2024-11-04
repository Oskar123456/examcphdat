package dk.obhnothing.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dk.obhnothing.security.controllers.AccessController;
import dk.obhnothing.security.exceptions.ApiException;
import dk.obhnothing.security.exceptions.NotAuthorizedException;
import dk.obhnothing.security.routes.SecurityRoutes;
import dk.obhnothing.utilities.Utils;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpResponseException;
import io.javalin.http.UnauthorizedResponse;

/*
 * Cph Business School....
 * Datamatiker 3. sem.....
 * -----------------------
 * Oskar Bahner Hansen....
 * cph-oh82@cphbusiness.dk
 * 2024-11-04.............
 * -----------------------
 */

public class MasterController
{

    private static Logger logger = LoggerFactory.getLogger(MasterController.class);

    public static PlantControllerDB plantController;
    public static HeadlineController headlineController;

    public static Javalin start(int port)
    {
        plantController = new PlantControllerDB();
        headlineController = new HeadlineController();
        Javalin jav = setup();
        jav.start(port);
        return jav;
    }

    private static Javalin setup()
    {
        Javalin jav = Javalin.create(config -> {
            /* GENERAL */
            config.showJavalinBanner = false;
            config.router.contextPath = "api";
            config.bundledPlugins.enableRouteOverview("/routes");
            config.router.apiBuilder(SecurityRoutes.getSecuredRoutes());
            config.router.apiBuilder(SecurityRoutes.getSecurityRoutes());
            config.requestLogger.http((ctx, ms) -> {
                generalLogger(ctx, ms);
            });
            /* APP-SPECIFIC */
            //config.router.apiBuilder(plantController.getRoutes());
            config.router.apiBuilder(headlineController.getRoutes());
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

}





























