package dk.obhnothing.persistence.dao;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import static io.javalin.apibuilder.ApiBuilder.after;

import java.util.ArrayList;
import java.util.Set;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nimbusds.jose.shaded.json.JSONArray;

import dk.obhnothing.persistence.dto.TypeDTO;
import dk.obhnothing.persistence.ent.Ability;
import dk.obhnothing.persistence.ent.Habitat;
import dk.obhnothing.persistence.ent.Move;
import dk.obhnothing.persistence.ent.Pokemon;
import dk.obhnothing.persistence.ent.Sprite;
import dk.obhnothing.persistence.ent.Type;
import dk.obhnothing.utilities.Utils;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class PokemonDAO
{

    private static Logger logger = LoggerFactory.getLogger(PokemonDAO.class);
    private static EntityManagerFactory EMF;
    public static void Init(EntityManagerFactory e) {EMF = e;}

    public static Type createType(Type t)
    {
        try (EntityManager em = EMF.createEntityManager())
        {
            em.getTransaction().begin();

            System.out.println(Utils.getObjectMapper().writeValueAsString(t));

            if (em.find(Type.class, t.name) != null)
                return em.find(Type.class, t.name);

            em.createNativeQuery("create table if not exists double_damage_from (this varchar(255), other varchar(255))").executeUpdate();
            em.createNativeQuery("create table if not exists double_damage_to (this varchar(255), other varchar(255))").executeUpdate();
            em.createNativeQuery("create table if not exists half_damage_from (this varchar(255), other varchar(255))").executeUpdate();
            em.createNativeQuery("create table if not exists half_damage_to (this varchar(255), other varchar(255))").executeUpdate();
            em.createNativeQuery("create table if not exists no_damage_from (this varchar(255), other varchar(255))").executeUpdate();
            em.createNativeQuery("create table if not exists no_damage_to (this varchar(255), other varchar(255))").executeUpdate();

            if (t.double_damage_from != null) {
                for (String tp : t.double_damage_from) {
                    em.createNativeQuery("insert into double_damage_from (this, other) VALUES(?1, ?2)")
                        .setParameter(1, t.name).setParameter(2, tp).executeUpdate();
                }
            }
            if (t.double_damage_to != null) {
                for (String tp : t.double_damage_to) {
                    em.createNativeQuery("insert into double_damage_to (this, other) VALUES(?1, ?2)")
                        .setParameter(1, t.name).setParameter(2, tp).executeUpdate();
                }
            }
            if (t.half_damage_from != null) {
                for (String tp : t.half_damage_from) {
                    em.createNativeQuery("insert into half_damage_from (this, other) VALUES(?1, ?2)")
                        .setParameter(1, t.name).setParameter(2, tp).executeUpdate();
                }
            }
            if (t.half_damage_to != null) {
                for (String tp : t.half_damage_to) {
                    em.createNativeQuery("insert into half_damage_to (this, other) VALUES(?1, ?2)")
                        .setParameter(1, t.name).setParameter(2, tp).executeUpdate();
                }
            }
            if (t.no_damage_from != null) {
                for (String tp : t.no_damage_from) {
                    em.createNativeQuery("insert into no_damage_from (this, other) VALUES(?1, ?2)")
                        .setParameter(1, t.name).setParameter(2, tp).executeUpdate();
                }
            }
            if (t.no_damage_to != null) {
                for (String tp : t.no_damage_to) {
                    em.createNativeQuery("insert into no_damage_to (this, other) VALUES(?1, ?2)")
                        .setParameter(1, t.name).setParameter(2, tp).executeUpdate();
                }
            }

            em.persist(t);
            em.getTransaction().commit();
            return t;
        }
        catch (Exception e) { logger.warn(e.getMessage()); e.printStackTrace(); }
        return null;
    }

    public static List<Pokemon> getByType(Type t)
    {
        try (EntityManager em = EMF.createEntityManager())
        {
            em.getTransaction().begin();
            List<Pokemon> ps = em.createQuery("from Pokemon where :t member of types order by id", Pokemon.class)
                .setParameter("t", t).getResultList();
            for (Pokemon p : ps) {
                p.abilities.size();
                p.moves.size();
                p.types.size();
                String s = p.habitat.name;
                s = p.sprites.front_default;
            }
            return ps;
        }
        catch (Exception e) { logger.warn(e.getMessage()); }
        return new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    public static List<TypeDTO> getTypeDTOs()
    {
        List<TypeDTO> dtos = new ArrayList<>();
        try (EntityManager em = EMF.createEntityManager())
        {
            em.getTransaction().begin();
            List<Type> ts = em.createQuery("from Type", Type.class).getResultList();
            for (Type t : ts) {
                TypeDTO dto = new TypeDTO();
                dto.name = t.name;
                dto.double_damage_from = (List<String>)em.createNativeQuery("select other from double_damage_from where this = :name ", String.class)
                    .setParameter("name", t.name).getResultList();
                dto.double_damage_to = (List<String>)em.createNativeQuery("select other from double_damage_to where this = :name ", String.class)
                    .setParameter("name", t.name).getResultList();
                dto.half_damage_from = (List<String>)em.createNativeQuery("select other from half_damage_from where this = :name ", String.class)
                    .setParameter("name", t.name).getResultList();
                dto.half_damage_to = (List<String>)em.createNativeQuery("select other from half_damage_to where this = :name ", String.class)
                    .setParameter("name", t.name).getResultList();
                dto.no_damage_from = (List<String>)em.createNativeQuery("select other from no_damage_from where this = :name ", String.class)
                    .setParameter("name", t.name).getResultList();
                dto.no_damage_to = (List<String>)em.createNativeQuery("select other from no_damage_to where this = :name ", String.class)
                    .setParameter("name", t.name).getResultList();
                dtos.add(dto);
            }
        }
        catch (Exception e) { logger.warn(e.getMessage()); }
        return dtos;
    }

    public static Type getTypeByName(String name)
    {
        try (EntityManager em = EMF.createEntityManager())
        {
            em.getTransaction().begin();
            Type t = em.createQuery("from Type where name = :n", Type.class).setParameter("n", name).getSingleResult();
            return t;
        }
        catch (Exception e) { logger.warn(e.getMessage()); }
        return null;
    }

    public static List<Type> getAllType()
    {
        try (EntityManager em = EMF.createEntityManager())
        {
            em.getTransaction().begin();
            List<Type> ts = em.createQuery("from Type order by name", Type.class).getResultList();
            return ts;
        }
        catch (Exception e) { logger.warn(e.getMessage()); }
        return new ArrayList<>();
    }

    public static List<Pokemon> getByHabitat(Habitat h)
    {
        try (EntityManager em = EMF.createEntityManager())
        {
            em.getTransaction().begin();
            List<Pokemon> ps = em.createQuery("from Pokemon where habitat = :h order by id", Pokemon.class)
                .setParameter("h", h).getResultList();
            for (Pokemon p : ps) {
                p.abilities.size();
                p.moves.size();
                p.types.size();
                String s = p.habitat.name;
                s = p.sprites.front_default;
            }
            return ps;
        }
        catch (Exception e) { logger.warn(e.getMessage()); }
        return new ArrayList<>();
    }

    public static List<Habitat> getAllHabitat()
    {
        try (EntityManager em = EMF.createEntityManager())
        {
            em.getTransaction().begin();
            List<Habitat> hs = em.createQuery("from Habitat order by name", Habitat.class).getResultList();
            return hs;
        }
        catch (Exception e) { logger.warn(e.getMessage()); }
        return new ArrayList<>();
    }

    public static List<Pokemon> getAll()
    {
        try (EntityManager em = EMF.createEntityManager())
        {
            em.getTransaction().begin();
            List<Pokemon> ps = em.createQuery("from Pokemon order by id", Pokemon.class).getResultList();
            for (Pokemon p : ps) {
                p.abilities.size();
                p.moves.size();
                p.types.size();
                String s = p.habitat.name;
                s = p.sprites.front_default;
            }
            return ps;
        }
        catch (Exception e) { logger.warn(e.getMessage()); }
        return new ArrayList<>();
    }

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

            p.abilities.clear();
            p.moves.clear();

            em.persist(p);
            em.getTransaction().commit();
        }
        catch (EntityExistsException | ConstraintViolationException e) { logger.info(e.getMessage());}
        catch (Exception e) { logger.warn(e.getMessage()); e.printStackTrace(); }
    }
}

