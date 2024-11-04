package dk.obhnothing.control;

import dk.obhnothing.persistence.HibernateConfig;
import dk.obhnothing.persistence.dao.PlantDAO;
import dk.obhnothing.persistence.dto.PlantDTO;
import dk.obhnothing.persistence.ent.Plant;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Cph Business School....
 * Datamatiker 3. sem.....
 * -----------------------
 * Oskar Bahner Hansen....
 * cph-oh82@cphbusiness.dk
 * 2024-11-04.............
 * -----------------------
 */

public class PlantControllerDB implements iPlantController
{

    private int num_plants = 10;
    private PlantDAO plantDAO;
    private Logger logger = LoggerFactory.getLogger(PlantController.class);

    public PlantControllerDB()
    {
        PlantDAO.Init(HibernateConfig.getEntityManagerFactory());
        plantDAO = new PlantDAO();
        plantDAO.populate(num_plants);
    }

    public EndpointGroup getRoutes()
    {
        return () -> {
            get("/plants", this::getAll, Role.ANYONE);
            get("/plant", this::getAll, Role.ANYONE);
            get("/plants/{id}", this::getById, Role.ANYONE);
            get("/plant/{id}", this::getById, Role.ANYONE);
            get("/plants/type/{type}", this::getByType, Role.ANYONE);
            get("/plant/type/{type}", this::getByType, Role.ANYONE);

            post("/plants", this::create, Role.ADMIN);
            post("/plant", this::create, Role.ADMIN);
        };
    }

    public void update(Context ctx)
    {
    }

    public void create(Context ctx)
    {
        try
        {
            ctx.json(Mapper.Plant_PlantDTO(
                        plantDAO.add(Mapper.PlantDTO_Plant(
                                ctx.bodyAsClass(PlantDTO.class)))));
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
            ctx.json(plantDAO.getAll().stream().map(Mapper::Plant_PlantDTO).toArray());
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
            ctx.json(Mapper.Plant_PlantDTO(
                        plantDAO.getById(
                            Integer.parseInt(ctx.pathParam("id")))));
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
            List<Plant> ps = plantDAO.getByType(ctx.pathParam("type"));
            ctx.json(ps.stream().map(Mapper::Plant_PlantDTO).toArray());
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
    }

}

