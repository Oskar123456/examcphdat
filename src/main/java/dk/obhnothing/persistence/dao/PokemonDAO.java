package dk.obhnothing.persistence.dao;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dk.obhnothing.persistence.ent.Pokemon;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class PokemonDAO
{

    private static Logger logger = LoggerFactory.getLogger(PokemonDAO.class);
    private static EntityManagerFactory EMF;
    public static void Init(EntityManagerFactory e) {EMF = e;}

    public static void create(Pokemon p)
    {
        try (EntityManager em = EMF.createEntityManager())
        {
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
        }
        catch (EntityExistsException | ConstraintViolationException e) { logger.info(e.getMessage()); }
        catch (Exception e) { logger.warn(e.getMessage()); }
    }
}

