package dk.obhnothing.persistence.dao;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dk.obhnothing.security.entities.Role;
import dk.obhnothing.security.entities.User;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class UserDAO
{

    private static Logger logger = LoggerFactory.getLogger(DoctorDAO_DB.class);
    private static EntityManagerFactory EMF;
    public static void Init(EntityManagerFactory e) {EMF = e;}

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
