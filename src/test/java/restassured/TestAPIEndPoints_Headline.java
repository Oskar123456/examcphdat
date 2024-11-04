package restassured;

import static dk.obhnothing.persistence.HibernateConfig.Mode.TEST;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import dk.obhnothing.control.MasterController;
import dk.obhnothing.persistence.HibernateConfig;
import dk.obhnothing.persistence.dao.HeadlineDAO;
import dk.obhnothing.persistence.dao.UserDAO;
import dk.obhnothing.persistence.ent.Headline;
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

public class TestAPIEndPoints_Headline
{

    static Javalin jav;
    static EntityManagerFactory EMF;
    static HeadlineDAO headline_dao;
    static String jwt_user = "";
    static String jwt_admin = "";
    static int port_test;
    static ObjectMapper jsonMapper;
    static int n_plants = 10;
    static List<Headline> headlines;

    /******************/
    /* SETUP/TEARDOWN */
    /******************/

    //@Rule public ExpectedException exCatcher = ExpectedException.none();
    // USAGE: exCatcher.expect(ExpeptionType.class)
    // --> do stuff

    @BeforeAll static void init()
    {
        jsonMapper = Utils.getObjectMapper();

        HibernateConfig.Init(TEST);
        EMF = HibernateConfig.getEntityManagerFactory();

        UserDAO.Init(EMF);
        UserDAO.Populate();

        HeadlineDAO.Init(EMF);
        headline_dao = new HeadlineDAO();

        port_test = 9999;
        jav = MasterController.start(port_test);
    }

    @AfterAll static void deinitAll()
    {
        jav.stop();
    }

    @BeforeEach void initEach()
    {
        logout();
    }

    @AfterEach void deinitEach()
    {
        logout();
    }

    /******************/
    /* TESTING BEGINS */
    /******************/

    @Test void testLoginUser()
    {
        RestAssured.given().port(port_test)
            .header("Authorization", "Bearer " + jwt_user)
            .when().get("/api/protected/user_demo")
            .then().assertThat().statusCode(401);
        loginUser();
        RestAssured.given().port(port_test)
            .header("Authorization", "Bearer " + jwt_user)
            .when().get("/api/protected/user_demo")
            .then().assertThat().statusCode(200);
    }

    @Test void testLoginAdmin()
    {
        RestAssured.given().port(port_test)
            .header("Authorization", "Bearer " + jwt_admin)
            .when().get("/api/protected/admin_demo")
            .then().assertThat().statusCode(401);
        loginAdmin();
        RestAssured.given().port(port_test)
            .header("Authorization", "Bearer " + jwt_admin)
            .when().get("/api/protected/admin_demo")
            .then().assertThat().statusCode(200);
    }

    /******************/
    /* TESTING BEGINS */
    /******************/

    /******************/
    /* TASK 6 *********/
    /******************/

    @Test @DisplayName("getAll") void testgetAll() // redundant
    {
        assert(false);
    }

    @Test @DisplayName("getById") void testgetById()
    {
        assert(false);
    }

    @Test @DisplayName("add without admin privilege") void testaddfail()
    {
        assert(false);
    }

    @Test @DisplayName("add with admin privilege") void testadd()
    {
        assert(false);
    }

    @Test @DisplayName("deletePlant") void testdeletePlant()
    {
        assert(false);
    }

    /******************/
    /* TESTING ENDS ***/
    /******************/

    static void loginUser()
    {
        String user_json = jsonMapper.createObjectNode()
            .put("username", "user").put("password", "user").toString();
        jwt_user = RestAssured.given().port(port_test).contentType("application/json").body(user_json)
            .when().post("/api/auth/login")
            .then().extract().path("token");
    }

    static void loginAdmin()
    {
        String admin_json = jsonMapper.createObjectNode()
            .put("username", "admin").put("password", "admin").toString();
        jwt_admin = RestAssured.given().port(port_test).contentType("application/json").body(admin_json)
            .when().post("/api/auth/login")
            .then().extract().path("token");
    }

    static void logout() { jwt_user = ""; jwt_admin = ""; }

}

