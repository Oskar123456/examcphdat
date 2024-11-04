package dk.obhnothing;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import dk.obhnothing.control.MasterController;
import dk.obhnothing.persistence.HibernateConfig;
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

        Random rng = new Random();

        try

        {

            MasterController.start(9999);

            System.out.printf("%n%n[%s] listening...%n%n",
                    (System.getenv("DEPLOYED") == null && System.getenv("PRODUCTION") == null)
                    ? "development" : "deployed");

            /* TEST */
            Populator.PopTrips(5);



        }

        catch (Exception e)

        {

            e.printStackTrace();
            EMF.close();

        }

    }

}





























