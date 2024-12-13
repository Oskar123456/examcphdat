package dk.obhnothing.persistence.dao;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dk.obhnothing.persistence.ent.Ability;
import dk.obhnothing.persistence.ent.Habitat;
import dk.obhnothing.persistence.ent.Move;
import dk.obhnothing.persistence.ent.Pokemon;
import dk.obhnothing.persistence.ent.Sprite;
import dk.obhnothing.persistence.ent.Type;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class PokemonDAO
{

    private static Logger logger = LoggerFactory.getLogger(PokemonDAO.class);
    private static EntityManagerFactory EMF;
    public static void Init(EntityManagerFactory e) {EMF = e;}

    public static Pokemon getByName(String name)
    {
        try (EntityManager em = EMF.createEntityManager())
        {
            em.getTransaction().begin();
            Pokemon p = em.createQuery("from Pokemon where name = :n", Pokemon.class)
                .setParameter("n", name)
                .getSingleResult();
            p.abilities.size();
            p.moves.size();
            p.types.size();
            String s = p.habitat.name;
            s = p.sprites.front_default;
            return p;
        }
        catch (Exception e) { logger.warn(e.getMessage()); }
        return null;
    }

    public static Pokemon getById(int id)
    {
        try (EntityManager em = EMF.createEntityManager())
        {
            em.getTransaction().begin();
            Pokemon p = em.find(Pokemon.class, id);
            p.abilities.size();
            p.moves.size();
            p.types.size();
            String s = p.habitat.name;
            s = p.sprites.front_default;
            return p;
        }
        catch (Exception e) { logger.warn(e.getMessage()); }
        return null;
    }

    public static void create(Pokemon p)
    {
        try (EntityManager em = EMF.createEntityManager())
        {
            em.getTransaction().begin();

            if (em.find(Habitat.class, p.habitat.name) == null)
                em.persist(p.habitat);
            if (p.sprites.id == null || em.find(Sprite.class, p.sprites.id) == null)
                em.persist(p.sprites);

            if (p.abilities != null) {
                Iterator<Ability> it = p.abilities.iterator();
                while (it.hasNext()) {
                    Ability a = it.next();
                    //a.pokemons = new HashSet<>();
                    //a.pokemons.add(p);
                    if (a.id == null || em.find(Ability.class, a.id) == null)
                        em.persist(a);
                }
            }
            if (p.moves != null) {
                Iterator<Move> it = p.moves.iterator();
                while (it.hasNext()) {
                    Move a = it.next();
                    //a.pokemons = new HashSet<>();
                    //a.pokemons.add(p);
                    if (a.id == null || em.find(Move.class, a.id) == null)
                        em.persist(a);
                }
            }
            if (p.types != null) {
                Iterator<Type> it = p.types.iterator();
                while (it.hasNext()) {
                    Type a = it.next();
                    //a.pokemons = new HashSet<>();
                    //a.pokemons.add(p);
                    if (a.id == null || em.find(Type.class, a.id) == null)
                        em.persist(a);
                }
            }

            p.abilities.clear();
            p.types.clear();
            p.moves.clear();

            em.persist(p);
            em.getTransaction().commit();
        }
        catch (EntityExistsException | ConstraintViolationException e) { logger.info(e.getMessage());}
        catch (Exception e) { logger.warn(e.getMessage()); e.printStackTrace(); }
    }
}

