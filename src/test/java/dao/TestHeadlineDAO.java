package dao;

import static dk.obhnothing.persistence.HibernateConfig.Mode.TEST;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import dk.obhnothing.persistence.HibernateConfig;
import dk.obhnothing.persistence.dao.HeadlineDAO;
import dk.obhnothing.persistence.dao.UserDAO;
import dk.obhnothing.persistence.dto.HeadlineListDTO;
import dk.obhnothing.persistence.ent.Headline;
import dk.obhnothing.persistence.ent.Plant;
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

public class TestHeadlineDAO
{

    static EntityManagerFactory EMF;
    static HeadlineDAO headline_dao;
    static String jwt_user = "";
    static String jwt_admin = "";
    static ObjectMapper jsonMapper;
    static int n_plants = 10;
    static List<Headline> headlines;

    /******************/
    /* SETUP/TEARDOWN */
    /******************/

    @BeforeAll static void init()
    {
        jsonMapper = Utils.getObjectMapper();

        HibernateConfig.Init(TEST);
        EMF = HibernateConfig.getEntityManagerFactory();

        UserDAO.Init(EMF);
        UserDAO.Populate();

        HeadlineDAO.Init(EMF);
        headline_dao = new HeadlineDAO();

        assert(n_plants > 0);
    }

    @AfterAll static void deinit()
    {
    }

    @BeforeEach void repopulate()
    {
        headline_dao.deleteAll();
        try {
            if (headline_dao.getAll().size() < 1) {
                HeadlineListDTO hs = Fetcher.headlines("us");
                System.out.println(jsonMapper.writeValueAsString(hs));
                Mapper.HeadlineListDTO_Extract(hs).forEach(headline_dao::add);
            }
            headlines = headline_dao.getAll();
            assert(headlines.size() >= n_plants);
        }
        catch (Exception e) { assert(false); }
    }

    /******************/
    /* TESTING BEGINS */
    /******************/

    /******************/
    /* TASK 5 *********/
    /******************/

    @Test @DisplayName("getAll") void testgetAll() // redundant
    {
        headlines = headline_dao.getAll();
        assert(headlines.size() >= 1);
    }

    @Test @DisplayName("getById") void testgetById()
    {
        for (Headline h : headlines) {
            Headline from_dao = headline_dao.getById(h.id);
            assertNotNull(from_dao);
            assert(from_dao.id == h.id);
        }
        Headline from_dao = headline_dao.getById(-1);
        assertNull(from_dao);
    }

    @Test @DisplayName("add") void testadd()
    {
        try {
            headlines = headline_dao.getAll();
            int prev_size = headlines.size();
            HeadlineListDTO hs = Fetcher.headlines("us");
            int fetched_size = hs.articles.length;
            assert(fetched_size > 0);
            Mapper.HeadlineListDTO_Extract(hs).forEach(headline_dao::add);
            headlines = headline_dao.getAll();
            int new_size = headlines.size();
            assert(new_size == prev_size + fetched_size);
        }
        catch (Exception e) { assert(false); }
    }

    @Test @DisplayName("deleteAll") void testdeletePlant()
    {
        try {
            headlines = headline_dao.getAll();
            int prev_size = headlines.size();
            assert(prev_size > 0);
            headline_dao.deleteAll();
            headlines = headline_dao.getAll();
            int new_size = headlines.size();
            assert(new_size == 0);
        }
        catch (Exception e) { assert(false); }
    }

    /******************/
    /* TESTING ENDS ***/
    /******************/

}

