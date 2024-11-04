package dk.obhnothing.control;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;
import static io.javalin.apibuilder.ApiBuilder.put;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import dk.obhnothing.persistence.dao.DoctorDAO;
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

public class DoctorController
{

    private static DoctorDAO doctorDAO = new DoctorDAO();

    public static EndpointGroup getRoutes()
    {
        return () -> {
            path("/api", () -> {
                get("/doctors", DoctorController::getAll, Role.ANYONE);
                post("/doctor", DoctorController::create, Role.ANYONE);
                post("/doctors", DoctorController::create, Role.ANYONE);
                get("/doctors/{id}", DoctorController::getById, Role.ANYONE);
                get("/doctor/{id}", DoctorController::getById, Role.ANYONE);
                put("/doctor/{id}", DoctorController::update, Role.ANYONE);
                get("/doctor/speciality/{speciality}", DoctorController::getBySpec, Role.ANYONE);
                get("/doctor/birthdate/range", DoctorController::getByBirthdate, Role.ANYONE);
                get("/*", DoctorController::test);
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
            DoctorDTO dto = ctx.bodyAsClass(DoctorDTO.class);
            dto = doctorDAO.update(id, dto);
            ctx.json(dto);
            ctx.status(200);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ApiException(404, e.getMessage());
        }
    }

    private static void create(Context ctx)
    {
        try {
            DoctorDTO dto = ctx.bodyAsClass(DoctorDTO.class);
            dto = doctorDAO.create(dto);
            ctx.json(dto);
            ctx.status(201);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ApiException(400, "failed to create doctor");
        }
    }

    private static void getAll(Context ctx)
    {
        try {
            List<DoctorDTO> dtos = doctorDAO.readAll();
            ctx.json(dtos);
            ctx.status(200);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ApiException(404, e.getMessage());
        }
    }

    private static void getById(Context ctx)
    {
        try {
            DoctorDTO dto = doctorDAO.read(Integer.parseInt(ctx.pathParam("id")));
            ctx.json(dto);
            ctx.status(200);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
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
            System.out.println(e.getMessage());
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
            System.out.println(e.getMessage());
            throw new ApiException(404, "doctor not found (" + ctx.queryParam("from") + " -- " + ctx.queryParam("to") + ")");
        }
    }

}




