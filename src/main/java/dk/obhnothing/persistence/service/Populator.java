package dk.obhnothing.persistence.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.github.javafaker.Faker;

import dk.obhnothing.persistence.dao.GuideDAO;
import dk.obhnothing.persistence.dao.TripDAO;
import dk.obhnothing.persistence.ent.Guide;
import dk.obhnothing.persistence.ent.Trip;
import dk.obhnothing.persistence.enums.Category;

public class Populator
{

    private static Faker nameGen = new Faker();
    private static Random rng = new Random();

    public static List<Trip> PopTrips(int n)
    {
        TripDAO trip_dao = new TripDAO();
        GuideDAO guide_dao = new GuideDAO();

        List<Trip> trips = new ArrayList<>();
        List<Guide> guides = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            Guide g = new Guide();
            g.id = null;
            g.firstname = nameGen.name().firstName();
            g.lastname = nameGen.name().lastName();
            g.email = g.firstname + "@"
                + nameGen.company().name() + "."
                + nameGen.country().countryCode2();
            g.yearsOfExperience = rng.nextInt(1, 55);
            g.phone = nameGen.phoneNumber().cellPhone();
            guide_dao.create(Mapper.Guide_GuideDTO(g));
        }

        guides = guide_dao.getAll().stream().map(Mapper::GuideDTO_Guide).toList();

        for (int i = 0; i < n; i++) {
            Trip t = new Trip();
            t.id = null;
            t.category = Category.values()[rng.nextInt(0, Category.values().length)];
            t.starttime = LocalDateTime.now().plusDays(rng.nextInt(10, 500));
            t.endtime = t.starttime.plusDays(rng.nextInt(10, 50));
            t.startposition = nameGen.country().capital();
            t.name = "Trip to " + t.startposition;
            t.price = rng.nextDouble(1000, 100000);
            t.guide = guides.get(rng.nextInt(0, guides.size()));
            trips.add(t);
        }

        trips.stream().map(Mapper::Trip_TripDTO).forEach(trip_dao::create);
        return trips;
    }

}
