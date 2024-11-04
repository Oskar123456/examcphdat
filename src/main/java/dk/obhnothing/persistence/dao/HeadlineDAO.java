package dk.obhnothing.persistence.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dk.obhnothing.persistence.dto.PlantDTO;
import dk.obhnothing.persistence.dto.ResellerDTO;
import dk.obhnothing.persistence.ent.Headline;
import dk.obhnothing.persistence.ent.Plant;
import dk.obhnothing.persistence.ent.Reseller;
import dk.obhnothing.persistence.service.Mapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.NoArgsConstructor;

/*
 * Cph Business School....
 * Datamatiker 3. sem.....
 * -----------------------
 * Oskar Bahner Hansen....
 * cph-oh82@cphbusiness.dk
 * 2024-11-04.............
 * -----------------------
 */

@NoArgsConstructor
public class HeadlineDAO
{

    private static Logger logger = LoggerFactory.getLogger(HeadlineDAO.class);
    private static EntityManagerFactory EMF;
    public static void Init(EntityManagerFactory e) {EMF = e;}

    public List<Headline> getAll()
    {
        try (EntityManager EM = EMF.createEntityManager())
        {
            EM.getTransaction().begin();
            return EM.createQuery("from Headline", Headline.class).getResultList();
        }

        catch (Exception e)
        {
            logger.info(e.getMessage());
            return new ArrayList<>();
        }
    }

    public Headline getById(int id)
    {
        try (EntityManager EM = EMF.createEntityManager())
        {
            EM.getTransaction().begin();
            return EM.createQuery("from Headline where id = :id", Headline.class)
                .setParameter("id", id).getSingleResult();
        }

        catch (Exception e)
        {
            logger.info(e.getMessage());
            return null;
        }
    }

    public List<Headline> getByType(String type)
    {
        try (EntityManager EM = EMF.createEntityManager())
        {
            EM.getTransaction().begin();
            return EM.createQuery("from Headline where planttype = :type", Headline.class)
                .setParameter("type", type).getResultList();
        }

        catch (Exception e)
        {
            logger.info(e.getMessage());
            return new ArrayList<>();
        }
    }

    public Headline add(Headline t)
    {
        try (EntityManager EM = EMF.createEntityManager())
        {
            EM.getTransaction().begin();
            // if (EM.find(Source.class, t.source.id) == null)
            //     EM.persist(t.source);
            EM.persist(t);
            EM.getTransaction().commit();
            return t;
        }

        catch (Exception e)
        {
            logger.info(e.getMessage());
            return null;
        }
    }

    public Headline deleteHeadline(int id)
    {
        try (EntityManager EM = EMF.createEntityManager())
        {
            EM.getTransaction().begin();
            Headline p = EM.find(Headline.class, id);
            EM.remove(p);
            EM.getTransaction().commit();
            return p;
        }

        catch (Exception e)
        {
            logger.info(e.getMessage());
            return null;
        }
    }

//    public int populate(int n)
//    {
//        Mapper.gen_Headlines(n).forEach(this::add);
//        return n;
//    }
//
    public void deleteAll()
    {
        try (EntityManager EM = EMF.createEntityManager())
        {
            EM.getTransaction().begin();
            EM.createQuery("delete from Headline").executeUpdate();
            EM.getTransaction().commit();
        }

        catch (Exception e)
        {
            logger.info(e.getMessage());
        }
    }

}

