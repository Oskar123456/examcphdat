package dao;

import static dk.obhnothing.persistence.HibernateConfig.Mode.TEST;
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
import dk.obhnothing.persistence.dao.PlantDAO;
import dk.obhnothing.persistence.dao.UserDAO;
import dk.obhnothing.persistence.ent.Plant;
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

public class TestPlantDAO
{

//    static EntityManagerFactory EMF;
//    static PlantDAO plant_dao;
//    static String jwt_user = "";
//    static String jwt_admin = "";
//    static ObjectMapper jsonMapper;
//    static int n_plants = 10;
//    static List<Plant> plants;
//
//    /******************/
//    /* SETUP/TEARDOWN */
//    /******************/
//
//    @BeforeAll static void init()
//    {
//        jsonMapper = Utils.getObjectMapper();
//
//        HibernateConfig.Init(TEST);
//        EMF = HibernateConfig.getEntityManagerFactory();
//
//        UserDAO.Init(EMF);
//        UserDAO.Populate();
//
//        PlantDAO.Init(EMF);
//        plant_dao = new PlantDAO();
//
//        assert(n_plants > 0);
//    }
//
//    @AfterAll static void deinit()
//    {
//    }
//
//    @BeforeEach void repopulate()
//    {
//        plant_dao.deleteAll();
//
//        plants = Mapper.gen_Plants(n_plants);
//
//        plants.get(0).maxheight = 100;
//
//        plants.stream().peek(p -> p.planttype = "test_type").forEach(plant_dao::add);
//
//        plants = plant_dao.getAll();
//        assert(plants.size() >= n_plants);
//    }
//
//    /******************/
//    /* TESTING BEGINS */
//    /******************/
//
//    /******************/
//    /* TASK 5 *********/
//    /******************/
//
//    @Test @DisplayName("getAll") void testgetAll() // redundant
//    {
//        plants = plant_dao.getAll();
//        assert(plants.size() >= n_plants);
//    }
//
//    @Test @DisplayName("getById") void testgetById()
//    {
//        for (Plant plant : plants) {
//            int id = plant.id;
//            Plant plant_from_read = plant_dao.getById(id);
//            assertNotNull(plant_from_read);
//            assertEquals(id, plant_from_read.id);
//        }
//    }
//
//    @Test @DisplayName("getByType") void testgetByType()
//    {
//        List<Plant> plants_from_dao = plant_dao.getByType("test_type");
//        for (Plant p : plants_from_dao)
//            assert(plants.contains(p));
//    }
//
//    @Test @DisplayName("add") void testadd()
//    {
//        Plant p = Mapper.gen_Plant();
//        Plant p_added = plant_dao.add(p);
//        Plant p_found = plant_dao.getById(p_added.id);
//        assertEquals(p_added, p_found);
//        assertEquals(p.name, p_found.name);
//        assertEquals(p.maxheight, p_found.maxheight);
//        assertEquals(p.planttype, p_found.planttype);
//        assertEquals(p.price, p_found.price);
//    }
//
//    @Test @DisplayName("getMaxHeightIs100") void testgetMaxHeightIs100()
//    {
//        List<Plant> plants_from_dao = plant_dao.getMaxHeightIs100();
//        assert(plants_from_dao.size() > 0);
//        Plant p = Mapper.gen_Plant();
//        p.maxheight = 100;
//        plant_dao.add(p);
//        List<Plant> plants_from_dao_after_add = plant_dao.getMaxHeightIs100();
//        assert(plants_from_dao.size() + 1 == plants_from_dao_after_add.size());
//    }
//
//    @Test @DisplayName("deletePlant") void testdeletePlant()
//    {
//        plants.stream().forEach(p -> plant_dao.deletePlant(p.id));
//        plants = plant_dao.getAll();
//        assert(plants.size() == 0);
//    }
//
//    @Test @DisplayName("addPlantToReseller") void testaddPlantToReseller()
//    {
//    }
//
//    @Test @DisplayName("getPlantsByReseller") void testgetPlantsByReseller()
//    {
//    }
//
//    /******************/
//    /* TESTING ENDS ***/
//    /******************/

}
