package dk.obhnothing.persistence.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dk.obhnothing.persistence.dto.PlantDTO;
import dk.obhnothing.persistence.dto.ResellerDTO;
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
public class PlantDAO implements iDao_Garden<Plant>
{

    private static Logger logger = LoggerFactory.getLogger(PlantDAO.class);
    private static EntityManagerFactory EMF;
    public static void Init(EntityManagerFactory e) {EMF = e;}

    public List<Plant> getAll()
    {
        try (EntityManager EM = EMF.createEntityManager())
        {
            EM.getTransaction().begin();
            return EM.createQuery("from Plant", Plant.class).getResultList();
        }

        catch (Exception e)
        {
            logger.info(e.getMessage());
            return new ArrayList<>();
        }
    }

    public Plant getById(int id)
    {
        try (EntityManager EM = EMF.createEntityManager())
        {
            EM.getTransaction().begin();
            return EM.createQuery("from Plant where id = :id", Plant.class)
                .setParameter("id", id).getSingleResult();
        }

        catch (Exception e)
        {
            logger.info(e.getMessage());
            return null;
        }
    }

    public List<Plant> getByType(String type)
    {
        try (EntityManager EM = EMF.createEntityManager())
        {
            EM.getTransaction().begin();
            return EM.createQuery("from Plant where planttype = :type", Plant.class)
                .setParameter("type", type).getResultList();
        }

        catch (Exception e)
        {
            logger.info(e.getMessage());
            return new ArrayList<>();
        }
    }

    public Plant add(Plant t)
    {
        try (EntityManager EM = EMF.createEntityManager())
        {
            EM.getTransaction().begin();
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

    public List<Plant> getMaxHeightIs100()
    {
        try (EntityManager EM = EMF.createEntityManager())
        {
            EM.getTransaction().begin();
            return EM.createQuery("from Plant where maxheight = :maxheight", Plant.class)
                .setParameter("maxheight", 100).getResultList();
        }

        catch (Exception e)
        {
            logger.info(e.getMessage());
            return new ArrayList<>();
        }
    }

    public Plant deletePlant(int id)
    {
        try (EntityManager EM = EMF.createEntityManager())
        {
            EM.getTransaction().begin();
            Plant p = EM.find(Plant.class, id);
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

    public Reseller addPlantToReseller(int resellerId, int plantId)
    {
        try (EntityManager EM = EMF.createEntityManager())
        {
            EM.getTransaction().begin();
            Reseller r = EM.find(Reseller.class, resellerId);
            Plant p = EM.find(Plant.class, plantId);
            r.plants.add(p);
            p.resellers.add(r);
            EM.merge(r);
            EM.merge(p);
            EM.getTransaction().commit();
            return r;
        }

        catch (Exception e)
        {
            logger.info(e.getMessage());
            return null;
        }
    }

    public List<Plant> getPlantsByReseller(int resellerId)
    {
        try (EntityManager EM = EMF.createEntityManager())
        {
            EM.getTransaction().begin();
            Reseller r = EM.find(Reseller.class, resellerId);
            return EM.createQuery("from Plant where :r in resellers", Plant.class)
                .setParameter("r", r).getResultList();
        }

        catch (Exception e)
        {
            logger.info(e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<String> getNames(List<Plant> plants)
    {
        try {
            return plants.stream().map(p -> p.name).toList();
        }
        catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public List<Plant> getSortedByNames(List<Plant> plants)
    {
        try {
            return plants.stream().sorted((a, b) -> a.name.compareTo(b.name)).toList();
        }
        catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public int populate(int n)
    {
        Mapper.gen_Plants(n).forEach(this::add);
        return n;
    }

    public void deleteAll()
    {
        try (EntityManager EM = EMF.createEntityManager())
        {
            EM.getTransaction().begin();
            EM.createQuery("delete from Plant").executeUpdate();
            EM.getTransaction().commit();
        }

        catch (Exception e)
        {
            logger.info(e.getMessage());
        }
    }

}
