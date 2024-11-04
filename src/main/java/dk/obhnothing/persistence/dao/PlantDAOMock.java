package dk.obhnothing.persistence.dao;

import java.util.ArrayList;
import java.util.List;

import dk.obhnothing.persistence.dto.PlantDTO;

/*
 * Cph Business School....
 * Datamatiker 3. sem.....
 * -----------------------
 * Oskar Bahner Hansen....
 * cph-oh82@cphbusiness.dk
 * 2024-11-02.............
 * -----------------------
 */

public class PlantDAOMock implements iDao_Garden<PlantDTO>
{

    private static int id_counter = 1;
    private List<PlantDTO> plants;

    public List<PlantDTO> getAll()
    {
        return plants;
    }

    public PlantDTO getById(int id)
    {
        try {
            return plants.stream().filter(p -> p.id == id).findFirst().get();
        }
        catch (Exception e) {
            return null;
        }
    }

    public List<PlantDTO> getByType(String type)
    {
        try {
            return plants.stream().filter(p -> p.planttype.equals(type)).toList();
        }
        catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public PlantDTO add(PlantDTO plant)
    {
        plant.id = genId();
        plants.add(plant);
        return plant;
    }

    public List<PlantDTO> getMaxHeightIs100()
    {
        try {
            return plants.stream().filter(p -> p.maxheight == 100).toList();
        }
        catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public List<String> getNames(List<PlantDTO> plantDTOs)
    {
        try {
            return plantDTOs.stream().map(p -> p.name).toList();
        }
        catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public List<PlantDTO> getSortedByNames(List<PlantDTO> plantDTOs)
    {
        try {
            return plantDTOs.stream().sorted((a, b) -> a.name.compareTo(b.name)).toList();
        }
        catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public int populate(int n)
    {
        if (plants == null || plants.size() > 0)
            plants = new ArrayList<>();
        int gen_count = 0;
        for (int i = 0; i < n; i++) {
            PlantDTO p = new PlantDTO();
            p.id = genId();
            plants.add(p);
            gen_count++;
        }
        return gen_count;
    }

    private static synchronized int genId()
    {
        return id_counter++;
    }

}
