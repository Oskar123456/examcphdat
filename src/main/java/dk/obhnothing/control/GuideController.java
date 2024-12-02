package dk.obhnothing.control;

import dk.obhnothing.persistence.HibernateConfig;
import dk.obhnothing.persistence.dao.GuideDAO;
import dk.obhnothing.persistence.dao.TripDAO;
import dk.obhnothing.persistence.dto.GuideDTO;
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

public class GuideController
{

    private int num_plants = 10;
    private GuideDAO dao;
    private Logger logger = LoggerFactory.getLogger(GuideController.class);

    public GuideController()
    {
        GuideDAO.Init(HibernateConfig.getEntityManagerFactory());
        dao = new GuideDAO();
    }

    public void update(Context ctx)
    {
    }

    public void create(Context ctx)
    {
        try
        {
            ctx.json(dao.create(ctx.bodyAsClass(GuideDTO.class)));
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
            List<GuideDTO> dtos = dao.getAll();
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
    }

}

