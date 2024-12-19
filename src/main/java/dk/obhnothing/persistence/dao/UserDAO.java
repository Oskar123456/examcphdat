package dk.obhnothing.persistence.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dk.bugelhartmann.UserDTO;
import dk.obhnothing.persistence.ent.Pokemon;
import dk.obhnothing.security.entities.Role;
import dk.obhnothing.security.entities.User;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class UserDAO
{

    private static Logger logger = LoggerFactory.getLogger(UserDAO.class);
    private static EntityManagerFactory EMF;
    public static void Init(EntityManagerFactory e) {EMF = e;}

    public static void initUserPokeStuff() {
        try (EntityManager em = EMF.createEntityManager())
        {
            em.getTransaction().begin();
            em.createNativeQuery("create table if not exists user_pokemon (id bigserial primary key, user_id varchar(255), pokemon_id bigint)").executeUpdate();
            em.createNativeQuery("ALTER TABLE user_pokemon DROP CONSTRAINT if exists fk_user_id").executeUpdate();
            em.createNativeQuery("ALTER TABLE user_pokemon DROP CONSTRAINT if exists fk_pokemon_id").executeUpdate();
            em.createNativeQuery("ALTER TABLE user_pokemon ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users (username)").executeUpdate();
            em.createNativeQuery("ALTER TABLE user_pokemon ADD CONSTRAINT fk_pokemon_id FOREIGN KEY (pokemon_id) REFERENCES pokemon (id)").executeUpdate();
            em.getTransaction().commit();
        }
        catch (EntityExistsException | ConstraintViolationException ignored) {  }
        catch (Exception e) { logger.warn(e.getMessage()); }
    }

    @SuppressWarnings("unchecked")
    public static List<Pokemon> getPokemon(UserDTO userDTO)
    {
        List<Integer> poke_ids = new ArrayList<>();
        try (EntityManager em = EMF.createEntityManager())
        {
            em.getTransaction().begin();
            User user = em.find(User.class, userDTO.getUsername());
            if (user == null) throw new SQLException("no such user exists");

            poke_ids = (List<Integer>) em.createNativeQuery("select pokemon_id from user_pokemon where user_id = ?1", Integer.class)
                .setParameter(1, user.getUsername()).getResultList();
        }
        catch (EntityExistsException | ConstraintViolationException ignored) {  }
        catch (Exception e) { logger.warn(e.getMessage()); }

        List<Pokemon> user_pokemon = new ArrayList<>();
        for (Integer id : poke_ids)
            user_pokemon.add(PokemonDAO.getById(id));
        return user_pokemon;
    }

    public static void addPokemon(UserDTO userDTO, List<Pokemon> pokemon)
    {
        if (userDTO == null || pokemon == null) return;

        try (EntityManager em = EMF.createEntityManager())
        {
            em.getTransaction().begin();
            User user = em.find(User.class, userDTO.getUsername());
            if (user == null) throw new SQLException("no such user exists");

            for (Pokemon p : pokemon){
                em.createNativeQuery("insert into user_pokemon (id, user_id, pokemon_id) VALUES(DEFAULT, ?1, ?2)")
                    .setParameter(1, user.getUsername()).setParameter(2, p.id).executeUpdate();
            }

            em.getTransaction().commit();
        }
        catch (EntityExistsException | ConstraintViolationException ignored) {  }
        catch (Exception e) { logger.warn(e.getMessage()); }
    }

    public static void Populate()
    {
        try (EntityManager em = EMF.createEntityManager())
        {
            em.getTransaction().begin();
            User user = new User("user", "user");
            User admin = new User("admin", "admin");
            User superUser = new User("super", "super");
            Role userRole = new Role("user");
            Role adminRole = new Role("admin");
            user.addRole(userRole);
            admin.addRole(adminRole);
            superUser.addRole(userRole);
            superUser.addRole(adminRole);

            if (em.find(User.class, user.getUsername()) == null)
                em.persist(user);
            if (em.find(User.class, admin.getUsername()) == null)
                em.persist(admin);
            if (em.find(User.class, superUser.getUsername()) == null)
                em.persist(superUser);
            if (em.find(Role.class, userRole.getRoleName()) == null)
                em.persist(userRole);
            if (em.find(Role.class, adminRole.getRoleName()) == null)
                em.persist(adminRole);
            em.getTransaction().commit();
        }
        catch (EntityExistsException | ConstraintViolationException ignored) {  }
        catch (Exception e) { logger.warn(e.getMessage()); }
    }
}
