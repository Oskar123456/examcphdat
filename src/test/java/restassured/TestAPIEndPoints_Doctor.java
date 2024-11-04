package restassured;

import static dk.obhnothing.persistence.HibernateConfig.Mode.TEST;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import dk.obhnothing.control.MasterController;
import dk.obhnothing.persistence.HibernateConfig;
import dk.obhnothing.persistence.dao.DoctorDAO_DB;
import dk.obhnothing.persistence.dao.UserDAO;
import dk.obhnothing.persistence.dto.DoctorDTO;
import dk.obhnothing.persistence.ent.DoctorEnt;
import dk.obhnothing.persistence.enums.DoctorSpeciality;
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

public class TestAPIEndPoints_Doctor
{

//    static Javalin jav;
//    static EntityManagerFactory EMF;
//    static DoctorDAO_DB doc_dao;
//    static String jwt_user = "";
//    static String jwt_admin = "";
//    static int port_test;
//    static ObjectMapper jsonMapper;
//    static int n_docs = 10;
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
//        DoctorDAO_DB.Init(EMF);
//        doc_dao = new DoctorDAO_DB();
//
//        port_test = 9999;
//        jav = MasterController.start(port_test);
//    }
//
//    @AfterAll static void deinit()
//    {
//        jav.stop();
//    }
//
//    @BeforeEach void repopulate()
//    {
//        doc_dao.deleteAll();
//        doc_dao.populate(n_docs);
//        List<DoctorDTO> docs = doc_dao.readAll();
//        assert(docs.size() >= n_docs);
//    }
//
//    /******************/
//    /* TESTING BEGINS */
//    /******************/
//
//    @Test void testLogin()
//    {
//        RestAssured.given().port(port_test)
//            .header("Authorization", "Bearer " + jwt_user)
//            .when().get("/protected/user_demo")
//            .then().assertThat().statusCode(401);
//        loginUser();
//        RestAssured.given().port(port_test)
//            .header("Authorization", "Bearer " + jwt_user)
//            .when().get("/protected/user_demo")
//            .then().assertThat().statusCode(200);
//        RestAssured.given().port(port_test)
//            .header("Authorization", "Bearer " + jwt_admin)
//            .when().get("/protected/admin_demo")
//            .then().assertThat().statusCode(401);
//        loginAdmin();
//        RestAssured.given().port(port_test)
//            .header("Authorization", "Bearer " + jwt_admin)
//            .when().get("/protected/admin_demo")
//            .then().assertThat().statusCode(200);
//    }
//
//    /******************/
//    /* TASK 6 *********/
//    /******************/
//
//    @Test @DisplayName("restassured::readAll") void testreadAll_restAssured()
//    {
//        DoctorDTO[] docs = RestAssured.given().port(port_test)
//            .when().get("/api/doctors")
//            .then().extract().body().as(DoctorDTO[].class);
//        assert(docs.length >= n_docs);
//    }
//
//    @Test @DisplayName("restassured::read") void testread_restAssured()
//    {
//        DoctorDTO[] docs = RestAssured.given().port(port_test)
//            .when().get("/api/doctors")
//            .then().extract().body().as(DoctorDTO[].class);
//        assert(docs.length >= n_docs);
//        for (DoctorDTO doc : docs) {
//            DoctorDTO doc_from_api = RestAssured.given().port(port_test)
//                .when().get("/api/doctor/" + doc.id)
//                .then().extract().body().as(DoctorDTO.class);
//            assert(doc.equals(doc_from_api));
//        }
//    }
//
//    @Test @DisplayName("restassured::doctorBySpeciality") void testdoctorBySpeciality_restAssured()
//    {
//        for (DoctorSpeciality spec : DoctorSpeciality.values()) {
//            DoctorDTO[] docs_with_spec = RestAssured.given().port(port_test)
//                .when().get("/api/doctors/speciality/" + spec)
//                .then().extract().body().as(DoctorDTO[].class);
//            for (DoctorDTO doc : docs_with_spec)
//                assert(doc.speciality == spec);
//        }
//    }
//
//    @Test @DisplayName("restassured::doctorByBirthdateRange") void testdoctorByBirthdateRange_restAssured()
//    {
//        int n_tests = 10;
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        for (int i = 0; i < n_tests; i++) {
//            Random rng = new Random();
//            LocalDate from = LocalDate.now().minusYears(rng.nextInt(0, 100))
//                .minusMonths(rng.nextInt(0, 13)).minusDays(rng.nextInt(0, 32));
//            LocalDate to = from.plusYears(rng.nextInt(0, 100))
//                .plusMonths(rng.nextInt(0, 13)).plusDays(rng.nextInt(0, 32));
//            DoctorDTO[] docs = RestAssured.given().port(port_test)
//                .when().get("/api/doctors/birthdate/range?"
//                        + "from=" + dtf.format(from) + "&to=" + dtf.format(to))
//                .then().extract().body().as(DoctorDTO[].class);
//            for (DoctorDTO doc : docs) {
//                assert(doc.date_of_birth.equals(from) || doc.date_of_birth.equals(from)
//                        || (doc.date_of_birth.isAfter(from) && doc.date_of_birth.isBefore(to)));
//            }
//        }
//    }
//
//    @Test @DisplayName("restassured::create") void testcreate_restAssured()
//    {
//        String test_name = "__TESTNAME__";
//        DoctorEnt doc = new DoctorEnt();
//        doc.name = test_name;
//
//        try {
//            loginUser();
//            RestAssured.given().port(port_test)
//                .header("Authorization", "Bearer " + jwt_user)
//                .contentType("application/json").body(jsonMapper.writeValueAsString(Mapper.DoctorEnt_DoctorDTO(doc)))
//                .when().post("/api/doctors/")
//                .then().statusCode(401);
//            loginAdmin();
//            DoctorDTO doc_dto = RestAssured.given().port(port_test)
//                .header("Authorization", "Bearer " + jwt_admin)
//                .contentType("application/json").body(jsonMapper.writeValueAsString(Mapper.DoctorEnt_DoctorDTO(doc)))
//                .when().post("/api/doctors/")
//                .then().statusCode(201).extract().body().as(DoctorDTO.class);
//
//            assertNotNull(doc_dto);
//            assert(doc.name.equals(doc_dto.name));
//            DoctorDTO doc_dto_from_db = doc_dao.read(doc_dto.id);
//            assert(doc.name.equals(doc_dto_from_db.name));
//        }
//        catch (Exception e) { assert(false); }
//    }
//
//    @Test @DisplayName("restassured::update") void testupdate_restAssured()
//    {
//        String test_name = "__TESTNAME__";
//
//        DoctorDTO[] docs = RestAssured.given().port(port_test)
//            .when().get("/api/doctors")
//            .then().extract().body().as(DoctorDTO[].class);
//        assert(docs.length >= n_docs);
//
//        for (DoctorDTO doc : docs) {
//            doc.name_of_clinic = test_name;
//            try {
//                loginUser();
//                RestAssured.given().port(port_test)
//                    .header("Authorization", "Bearer " + jwt_user)
//                    .contentType("application/json").body(jsonMapper.writeValueAsString(doc))
//                    .when().put("/api/doctor/" + doc.id)
//                    .then().statusCode(401);
//                loginAdmin();
//                RestAssured.given().port(port_test)
//                    .header("Authorization", "Bearer " + jwt_admin)
//                    .contentType("application/json").body(jsonMapper.writeValueAsString(doc))
//                    .when().put("/api/doctor/" + doc.id)
//                    .then().statusCode(200).extract().body().as(DoctorDTO.class);
//            }
//            catch (Exception e) { assert(false); }
//        }
//
//        docs = RestAssured.given().port(port_test)
//            .when().get("/api/doctors")
//            .then().extract().body().as(DoctorDTO[].class);
//        assert(docs.length >= n_docs);
//
//        for (DoctorDTO doc : docs)
//            assert(doc.name_of_clinic.equals(test_name));
//    }
//
//    @Test @DisplayName("restassured::delete") void testdelete_restAssured()
//    {
//        DoctorDTO[] docs = RestAssured.given().port(port_test)
//            .when().get("/api/doctors")
//            .then().extract().body().as(DoctorDTO[].class);
//        assert(docs.length >= n_docs);
//
//        for (DoctorDTO doc : docs) {
//            loginUser();
//            RestAssured.given().port(port_test)
//                .header("Authorization", "Bearer " + jwt_user)
//                .when().delete("/api/doctors/" + doc.id)
//                .then().statusCode(401);
//            loginAdmin();
//            RestAssured.given().port(port_test)
//                .header("Authorization", "Bearer " + jwt_admin)
//                .when().delete("/api/doctors/" + doc.id)
//                .then().statusCode(200);
//        }
//
//        docs = RestAssured.given().port(port_test)
//            .when().get("/api/doctors")
//            .then().extract().body().as(DoctorDTO[].class);
//
//        assert(docs.length == 0);
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
//            .when().post("/auth/login")
//            .then().extract().path("token");
//    }
//
//    static void loginAdmin()
//    {
//        String admin_json = jsonMapper.createObjectNode()
//            .put("username", "admin").put("password", "admin").toString();
//        jwt_admin = RestAssured.given().port(port_test).contentType("application/json").body(admin_json)
//            .when().post("/auth/login")
//            .then().extract().path("token");
//    }

}
