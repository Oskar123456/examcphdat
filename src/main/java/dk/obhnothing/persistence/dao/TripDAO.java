package dk.obhnothing.persistence.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dk.obhnothing.persistence.dto.TripDTO;
import dk.obhnothing.persistence.ent.Guide;
import dk.obhnothing.persistence.ent.Trip;
import dk.obhnothing.persistence.service.Mapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TripDAO implements IDAO<TripDTO, Integer>, ITripGuideDAO
{

    private static Logger logger = LoggerFactory.getLogger(TripDAO.class);
    private static EntityManagerFactory EMF;
    public static void Init(EntityManagerFactory e) {EMF = e;}

    public void deleteAll()
    {
        try (EntityManager EM = EMF.createEntityManager())
        {
            EM.getTransaction().begin();
            EM.createQuery("delete t from Trip t", Trip.class).executeUpdate();
        }

        catch (Exception e)
        {
            logger.info(e.getMessage());
        }
    }

    public List<TripDTO> getAll()
    {
        try (EntityManager EM = EMF.createEntityManager())
        {
            EM.getTransaction().begin();
            return EM.createQuery("from Trip", Trip.class).getResultList()
                .stream().map(Mapper::Trip_TripDTO).toList();
        }

        catch (Exception e)
        {
            logger.info(e.getMessage());
            return new ArrayList<>();
        }
    }

    public TripDTO getById(Integer id)
    {
        try (EntityManager EM = EMF.createEntityManager())
        {
            EM.getTransaction().begin();
            return Mapper.Trip_TripDTO(
                    EM.createQuery("from Trip where id = :id", Trip.class)
                    .setParameter("id", id).getSingleResult());
        }

        catch (Exception e)
        {
            logger.info(e.getMessage());
            return null;
        }
    }

    public TripDTO create(TripDTO dto)
    {
        try (EntityManager EM = EMF.createEntityManager())
        {
            Trip t = Mapper.TripDTO_Trip(dto);
            EM.getTransaction().begin();
            if (t.guide == null &&
                    (t.guide.id == null || EM.find(Guide.class, t.guide.id) == null))
                EM.persist(t.guide);
            EM.persist(t);
            EM.getTransaction().commit();
            return Mapper.Trip_TripDTO(t);
        }

        catch (Exception e)
        {
            logger.info(e.getMessage());
            return null;
        }
    }

    public TripDTO update(Integer id, TripDTO dto)
    {
        try (EntityManager EM = EMF.createEntityManager())
        {
            EM.getTransaction().begin();
            Trip t = EM.find(Trip.class, id);
            EM.persist(t);
            EM.getTransaction().commit();
            return dto;
        }

        catch (Exception e)
        {
            logger.info(e.getMessage());
            return null;
        }
    }

    public TripDTO delete(Integer id)
    {
        try (EntityManager EM = EMF.createEntityManager())
        {
            EM.getTransaction().begin();
            Trip t = EM.find(Trip.class, id);
            EM.remove(t);
            EM.getTransaction().commit();
            return Mapper.Trip_TripDTO(t);
        }

        catch (Exception e)
        {
            logger.info(e.getMessage());
            return null;
        }
    }

    public void addGuideToTrip(int tripId, int guideId)
    {
        try (EntityManager EM = EMF.createEntityManager())
        {
            EM.getTransaction().begin();
            Trip t = EM.find(Trip.class, tripId);
            Guide g = EM.find(Guide.class, guideId);
            if (g == null)
                EM.persist(g);
            t.guide = g;
            EM.merge(t);
            EM.getTransaction().commit();
        }

        catch (Exception e)
        {
            logger.info(e.getMessage());
        }
    }

    public Set<TripDTO> getTripsByGuide(int guideId)
    {
        try (EntityManager EM = EMF.createEntityManager())
        {
            EM.getTransaction().begin();
            Guide g = EM.find(Guide.class, guideId);
            return new HashSet<TripDTO>(EM.createQuery("from Trip where guide = g", Trip.class)
                .setParameter("g", g).getResultList()
                .stream().map(Mapper::Trip_TripDTO).toList());
        }

        catch (Exception e)
        {
            logger.info(e.getMessage());
            return new HashSet<>();
        }
    }

}

