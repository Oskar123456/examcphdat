package dk.obhnothing.control;

import static io.javalin.apibuilder.ApiBuilder.delete;
import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;
import static io.javalin.apibuilder.ApiBuilder.put;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dk.obhnothing.persistence.dao.DoctorDAO_DB;
import dk.obhnothing.persistence.dto.DoctorDTO;
import dk.obhnothing.persistence.enums.DoctorSpeciality;
import dk.obhnothing.security.enums.Role;
import dk.obhnothing.security.exceptions.ApiException;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.http.Context;

/*
 * Cph Business School....
 * Datamatiker 3. sem.....
 * -----------------------
 * Oskar Bahner Hansen....
 * cph-oh82@cphbusiness.dk
 * 2024-11-04.............
 * -----------------------
 */

public class DoctorController_DB
{

    private static Logger logger = LoggerFactory.getLogger(DoctorController_DB.class);
    private static DoctorDAO_DB doctorDAO = new DoctorDAO_DB();

    public static EndpointGroup getRoutes()
    {
        return () -> {
            path("/api", () -> {
                get("/doctors", DoctorController_DB::getAll, Role.ANYONE);
                post("/doctor", DoctorController_DB::create, Role.ADMIN);
                post("/doctors", DoctorController_DB::create, Role.ADMIN);
                get("/doctors/{id}", DoctorController_DB::getById, Role.ANYONE);
                delete("/doctors/{id}", DoctorController_DB::deleteById, Role.ADMIN);
                delete("/doctor/{id}", DoctorController_DB::deleteById, Role.ADMIN);
                get("/doctor/{id}", DoctorController_DB::getById, Role.ANYONE);
                put("/doctor/{id}", DoctorController_DB::update, Role.ADMIN);
                put("/doctors/{id}", DoctorController_DB::update, Role.ADMIN);
                get("/doctor/speciality/{speciality}", DoctorController_DB::getBySpec, Role.ANYONE);
                get("/doctors/speciality/{speciality}", DoctorController_DB::getBySpec, Role.ANYONE);
                get("/doctor/birthdate/range", DoctorController_DB::getByBirthdate, Role.ANYONE);
                get("/doctors/birthdate/range", DoctorController_DB::getByBirthdate, Role.ANYONE);
                get("/*", DoctorController_DB::test);
            });
        };
    }

    private static void test(Context ctx)
    {
        throw new ApiException(404, "endpoint not found");
    }

    private static void update(Context ctx)
    {
        try {
            Integer id = Integer.parseInt(ctx.pathParam("id"));
            doctorDAO.update(id, ctx.bodyAsClass(DoctorDTO.class));
            ctx.status(200);
            ctx.json(doctorDAO.read(id));
        }
        catch (Exception e) {
            logger.info(e.getMessage());
            throw new ApiException(404, e.getMessage());
        }
    }

    private static void create(Context ctx)
    {
        try {
            DoctorDTO dto = ctx.bodyAsClass(DoctorDTO.class);
            ctx.status(201);
            ctx.json(doctorDAO.create(dto));
        }
        catch (Exception e) {
            logger.info(e.getMessage());
            throw new ApiException(400, "failed to create doctor");
        }
    }

    private static void getAll(Context ctx)
    {
        try {
            ctx.json(doctorDAO.readAll());
            ctx.status(200);
        }
        catch (Exception e) {
            logger.info(e.getMessage());
            throw new ApiException(404, e.getMessage());
        }
    }

    private static void getById(Context ctx)
    {
        try {
            DoctorDTO doc = doctorDAO.read(Integer.parseInt(ctx.pathParam("id")));
            if (doc == null)
                throw new ApiException(404, "doctor not found (" + ctx.pathParam("id") + ")");
            ctx.json(doc);
            ctx.status(200);
        }
        catch (Exception e) {
            logger.info(e.getMessage());
            throw new ApiException(404, "doctor not found (" + ctx.pathParam("id") + ")");
        }
    }

    private static void getBySpec(Context ctx)
    {
        try {
            List<DoctorDTO> dtos = doctorDAO.doctorBySpeciality(
                    DoctorSpeciality.valueOf(
                        ctx.pathParam("speciality").toUpperCase()));
            ctx.json(dtos);
            ctx.status(200);
        }
        catch (Exception e) {
            logger.info(e.getMessage());
            throw new ApiException(404, "doctors with speciality not found (" + ctx.pathParam("speciality") + ")");
        }
    }

    private static void getByBirthdate(Context ctx)
    {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate from = LocalDate.parse(ctx.queryParam("from"), dtf);
            LocalDate to = LocalDate.parse(ctx.queryParam("to"), dtf);
            List<DoctorDTO> dtos = doctorDAO.doctorByBirthdateRange(from, to);
            ctx.json(dtos);
            ctx.status(200);
        }
        catch (Exception e) {
            logger.info(e.getMessage());
            throw new ApiException(404, "doctor not found (" + ctx.queryParam("from") + " -- " + ctx.queryParam("to") + ")");
        }
    }

    private static void deleteById(Context ctx)
    {
        try {
            ctx.json(doctorDAO.delete(Integer.parseInt(ctx.pathParam("id"))));
            ctx.status(200);
        }
        catch (Exception e) {
            logger.info(e.getMessage());
            throw new ApiException(404, "doctor not found (" + ctx.pathParam("id") + ")");
        }
    }

}




