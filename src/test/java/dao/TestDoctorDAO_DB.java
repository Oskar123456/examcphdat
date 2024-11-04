package dao;

import static dk.obhnothing.persistence.HibernateConfig.Mode.TEST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import dk.obhnothing.persistence.HibernateConfig;
import dk.obhnothing.persistence.dao.DoctorDAO_DB;
import dk.obhnothing.persistence.dao.UserDAO;
import dk.obhnothing.persistence.dto.DoctorDTO;
import dk.obhnothing.persistence.ent.DoctorEnt;
import dk.obhnothing.persistence.enums.DoctorSpeciality;
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

public class TestDoctorDAO_DB
{

//    static EntityManagerFactory EMF;
//    static DoctorDAO_DB doc_dao;
//    static String jwt_user = "";
//    static String jwt_admin = "";
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
//    }
//
//    @AfterAll static void deinit()
//    {
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
//    /******************/
//    /* TASK 5 *********/
//    /******************/
//
//    @Test @DisplayName("readAll") void testreadAll()
//    {
//        List<DoctorDTO> docs = doc_dao.readAll();
//        assert(docs.size() >= n_docs);
//    }
//
//    @Test @DisplayName("read") void testread()
//    {
//        List<DoctorDTO> docs = doc_dao.readAll();
//        assert(docs.size() >= n_docs);
//        for (DoctorDTO doc : docs) {
//            int id = doc.id;
//            DoctorDTO doc_from_read = doc_dao.read(id);
//            assertNotNull(doc_from_read);
//            assertEquals(id, doc_from_read.id);
//        }
//    }
//
//    @Test @DisplayName("doctorBySpeciality") void testdoctorBySpeciality()
//    {
//        for (DoctorSpeciality spec : DoctorSpeciality.values()) {
//            List<DoctorDTO> docs_with_spec = doc_dao.doctorBySpeciality(spec);
//            for (DoctorDTO doc : docs_with_spec)
//                assert(doc.speciality == spec);
//        }
//    }
//
//    @Test @DisplayName("doctorByBirthdateRange") void testdoctorByBirthdateRange()
//    {
//        int n_tests = 10;
//        for (int i = 0; i < n_tests; i++) {
//            Random rng = new Random();
//            LocalDate from = LocalDate.now().minusYears(rng.nextInt(0, 100))
//                .minusMonths(rng.nextInt(0, 13)).minusDays(rng.nextInt(0, 32));
//            LocalDate to = from.plusYears(rng.nextInt(0, 100))
//                .plusMonths(rng.nextInt(0, 13)).plusDays(rng.nextInt(0, 32));
//            List<DoctorDTO> docs = doc_dao.doctorByBirthdateRange(from, to);
//            for (DoctorDTO doc : docs) {
//                assert(doc.date_of_birth.equals(from) || doc.date_of_birth.equals(from)
//                        || (doc.date_of_birth.isAfter(from) && doc.date_of_birth.isBefore(to)));
//            }
//        }
//    }
//
//    @Test @DisplayName("create") void testcreate()
//    {
//        String test_name = "__TESTNAME__";
//        DoctorEnt doc = new DoctorEnt();
//        doc.name = test_name;
//        DoctorDTO doc_dto_from_create = doc_dao.create(Mapper.DoctorEnt_DoctorDTO(doc));
//        assert(doc.name.equals(doc_dto_from_create.name));
//        DoctorDTO doc_dto_from_db = doc_dao.read(doc_dto_from_create.id);
//        assert(doc.name.equals(doc_dto_from_db.name));
//    }
//
//    @Test @DisplayName("update") void testupdate()
//    {
//        String test_name = "__TESTNAME__";
//
//        List<DoctorDTO> docs = doc_dao.readAll();
//        assert(docs.size() >= n_docs);
//
//        for (DoctorDTO doc : docs) {
//            int id = doc.id;
//            assertNotNull(doc);
//            doc.name_of_clinic = test_name;
//            doc_dao.update(id, doc);
//            DoctorDTO doc_from_db = doc_dao.read(id);
//            assertNotNull(doc_from_db);
//            assertEquals(id, doc_from_db.id);
//            assert(test_name.equals(doc_from_db.name_of_clinic));
//        }
//    }
//
//    @Test @DisplayName("delete") void testdelete()
//    {
//        List<DoctorDTO> docs = doc_dao.readAll();
//        assert(docs.size() >= n_docs);
//
//        for (DoctorDTO doc : docs)
//            doc_dao.delete(doc.id);
//
//        docs = doc_dao.readAll();
//        assert(docs.size() == 0);
//    }
//
//    /******************/
//    /* TESTING ENDS ***/
//    /******************/

}
