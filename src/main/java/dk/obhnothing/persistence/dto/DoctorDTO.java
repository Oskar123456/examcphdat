package dk.obhnothing.persistence.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dk.obhnothing.persistence.enums.DoctorSpeciality;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.EqualsAndHashCode.Exclude;

/*
 * Cph Business School....
 * Datamatiker 3. sem.....
 * -----------------------
 * Oskar Bahner Hansen....
 * cph-oh82@cphbusiness.dk
 * 2024-10-31.............
 * -----------------------
 */

@ToString
@EqualsAndHashCode
public class DoctorDTO
{

    public Integer id;
    public String name;
    public LocalDate date_of_birth;
    public Integer year_of_graduation;
    public String name_of_clinic;
    public DoctorSpeciality speciality;
    @Exclude @lombok.ToString.Exclude public List<AppointmentDTO> appointments = new ArrayList<>();

}
