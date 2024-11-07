package dk.obhnothing.routes;

import io.javalin.apibuilder.EndpointGroup;
import dk.obhnothing.control.TripController;
import dk.obhnothing.persistence.HibernateConfig;
import dk.obhnothing.persistence.dao.TripDAO;
import dk.obhnothing.persistence.service.Mapper;
import dk.obhnothing.security.enums.Role;
import dk.obhnothing.security.exceptions.ApiException;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.http.Context;
import java.util.List;

import static io.javalin.apibuilder.ApiBuilder.delete;
import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;
import static io.javalin.apibuilder.ApiBuilder.put;

public class TripRoutes
{

    private static TripController tripController = new TripController();

    public static EndpointGroup getRoutes()
    {
        return () -> {
            get("/trips", tripController::getAll, Role.ANYONE);
            get("/trip", tripController::getAll, Role.ANYONE);
            get("/trips/populate/{n}", tripController::populate, Role.ADMIN);
            get("/trip/populate/{n}", tripController::populate, Role.ADMIN);
            get("/trips/category/{category}", tripController::filterByCategory, Role.ANYONE);
            get("/trip/category/{category}", tripController::filterByCategory, Role.ANYONE);
            get("/trips/totalweight/{id}", tripController::getTotalPackingWeight, Role.ANYONE);
            get("/trip/totalweight/{id}", tripController::getTotalPackingWeight, Role.ANYONE);
            get("/trips/packinglist/{category}", tripController::getPackingList, Role.ANYONE);
            get("/trip/packinglist/{category}", tripController::getPackingList, Role.ANYONE);
            get("/trips/guidespricesum", tripController::getSumOfEachGuide, Role.ANYONE);
            get("/trip/guidespricesum", tripController::getSumOfEachGuide, Role.ANYONE);
            get("/trips/{id}", tripController::getById, Role.ANYONE);
            get("/trip/{id}", tripController::getById, Role.ANYONE);

            post("/trips", tripController::create, Role.ADMIN);
            post("/trip", tripController::create, Role.ADMIN);

            put("/trips/{id}", tripController::update, Role.ADMIN);
            put("/trip/{id}", tripController::update, Role.ADMIN);

            put("/trips/{tripId}/guides/{guideId}", tripController::addguide, Role.ADMIN);
            put("/trip/{tripId}/guides/{guideId}", tripController::addguide, Role.ADMIN);
        };
    }

}

