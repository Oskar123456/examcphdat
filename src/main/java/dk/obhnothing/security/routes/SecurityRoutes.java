package dk.obhnothing.security.routes;

import com.fasterxml.jackson.databind.ObjectMapper;

import dk.obhnothing.security.controllers.SecurityController;
import dk.obhnothing.security.enums.Role;
import dk.obhnothing.utilities.Utils;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

/**
 * Purpose: To handle security in the API
 *  Author: Thomas Hartmann
 */
public class SecurityRoutes {
    private static ObjectMapper jsonMapper = new Utils().getObjectMapper();
    private static SecurityController securityController = SecurityController.getInstance();
    public static EndpointGroup getSecurityRoutes() {
        return ()->{
            path("/auth", ()->{
                get("/test", ctx->ctx.json(jsonMapper.createObjectNode().put("msg",  "Hello from Open")), Role.ANYONE);
                get("/healthcheck", ctx->ctx.json(jsonMapper.createObjectNode().put("message",  "i'm good :)")), Role.ANYONE);
                post("/login", securityController.login(), Role.ANYONE);
                post("/register", securityController.register(), Role.ANYONE);
                post("/user/addrole", securityController.addRole(), Role.USER);
            });
        };
    }
    public static EndpointGroup getSecuredRoutes(){
        return ()->{
            path("/protected", ()->{
                get("/user_demo", (ctx)->ctx.json(jsonMapper.createObjectNode().put("msg", "Hello from USER Protected")), Role.USER);
                get("/admin_demo", (ctx)->ctx.json(jsonMapper.createObjectNode().put("msg", "Hello from ADMIN Protected")), Role.ADMIN);
            });
        };
    }
}
