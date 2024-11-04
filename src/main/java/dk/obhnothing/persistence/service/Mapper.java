package dk.obhnothing.persistence.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import com.github.javafaker.Faker;

import dk.obhnothing.persistence.dto.DoctorDTO;
import dk.obhnothing.persistence.dto.HeadlineDTO;
import dk.obhnothing.persistence.dto.HeadlineListDTO;
import dk.obhnothing.persistence.dto.PlantDTO;
import dk.obhnothing.persistence.dto.ResellerDTO;
import dk.obhnothing.persistence.ent.DoctorEnt;
import dk.obhnothing.persistence.ent.Headline;
import dk.obhnothing.persistence.ent.Plant;
import dk.obhnothing.persistence.ent.Reseller;
import dk.obhnothing.persistence.ent.Source;
import dk.obhnothing.utilities.Utils;

/*
 * Cph Business School....
 * Datamatiker 3. sem.....
 * -----------------------
 * Oskar Bahner Hansen....
 * cph-oh82@cphbusiness.dk
 * 2024-11-04.............
 * -----------------------
 */

public class Mapper
{

    private static Faker nameGen = new Faker();
    private static Random rng = new Random();

    public static DoctorEnt DoctorDTO_DoctorEnt(DoctorDTO dto)
    {
        DoctorEnt de = new DoctorEnt();
        de.id = dto.id == null ? -1 : dto.id;
        de.name = dto.name;
        de.name_of_clinic = dto.name_of_clinic;
        de.date_of_birth = dto.date_of_birth;
        de.year_of_graduation = dto.year_of_graduation;
        de.speciality = dto.speciality;
        return de;
    }

    public static DoctorDTO DoctorEnt_DoctorDTO(DoctorEnt de)
    {
        DoctorDTO dto = new DoctorDTO();
        dto.id = de.id;
        dto.name = de.name;
        dto.name_of_clinic = de.name_of_clinic;
        dto.date_of_birth = de.date_of_birth;
        dto.year_of_graduation = de.year_of_graduation;
        dto.speciality = de.speciality;
        return dto;
    }

    public static Plant Plant_Plant(Plant from, Plant to)
    {
        to.id = from.id;
        to.name = from.name;
        to.planttype = from.planttype;
        to.maxheight = from.maxheight;
        to.price = from.price;
        to.resellers = new HashSet<>();
        for (Reseller r : from.resellers)
            to.resellers.add(r);
        return to;
    }

    public static Plant PlantDTO_Plant(PlantDTO from)
    {
        Plant to = new Plant();
        to.id = from.id <= 0 ? null : from.id;
        to.name = from.name;
        to.planttype = from.planttype;
        to.maxheight = from.maxheight;
        to.price = from.price;
        to.resellers = new HashSet<>();
        return to;
    }

    public static PlantDTO Plant_PlantDTO(Plant from)
    {
        PlantDTO to = new PlantDTO();
        to.id = from.id;
        to.name = from.name;
        to.planttype = from.planttype;
        to.maxheight = from.maxheight;
        to.price = from.price;
        return to;
    }

    public static Reseller Reseller_Reseller(Reseller from)
    {
        Reseller to = new Reseller();
        to.id = from.id;
        to.name = from.name;
        to.address = from.address;
        to.phone = from.phone;
        to.plants = new HashSet<>();
        for (Plant p : from.plants)
            to.plants.add(p);
        return to;
    }

    public static Reseller ResellerDTO_Reseller(ResellerDTO from)
    {
        Reseller to = new Reseller();
        to.id = from.id <= 0 ? null : from.id;
        to.name = from.name;
        to.address = from.address;
        to.phone = from.phone;
        to.plants = new HashSet<>();
        return to;
    }

    public static ResellerDTO Reseller_ResellerDTO(Reseller from)
    {
        ResellerDTO to = new ResellerDTO();
        to.id = from.id;
        to.name = from.name;
        to.address = from.address;
        to.phone = from.phone;
        return to;
    }

    public static PlantDTO gen_PlantDTO()
    {
        PlantDTO dto = new PlantDTO();
        dto.id = -1;
        dto.name = nameGen.funnyName().name();
        dto.planttype = nameGen.animal().name() + " " + nameGen.ancient().titan();
        dto.maxheight = rng.nextInt(1, 100);
        dto.price = Utils.round(rng.nextDouble(1, 100), 2);
        return dto;
    }

    public static ResellerDTO gen_ResellerDTO()
    {
        ResellerDTO dto = new ResellerDTO();
        dto.id = -1;
        dto.name = nameGen.company().name() + " plants";
        dto.address = nameGen.address().fullAddress();
        dto.phone = nameGen.phoneNumber().phoneNumber();
        return dto;
    }

    public static Plant gen_Plant()
    {
        return PlantDTO_Plant(gen_PlantDTO());
    }

    public static Reseller gen_Reseller()
    {
        return ResellerDTO_Reseller(gen_ResellerDTO());
    }

    public static List<Plant> gen_Plants(int n)
    {
        List<Plant> plants = new ArrayList<>();
        for (int i = 0; i < n; i++)
            plants.add(gen_Plant());
        return plants;
    }

    public static Headline HeadlineDTO_Headline(HeadlineDTO dto)
    {
        Headline h = new Headline();
        h.id = null;
        h.author = dto.author;
        h.content = dto.content;
        h.description = dto.description;
        h.title = dto.title;
        h.url = dto.url;
        h.urlToImage = dto.urlToImage;
        h.publishedAt = dto.publishedAt;
        h.source = new Source(dto.source.id, dto.source.name);
        return h;
    }

    public static HeadlineDTO Headline_HeadlineDTO(Headline h)
    {
        HeadlineDTO dto = new HeadlineDTO();
        dto.id = h.id;
        dto.author = h.author;
        dto.content = h.content;
        dto.description = h.description;
        dto.title = h.title;
        dto.url = h.url;
        dto.urlToImage = h.urlToImage;
        dto.publishedAt = h.publishedAt;
        dto.source = new HeadlineDTO.Source(h.source.id_str, h.source.name);
        return dto;
    }

    public static List<Headline> HeadlineListDTO_Extract(HeadlineListDTO dto_list)
    {
        return Arrays.stream(dto_list.articles).map(Mapper::HeadlineDTO_Headline).toList();
    }

}


























