package restassured;

import static dk.obhnothing.persistence.HibernateConfig.Mode.TEST;

import java.util.ArrayList;
import java.util.Arrays;
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
import dk.obhnothing.persistence.dao.TripDAO;
import dk.obhnothing.persistence.dao.UserDAO;
import dk.obhnothing.persistence.dto.TripDTO;
import dk.obhnothing.persistence.ent.Trip;
import dk.obhnothing.persistence.service.Mapper;
import dk.obhnothing.persistence.service.Populator;
import dk.obhnothing.security.exceptions.ApiException;
import dk.obhnothing.utilities.Utils;
import io.javalin.Javalin;
import io.restassured.RestAssured;
import jakarta.persistence.EntityManagerFactory;

public class TestAPIEndPoints_Trip
{

    static Javalin jav;
    static EntityManagerFactory EMF;
    static TripDAO trip_dao;
    static String jwt_user = "";
    static String jwt_admin = "";
    static int port_test;
    static ObjectMapper jsonMapper;
    static int n_plants = 10;
    static List<Trip> trips;

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

        trip_dao = new TripDAO();

        port_test = 9999;
        jav = MasterController.start(port_test);

        Populator.PopTrips(5);
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

    @Test @DisplayName("getById") void testgetById()
    {
        for (Trip t : trips) {
            TripDTO trip_dto = RestAssured.given().port(port_test)
                .when().get("/api/trips/" + t.id)
                .then().assertThat().statusCode(200)
                .extract().body().as(TripDTO.class);
            assert(t.id.equals(trip_dto.id));
        }
        int status = RestAssured.given().port(port_test)
            .when().get("/api/trips/aoe")
            .body().jsonPath().getInt("status");
        assert(status == 404);
    }


    @Test @DisplayName("deletePlant") void testdeletePlant()
    {
        loginAdmin();
        int status = RestAssured.given().port(port_test)
            .header("Authorization", "Bearer " + jwt_admin)
            .when().delete("/api/trips/" + 2053)
            .body().jsonPath().getInt("status");
        assert(status == 404);
    }

    @Test @DisplayName("getAll") void testgetAll() // redundant
    {
        TripDTO[] trip_dtos = RestAssured.given().port(port_test)
            .when().get("/api/trips")
            .then().assertThat().statusCode(200)
            .extract().body().as(TripDTO[].class);
        assert(trip_dtos.length > 0);
        trips = Arrays.stream(trip_dtos).map(Mapper::TripDTO_Trip).toList();
        assert(trips.size() > 0);
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
