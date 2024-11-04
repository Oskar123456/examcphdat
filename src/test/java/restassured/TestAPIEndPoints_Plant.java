package restassured;

import static dk.obhnothing.persistence.HibernateConfig.Mode.TEST;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dk.obhnothing.control.MasterController;
import dk.obhnothing.persistence.HibernateConfig;
import dk.obhnothing.persistence.dao.PlantDAO;
import dk.obhnothing.persistence.dao.UserDAO;
import dk.obhnothing.persistence.dto.PlantDTO;
import dk.obhnothing.persistence.ent.Plant;
import dk.obhnothing.persistence.service.Mapper;
import dk.obhnothing.utilities.Utils;
import io.javalin.Javalin;
import io.restassured.RestAssured;
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

public class TestAPIEndPoints_Plant
{

//    static Javalin jav;
//    static EntityManagerFactory EMF;
//    static PlantDAO plant_dao;
//    static String jwt_user = "";
//    static String jwt_admin = "";
//    static int port_test;
//    static ObjectMapper jsonMapper;
//    static int n_plants = 10;
//    static List<Plant> plants;
//
//    /******************/
//    /* SETUP/TEARDOWN */
//    /******************/
//
//    //@Rule public ExpectedException exCatcher = ExpectedException.none();
//    // USAGE: exCatcher.expect(ExpeptionType.class)
//    // --> do stuff
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
//        port_test = 9999;
//        jav = MasterController.start(port_test);
//    }
//
//    @AfterAll static void deinitAll()
//    {
//        jav.stop();
//    }
//
//    @BeforeEach void initEach()
//    {
//        plant_dao.deleteAll();
//
//        plants = Mapper.gen_Plants(n_plants);
//        plants.get(0).maxheight = 100;
//        plants.stream().peek(p -> p.planttype = "test_type").forEach(plant_dao::add);
//        plants = plant_dao.getAll();
//        assert(plants.size() >= n_plants);
//
//        logout();
//    }
//
//    @AfterEach void deinitEach()
//    {
//        logout();
//    }
//
//    /******************/
//    /* TESTING BEGINS */
//    /******************/
//
//    @Test void testLoginUser()
//    {
//        RestAssured.given().port(port_test)
//            .header("Authorization", "Bearer " + jwt_user)
//            .when().get("/api/protected/user_demo")
//            .then().assertThat().statusCode(401);
//        loginUser();
//        RestAssured.given().port(port_test)
//            .header("Authorization", "Bearer " + jwt_user)
//            .when().get("/api/protected/user_demo")
//            .then().assertThat().statusCode(200);
//    }
//
//    @Test void testLoginAdmin()
//    {
//        RestAssured.given().port(port_test)
//            .header("Authorization", "Bearer " + jwt_admin)
//            .when().get("/api/protected/admin_demo")
//            .then().assertThat().statusCode(401);
//        loginAdmin();
//        RestAssured.given().port(port_test)
//            .header("Authorization", "Bearer " + jwt_admin)
//            .when().get("/api/protected/admin_demo")
//            .then().assertThat().statusCode(200);
//    }
//
//    /******************/
//    /* TESTING BEGINS */
//    /******************/
//
//    /******************/
//    /* TASK 6 *********/
//    /******************/
//
//    @Test @DisplayName("getAll") void testgetAll() // redundant
//    {
//        PlantDTO[] plant_dtos = RestAssured.given().port(port_test)
//            .when().get("/api/plants")
//            .then().assertThat().statusCode(200)
//            .extract().body().as(PlantDTO[].class);
//        plants = Arrays.stream(plant_dtos).map(Mapper::PlantDTO_Plant).toList();
//        assert(plants.size() >= n_plants);
//    }
//
//    @Test @DisplayName("getById") void testgetById()
//    {
//        for (Plant p : plants) {
//            PlantDTO plant_dto = RestAssured.given().port(port_test)
//                .when().get("/api/plants/" + p.id)
//                .then().assertThat().statusCode(200)
//                .extract().body().as(PlantDTO.class);
//            Plant plant_dto_to_ent = Mapper.PlantDTO_Plant(plant_dto);
//            assert(plant_dto_to_ent.equals(p)); // this breaks if representation changes for one without the other
//            assert(plant_dto_to_ent.id.equals(p.id));
//        }
//    }
//
//    @Test @DisplayName("getByType") void testgetByType()
//    {
//        PlantDTO[] plant_dtos = RestAssured.given().port(port_test)
//            .when().get("/api/plants/type/" + "test_type")
//            .then().assertThat().statusCode(200)
//            .extract().body().as(PlantDTO[].class);
//        List<Plant> plants_dtos_to_ents = Arrays.stream(plant_dtos).map(Mapper::PlantDTO_Plant).toList();
//        for (Plant p : plants_dtos_to_ents)
//            assert(plants.contains(p));
//        plant_dtos = RestAssured.given().port(port_test)
//            .when().get("/api/plants/type/" + "doesnt exist")
//            .then().assertThat().statusCode(200)
//            .extract().body().as(PlantDTO[].class);
//        assert(plant_dtos.length == 0);
//    }
//
//    @Test @DisplayName("add without admin privilege") void testaddfail()
//    {
//        try {
//            PlantDTO p = Mapper.gen_PlantDTO();
//            loginUser();
//            RestAssured.given().port(port_test)
//                .header("Authorization", "Bearer " + jwt_user)
//                .contentType("application/json")
//                .body(jsonMapper.writeValueAsString(p))
//                .when().post("/api/plant")
//                .then().assertThat().statusCode(401);
//        }
//        catch (JsonProcessingException e) {
//            assert(false);
//        }
//    }
//
//    @Test @DisplayName("add with admin privilege") void testadd()
//    {
//        try {
//            PlantDTO p = Mapper.gen_PlantDTO();
//
//            loginAdmin();
//            PlantDTO plant_dto = RestAssured.given().port(port_test)
//                .header("Authorization", "Bearer " + jwt_admin)
//                .contentType("application/json")
//                .body(jsonMapper.writeValueAsString(p))
//                .when().post("/api/plant")
//                .then().assertThat().statusCode(201)
//                .extract().body().as(PlantDTO.class);
//
//            p.id = plant_dto.id; // for proper equals
//            PlantDTO p_found = Mapper.Plant_PlantDTO(plant_dao.getById(plant_dto.id));
//            assertEquals(plant_dto, p_found);
//            assertEquals(p, p_found);
//        }
//        catch (JsonProcessingException e) {
//            assert(false);
//        }
//    }
//
//    @Test @DisplayName("getMaxHeightIs100") void testgetMaxHeightIs100()
//    {
//        Plant plant_with_100_mh = plants.get(0);
//        PlantDTO plantdto_with_100_mh = Mapper.Plant_PlantDTO(plant_with_100_mh);
//    }
//
//    @Test @DisplayName("deletePlant") void testdeletePlant()
//    {
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
//
//    static void loginUser()
//    {
//        String user_json = jsonMapper.createObjectNode()
//            .put("username", "user").put("password", "user").toString();
//        jwt_user = RestAssured.given().port(port_test).contentType("application/json").body(user_json)
//            .when().post("/api/auth/login")
//            .then().extract().path("token");
//    }
//
//    static void loginAdmin()
//    {
//        String admin_json = jsonMapper.createObjectNode()
//            .put("username", "admin").put("password", "admin").toString();
//        jwt_admin = RestAssured.given().port(port_test).contentType("application/json").body(admin_json)
//            .when().post("/api/auth/login")
//            .then().extract().path("token");
//    }
//
//    static void logout() { jwt_user = ""; jwt_admin = ""; }

}
