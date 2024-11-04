package dk.obhnothing.control;

import dk.obhnothing.persistence.HibernateConfig;
import dk.obhnothing.persistence.dao.HeadlineDAO;
import dk.obhnothing.persistence.dto.HeadlineDTO;
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

public class HeadlineController
{

    private int num_plants = 10;
    private HeadlineDAO headlineDAO;
    private Logger logger = LoggerFactory.getLogger(PlantController.class);

    public HeadlineController()
    {
        HeadlineDAO.Init(HibernateConfig.getEntityManagerFactory());
        headlineDAO = new HeadlineDAO();
    }

    public EndpointGroup getRoutes()
    {
        return () -> {
            get("/headlines", this::getAll, Role.ANYONE);
            get("/headline", this::getAll, Role.ANYONE);
            get("/headlines/{id}", this::getById, Role.ANYONE);
            get("/headline/{id}", this::getById, Role.ANYONE);
            //get("/headlines/type/{type}", this::getByType, Role.ANYONE);
            //get("/headline/type/{type}", this::getByType, Role.ANYONE);

            post("/headlines", this::create, Role.ADMIN);
            post("/headline", this::create, Role.ADMIN);
        };
    }

    public void update(Context ctx)
    {
    }

    public void create(Context ctx)
    {
        try
        {
            ctx.json(Mapper.Headline_HeadlineDTO(
                        headlineDAO.add(Mapper.HeadlineDTO_Headline(
                            ctx.bodyAsClass(HeadlineDTO.class)))));
            ctx.status(201); // created code
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
            ctx.json(Mapper.Headline_HeadlineDTO(headlineDAO.getById(Integer.parseInt(ctx.pathParam("id")))));
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

    public void getAll(Context ctx)
    {
        try
        {
            ctx.json(headlineDAO.getAll().stream().map(Mapper::Headline_HeadlineDTO).toArray());
            ctx.status(200);
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

