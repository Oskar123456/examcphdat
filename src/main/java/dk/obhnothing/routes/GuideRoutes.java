package dk.obhnothing.routes;

import static io.javalin.apibuilder.ApiBuilder.post;

import dk.obhnothing.control.GuideController;
import dk.obhnothing.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

public class GuideRoutes
{

    private static GuideController guideController = new GuideController();

    public static EndpointGroup getRoutes()
    {
        return () -> {
            post("/guides", guideController::create, Role.ADMIN);
            post("/guide", guideController::create, Role.ADMIN);
        };
    }

}

