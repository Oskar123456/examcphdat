package dk.obhnothing.control;

import dk.obhnothing.persistence.HibernateConfig;
import dk.obhnothing.persistence.dao.GuideDAO;
import dk.obhnothing.persistence.dao.TripDAO;
import dk.obhnothing.persistence.dto.GuideDTO;
import dk.obhnothing.persistence.dto.TripDTO;
import dk.obhnothing.persistence.ent.Guide;
import dk.obhnothing.persistence.enums.Category;
import dk.obhnothing.persistence.service.Mapper;
import dk.obhnothing.persistence.service.Populator;
import dk.obhnothing.security.enums.Role;
import dk.obhnothing.security.exceptions.ApiException;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.http.Context;

import java.util.HashMap;
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
    private GuideDAO guide_dao;
    private Logger logger = LoggerFactory.getLogger(TripController.class);

    public TripController()
    {
        TripDAO.Init(HibernateConfig.getEntityManagerFactory());
        GuideDAO.Init(HibernateConfig.getEntityManagerFactory());
        dao = new TripDAO();
        guide_dao = new GuideDAO();
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

    public void filterByCategory(Context ctx)
    {
        try
        {
            Category cat = Category.valueOf(ctx.pathParam("category"));
            List<TripDTO> dtos = dao.getAll();
            ctx.json(dtos.stream().filter(t -> t.category == cat).toList());
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

    public void getSumOfEachGuide(Context ctx)
    {
        try
        {
            List<TripDTO> dtos = dao.getAll();
            HashMap<Integer, Double> pmap = new HashMap<>();
            for (TripDTO d : dtos) {
                if (d.guide != null && d.guide.id != null && d.price != null)
                    pmap.put(d.guide.id, d.price + (pmap.containsKey(d.guide.id) ? 0 : pmap.get(d.guide.id)));
            }
            ctx.json(pmap);
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

