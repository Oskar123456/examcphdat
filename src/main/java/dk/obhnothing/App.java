package dk.obhnothing;

import java.io.PrintStream;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import dk.obhnothing.control.MasterController;
import dk.obhnothing.control.PlantController;
import dk.obhnothing.persistence.HibernateConfig;
import dk.obhnothing.persistence.dao.DoctorDAO;
import dk.obhnothing.persistence.dao.DoctorDAO_DB;
import dk.obhnothing.persistence.dao.HeadlineDAO;
import dk.obhnothing.persistence.dao.UserDAO;
import dk.obhnothing.persistence.dto.HeadlineListDTO;
import dk.obhnothing.persistence.service.Fetcher;
import dk.obhnothing.persistence.service.Mapper;
import dk.obhnothing.utilities.Utils;
import jakarta.persistence.EntityManagerFactory;

/*
 * Cph Business School....
 * Datamatiker 3. sem.....
 * -----------------------
 * Oskar Bahner Hansen....
 * cph-oh82@cphbusiness.dk
 * 2024-11-04.............
 * -----------------------
 */

public class App
{

    private static ObjectMapper jsonMapper = Utils.getObjectMapper();
    private static Logger logger = LoggerFactory.getLogger(App.class);
    private static EntityManagerFactory EMF;

    public static void main(String... args)
    {
        /* REDIRECT STDOUT/ERR */
        //PrintStream stdout = System.out;
        //PrintStream stderr = System.err;
        //System.setOut(new PrintStream(PrintStream.nullOutputStream()));
        //System.setErr(new PrintStream(PrintStream.nullOutputStream()));
        /* INIT */
        jsonMapper.enable(SerializationFeature.INDENT_OUTPUT);
        /* INIT HIBERNATE & DEPENDENTS */
        HibernateConfig.Init(HibernateConfig.Mode.DEV);
        EMF = HibernateConfig.getEntityManagerFactory();

        UserDAO.Init(EMF);
        UserDAO.Populate();

        Random rng = new Random();

        HeadlineDAO.Init(EMF);
        HeadlineDAO hd = new HeadlineDAO();

        try

        {

            if (hd.getAll().size() < 1) {
                HeadlineListDTO hs = Fetcher.headlines("us");
                System.out.println(jsonMapper.writeValueAsString(hs));
                Mapper.HeadlineListDTO_Extract(hs).forEach(hd::add);
            }

            MasterController.start(9999);

            System.out.printf("%n%n[%s] listening...%n%n",
                    (System.getenv("DEPLOYED") == null && System.getenv("PRODUCTION") == null)
                    ? "development" : "deployed");

            /* RESTORE STDOUT/ERR */

            /* TEST */

        }

        catch (Exception e)

        {

            e.printStackTrace();
            EMF.close();

        }

    }

}





























