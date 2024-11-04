package dk.obhnothing.persistence.ent;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.github.javafaker.Faker;

import dk.obhnothing.persistence.enums.DoctorSpeciality;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

/*
 * Cph Business School....
 * Datamatiker 3. sem.....
 * -----------------------
 * Oskar Bahner Hansen....
 * cph-oh82@cphbusiness.dk
 * 2024-10-31.............
 * -----------------------
 */

@Entity
public class DoctorEnt
{

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) public int id;
    public String name;
    public LocalDate date_of_birth;
    public Integer year_of_graduation;
    public String name_of_clinic;
    @Enumerated(EnumType.STRING) public DoctorSpeciality speciality;
    public LocalDateTime created_at;
    public LocalDateTime updated_at;
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<AppointmentEnt> appointments;

    @PrePersist public void timestamp_created() { created_at = LocalDateTime.now(); updated_at = LocalDateTime.now(); }
    @PreUpdate public void timestamp_updated() { updated_at = LocalDateTime.now(); }

    public DoctorEnt()
    {
        Random rng = new Random();
        Faker nameGen = new Faker();
        name = nameGen.name().fullName();
        name_of_clinic = nameGen.funnyName().name();
        date_of_birth = LocalDate.parse("2024-12-31")
            .minusYears(rng.nextInt(0, 80))
            .minusMonths(rng.nextInt(0, 12))
            .minusDays(rng.nextInt(0, 31));
        year_of_graduation = LocalDate.now().getYear() - rng.nextInt(1, 60);
        speciality = DoctorSpeciality.values()[rng.nextInt(0, DoctorSpeciality.values().length)];
    }

    public void addAppointment(AppointmentEnt a)
    {
        if (appointments == null)
            appointments = new HashSet<>();
        a.doctor = this;
        appointments.add(a);
    }

}
