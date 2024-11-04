package dk.obhnothing.control;

import io.javalin.apibuilder.EndpointGroup;
import io.javalin.http.Context;

/*
 * Cph Business School....
 * Datamatiker 3. sem.....
 * -----------------------
 * Oskar Bahner Hansen....
 * cph-oh82@cphbusiness.dk
 * 2024-11-04.............
 * -----------------------
 */

public interface iPlantController
{

    EndpointGroup getRoutes();

    void update(Context ctx);
    void create(Context ctx);
    void getAll(Context ctx);
    void getById(Context ctx);
    void getByType(Context ctx);
    void deleteById(Context ctx);

}

