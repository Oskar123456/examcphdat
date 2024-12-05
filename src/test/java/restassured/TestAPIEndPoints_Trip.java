package restassured;

import static dk.obhnothing.persistence.HibernateConfig.Mode.TEST;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import dk.obhnothing.control.MasterController;
import dk.obhnothing.persistence.HibernateConfig;
import dk.obhnothing.persistence.dao.GuideDAO;
import dk.obhnothing.persistence.dao.TripDAO;
import dk.obhnothing.persistence.dao.UserDAO;
import dk.obhnothing.persistence.dto.GuideDTO;
import dk.obhnothing.persistence.dto.PackingList;
import dk.obhnothing.persistence.dto.PackingOption;
import dk.obhnothing.persistence.dto.TripDTO;
import dk.obhnothing.persistence.ent.Trip;
import dk.obhnothing.persistence.enums.Category;
import dk.obhnothing.persistence.service.Fetcher;
import dk.obhnothing.persistence.service.Mapper;
import dk.obhnothing.persistence.service.Populator;
import dk.obhnothing.security.exceptions.ApiException;
import dk.obhnothing.utilities.Utils;
import io.javalin.Javalin;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import jakarta.persistence.EntityManagerFactory;

public class TestAPIEndPoints_Trip
{

    static Javalin jav;
    static EntityManagerFactory EMF;
    static TripDAO trip_dao;
    static GuideDAO guide_dao;
    static String jwt_user = "";
    static String jwt_admin = "";
    static int port_test;
    static ObjectMapper jsonMapper;
    static int n_plants = 10;
    static List<TripDTO> trips;

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
        TripDAO.Init(EMF);
        GuideDAO.Init(EMF);
        UserDAO.Populate();

        trip_dao = new TripDAO();
        guide_dao = new GuideDAO();

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
        trip_dao.deleteAll();
        Populator.PopTrips(5);
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
    }

    @Test @DisplayName("delete by id") void testDeleteById()
    {
        loginAdmin();
        trips = trip_dao.getAll();
        int n_trips_orig = trips.size();
        int n_trips_del = 0;
        for (TripDTO t : trips) {
            RestAssured.given().port(port_test)
                .header("Authorization", "Bearer " + jwt_admin)
                .when().delete("/api/trips/" + t.id)
                .then().assertThat().statusCode(200);
            n_trips_del++;
            int n_trips_now = trip_dao.getAll().size();
            assert(n_trips_now + n_trips_del == n_trips_orig);
        }
    }

    @Test @DisplayName("getAll") void testgetAll() // redundant
    {
        trips = trip_dao.getAll();
        TripDTO[] trip_dtos = RestAssured.given().port(port_test)
            .when().get("/api/trips")
            .then().assertThat().statusCode(200)
            .extract().body().as(TripDTO[].class);
        for (TripDTO dto_from_api : trip_dtos) {
            assert(trips.contains(dto_from_api));
        }
    }

    @Test @DisplayName("get by category") void testGetByCat() // redundant
    {
        int trips_total = trip_dao.getAll().size();
        int trips_retrieved = 0;
        for (Category cat : Category.values()) {
            TripDTO[] trip_dtos = RestAssured.given().port(port_test)
                .when().get("/api/trips/category/" + cat.toString())
                .then().assertThat().statusCode(200)
                .extract().body().as(TripDTO[].class);
            trips_retrieved += trip_dtos.length;
            for (TripDTO dto : trip_dtos)
                assert(dto.category == cat);
        }
        assert(trips_total == trips_retrieved);
    }

    @Test @DisplayName("post trip") void testPostTrip() // redundant
    {
        int n_trips = trips.size();
        int n_new_trips = 5;
        List<TripDTO> trips_new = Populator.createTrips(n_new_trips).stream().map(Mapper::Trip_TripDTO).toList();

        try {
            for (TripDTO dto : trips_new) {
                RestAssured.given().port(port_test)
                    .header("Authorization", "Bearer " + jwt_admin)
                    .contentType("application/json")
                    .body(Utils.getObjectMapper().writeValueAsString(dto))
                    .when().post("/api/trips")
                    .then().assertThat().statusCode(401);
            }
        } catch (Exception e) { assert(false); }

        loginAdmin();

        try {
            for (TripDTO dto : trips_new) {
                TripDTO trip_dto = RestAssured.given().port(port_test)
                    .header("Authorization", "Bearer " + jwt_admin)
                    .contentType("application/json")
                    .body(Utils.getObjectMapper().writeValueAsString(dto))
                    .when().post("/api/trips")
                    .then().assertThat().statusCode(201)
                    .extract().body().as(TripDTO.class);
                dto.id = trip_dto.id;
                assert(dto.equals(trip_dto));
            }
        } catch (Exception e) { assert(false); }

        trips = trip_dao.getAll();
        assert(trips.size() == n_trips + n_new_trips);
    }

    @Test @DisplayName("put trip") void testPutTrip() // redundant
    {
        loginAdmin();
        trips = trip_dao.getAll();
        try {
            for (TripDTO dto : trips) {
                int id = dto.id;
                dto.id = null;
                dto.name = "TEST NAME";
                TripDTO trip_dto = RestAssured.given().port(port_test)
                    .header("Authorization", "Bearer " + jwt_admin)
                    .contentType("application/json")
                    .body(Utils.getObjectMapper().writeValueAsString(dto))
                    .when().put("/api/trips/" + id)
                    .then().assertThat().statusCode(200)
                    .extract().body().as(TripDTO.class);
                assert(trip_dto.name.equals("TEST NAME"));
            }
        } catch (Exception e) { assert(false); }

        trips = trip_dao.getAll();
        for (TripDTO dto : trips) {
            assert(dto.name.equals("TEST NAME"));
        }
    }

    @Test @DisplayName("put guide in trip") void testPutGuideInTrip() // redundant
    {
        loginAdmin();
        List<GuideDTO> guides = guide_dao.getAll();
        GuideDTO guide = guides.get(new Random().nextInt(0, guides.size()));
        trips = trip_dao.getAll();

        try {
            for (TripDTO dto : trips) {
                RestAssured.given().port(port_test)
                    .header("Authorization", "Bearer " + jwt_admin)
                    .when().put("/api/trips/" + dto.id + "/guides/" + guide.id)
                    .then().assertThat().statusCode(200);
            }
        } catch (Exception e) { assert(false); }

        trips = trip_dao.getAll();
        for (TripDTO dto : trips)
            assert(dto.guide.equals(guide));
    }

    @Test @DisplayName("get sum of each guide") void testgetSumOfEachGuide() // redundant
    {
        List<GuideDTO> guides = guide_dao.getAll();
        trips = trip_dao.getAll();
        Map<Integer, Double> guide_sums = new HashMap<>();

        for (TripDTO trip : trips) {
            if (trip.price != null && trip.guide != null && trip.guide.id != null)
                guide_sums.put(trip.guide.id,
                        guide_sums.containsKey(trip.guide.id)
                        ? guide_sums.get(trip.guide.id) + trip.price
                        : trip.price);
        }

        try {
            JsonPath total_sum = RestAssured.given().port(port_test)
                .header("Authorization", "Bearer " + jwt_admin)
                .when().get("/api/trips/guidespricesum")
                .then().assertThat().statusCode(200)
                .extract().body().jsonPath();
            for (GuideDTO g : guides) {
                Float sum = total_sum.get(g.id.toString());
                if (sum != null)
                    assert(Math.abs(guide_sums.get(g.id).floatValue() - sum) < 0.01);
            }
        } catch (Exception e) { System.err.println(e.getMessage()); assert(false); }
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
