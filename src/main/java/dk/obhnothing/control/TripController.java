package dk.obhnothing.control;

import dk.obhnothing.persistence.HibernateConfig;
import dk.obhnothing.persistence.dao.TripDAO;
import dk.obhnothing.persistence.dto.TripDTO;
import dk.obhnothing.persistence.service.Mapper;
import dk.obhnothing.persistence.service.Populator;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TripController
{

    private int num_plants = 10;
    private TripDAO dao;
    private Logger logger = LoggerFactory.getLogger(TripController.class);

    public TripController()
    {
        TripDAO.Init(HibernateConfig.getEntityManagerFactory());
        dao = new TripDAO();
    }

    public EndpointGroup getRoutes()
    {
        return () -> {
            get("/trips", this::getAll, Role.ANYONE);
            get("/trip", this::getAll, Role.ANYONE);
            get("/trips/{id}", this::getById, Role.ANYONE);
            get("/trip/{id}", this::getById, Role.ANYONE);
            get("/trips/type/{type}", this::getByType, Role.ANYONE);
            get("/trip/type/{type}", this::getByType, Role.ANYONE);

            post("/trips", this::create, Role.ADMIN);
            post("/trip", this::create, Role.ADMIN);
            put("/trips/{id}", this::update, Role.ADMIN);
            put("/trip/{id}", this::update, Role.ADMIN);
            post("/trips/populate", this::populate, Role.ADMIN);
            put("/trips/{tripId}/guides/{guideId}", this::addguide, Role.ADMIN);
            put("/trip/{tripId}/guides/{guideId}", this::addguide, Role.ADMIN);
        };
    }

    public void addguide(Context ctx)
    {
        try
        {
            int trip_id = Integer.parseInt(ctx.pathParam("tripId"));
            int guide_id = Integer.parseInt(ctx.pathParam("guideId"));
            dao.addGuideToTrip(trip_id, guide_id);
            ctx.status(200);
        }
        catch (NumberFormatException e)
        {
            logger.info(e.getMessage());
            throw new ApiException(400, e.getMessage());
        }
        catch (Exception e)
        {
            logger.info(e.getMessage());
            throw new ApiException(404, e.getMessage());
        }
    }

    public void update(Context ctx)
    {
        try
        {
            int id = Integer.parseInt(ctx.pathParam("id"));
            TripDTO dto = dao.update(id, ctx.bodyAsClass(TripDTO.class));
            ctx.json(dto);
            ctx.status(200);
        }
        catch (NumberFormatException e)
        {
            logger.info(e.getMessage());
            throw new ApiException(400, e.getMessage());
        }
        catch (Exception e)
        {
            logger.info(e.getMessage());
            throw new ApiException(404, e.getMessage());
        }
    }

    public void create(Context ctx)
    {
        try
        {
            TripDTO dto = ctx.bodyAsClass(TripDTO.class);
            dto = dao.create(dto);
            ctx.json(dto);
            ctx.status(201); // created code
        }
        catch (Exception e)
        {
            logger.info(e.getMessage());
            throw new ApiException(404, e.getMessage());
        }
    }

    public void getAll(Context ctx)
    {
        try
        {
            List<TripDTO> dtos = dao.getAll();
            ctx.json(dtos);
            ctx.status(200);
        }
        catch (Exception e)
        {
            logger.info(e.getMessage());
            throw new ApiException(404, e.getMessage());
        }
    }

    public void getById(Context ctx)
    {
        try
        {
            TripDTO dto = dao.getById(Integer.parseInt(ctx.pathParam("id")));
            ctx.json(dto);
            ctx.status(200);
        }
        catch (NumberFormatException e)
        {
            logger.info(e.getMessage());
            throw new ApiException(400, e.getMessage());
        }
        catch (Exception e)
        {
            logger.info(e.getMessage());
            throw new ApiException(404, e.getMessage());
        }
    }

    public void getByType(Context ctx)
    {
        try
        {
            ctx.status(200);
        }
        catch (NullPointerException e)
        {
            logger.info(e.getMessage());
            throw new ApiException(400, e.getMessage());
        }
        catch (Exception e)
        {
            logger.info(e.getMessage());
            throw new ApiException(404, e.getMessage());
        }
    }

    public void deleteById(Context ctx)
    {
        try
        {
            TripDTO dto = dao.delete(Integer.parseInt(ctx.pathParam("id")));
            ctx.json(dto);
            ctx.status(200);
        }
        catch (NumberFormatException e)
        {
            logger.info(e.getMessage());
            throw new ApiException(400, e.getMessage());
        }
        catch (Exception e)
        {
            logger.info(e.getMessage());
            throw new ApiException(404, e.getMessage());
        }
    }

    public void populate(Context ctx)
    {
        Populator.PopTrips(5);
    }

}

