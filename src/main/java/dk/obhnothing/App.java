package dk.obhnothing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import dk.obhnothing.control.MasterController;
import dk.obhnothing.persistence.HibernateConfig;
import dk.obhnothing.persistence.dao.GuideDAO;
import dk.obhnothing.persistence.dao.TripDAO;
import dk.obhnothing.persistence.dao.UserDAO;
import dk.obhnothing.persistence.service.Populator;
import dk.obhnothing.utilities.Utils;
import jakarta.persistence.EntityManagerFactory;

public class App
{

    private static ObjectMapper jsonMapper = Utils.getObjectMapper();
    private static Logger logger = LoggerFactory.getLogger(App.class);
    private static EntityManagerFactory EMF;

    public static void main(String... args)
    {
        /* REDIRECT STDOUT/ERR */
        /* INIT */
        jsonMapper.enable(SerializationFeature.INDENT_OUTPUT);
        /* INIT HIBERNATE & DEPENDENTS */
        HibernateConfig.Init(HibernateConfig.Mode.DEV);
        EMF = HibernateConfig.getEntityManagerFactory();

        UserDAO.Init(EMF);
        UserDAO.Populate();
        GuideDAO.Init(EMF);
        TripDAO.Init(EMF);
        TripDAO tripDAO = new TripDAO();
        if (tripDAO.getAll().size() < 25)
            Populator.PopTrips(20);

        try

        {

            MasterController.start(9999);

            System.out.printf("%n%n[%s] listening...%n%n",
                    (System.getenv("DEPLOYED") == null && System.getenv("PRODUCTION") == null)
                    ? "development" : "deployed");

            /* TEST */



        }

        catch (Exception e)

        {

            logger.error(e.getMessage());
            EMF.close();

        }

    }

}





























