package dk.obhnothing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import dk.obhnothing.control.MasterController;
import dk.obhnothing.persistence.HibernateConfig;
import dk.obhnothing.persistence.dao.PokemonDAO;
import dk.obhnothing.persistence.dao.UserDAO;
import dk.obhnothing.persistence.dto.PokemonDTO;
import dk.obhnothing.persistence.ent.Pokemon;
import dk.obhnothing.persistence.service.Fetcher;
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

        PokemonDAO.Init(EMF);
        UserDAO.Init(EMF);
        UserDAO.Populate();

        try

        {

            //MasterController.start(9999);

            System.out.printf("%n%n[%s] listening...%n%n",
                    (System.getenv("DEPLOYED") == null && System.getenv("PRODUCTION") == null)
                    ? "development" : "deployed");

            /* TEST */

            PokemonDTO poke = Fetcher.fetchPokemon(2);
            Pokemon pokemon = Fetcher.fromDTO(poke);

            System.out.printf("%n%s found in:  %s%n", poke.name, poke.location_area_encounters);

            System.out.println(jsonMapper.writeValueAsString(poke.abilities));

            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();

            System.out.println(jsonMapper.writeValueAsString(pokemon));
            PokemonDAO.create(pokemon);


        }

        catch (Exception e)

        {

            logger.error(e.getMessage());
            EMF.close();

        }

    }

}





























