package dk.obhnothing.persistence.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dk.obhnothing.persistence.dto.DoctorDTO;
import dk.obhnothing.persistence.ent.DoctorEnt;
import dk.obhnothing.persistence.enums.DoctorSpeciality;
import dk.obhnothing.persistence.service.Mapper;
import lombok.NoArgsConstructor;

/*
 * Cph Business School....
 * Datamatiker 3. sem.....
 * -----------------------
 * Oskar Bahner Hansen....
 * cph-oh82@cphbusiness.dk
 * 2024-10-31.............
 * -----------------------
 */

@NoArgsConstructor
public class DoctorDAO implements iDao<DoctorDTO>
{

    private static int id_counter = 1;
    private static List<DoctorDTO> doctors = new ArrayList<>();

    public List<DoctorDTO> readAll()
    {
        return doctors;
    }

    public DoctorDTO read(int id)
    {
        try {
            return doctors.stream().filter(d -> d.id == id).findFirst().get();
        }
        catch (Exception e) {
            return null;
        }
    }

    public List<DoctorDTO> doctorBySpeciality(DoctorSpeciality speciality)
    {
        return doctors.stream().filter(d -> d.speciality == speciality).toList();
    }

    public List<DoctorDTO> doctorByBirthdateRange(LocalDate from, LocalDate to)
    {
        return doctors.stream().filter(d ->
                d.date_of_birth.isEqual(from) || d.date_of_birth.isEqual(to)
            || (d.date_of_birth.isAfter(from) && d.date_of_birth.isBefore(to)))
            .toList();
    }

    public DoctorDTO create(DoctorDTO doctor)
    {
        if (doctor != null) {
            doctor.id = id_counter++;
            doctors.add(doctor);
        }
        return doctor;
    }

    public DoctorDTO update(int id, DoctorDTO doctor)
    {
        for (int i = 0; i < doctors.size(); i++) {
            if (id == doctors.get(i).id) {
                doctors.set(i, doctor);
                doctors.get(i).id = id;
                return doctors.get(i);
            }
        }
        return null;
    }

    public void populate(int n)
    {
        if (doctors == null || doctors.size() > 0)
            doctors = new ArrayList<>();
        for (int i = 0; i < n; i++)
            doctors.add(Mapper.DoctorEnt_DoctorDTO(new DoctorEnt()));
    }

    public void deleteAll()
    {
        doctors = new ArrayList<>();
    }

}
