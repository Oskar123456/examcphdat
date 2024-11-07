package dk.obhnothing.persistence.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import com.github.javafaker.Faker;

import dk.obhnothing.persistence.dto.GuideDTO;
import dk.obhnothing.persistence.dto.TripDTO;
import dk.obhnothing.persistence.ent.Guide;
import dk.obhnothing.persistence.ent.Trip;
import dk.obhnothing.utilities.Utils;

public class Mapper
{

    private static Faker nameGen = new Faker();
    private static Random rng = new Random();

    public static TripDTO Trip_TripDTO(Trip t)
    {
        TripDTO dto = new TripDTO();
        dto.id = t.id;
        dto.category = t.category;
        dto.endtime = t.endtime;
        dto.starttime = t.starttime;
        dto.startposition = t.startposition;
        dto.name = t.name;
        dto.price = Math.round(t.price * 100.0) / 100.0;
        dto.guide = Mapper.Guide_GuideDTO(t.guide);
        return dto;
    }

    public static Trip TripDTO_Trip(TripDTO dto)
    {
        Trip t = new Trip();
        t.id = dto.id;
        t.category = dto.category;
        t.endtime = dto.endtime;
        t.starttime = dto.starttime;
        t.startposition = dto.startposition;
        t.name = dto.name;
        t.price = Math.round(dto.price * 100.0) / 100.0;
        t.guide = Mapper.GuideDTO_Guide(dto.guide);
        return t;
    }

    public static Guide GuideDTO_Guide(GuideDTO dto)
    {
        Guide g = new Guide();
        g.id = dto.id;
        g.firstname = dto.firstname;
        g.lastname = dto.lastname;
        g.phone = dto.phone;
        g.email = dto.email;
        g.yearsOfExperience = dto.yearsOfExperience;
        return g;
    }

    public static GuideDTO Guide_GuideDTO(Guide g)
    {
        GuideDTO dto = new GuideDTO();
        dto.id = g.id;
        dto.firstname = g.firstname;
        dto.lastname = g.lastname;
        dto.phone = g.phone;
        dto.email = g.email;
        dto.yearsOfExperience = g.yearsOfExperience;
        return dto;
    }

}


























