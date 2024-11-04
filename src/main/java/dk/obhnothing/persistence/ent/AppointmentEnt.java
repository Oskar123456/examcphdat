package dk.obhnothing.persistence.ent;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;

import com.github.javafaker.Faker;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class AppointmentEnt
{

    @Id @GeneratedValue public int id;
    public String client_name;
    public String comment;
    public LocalDate date;
    public LocalTime time;
    @ManyToOne(fetch = FetchType.EAGER) DoctorEnt doctor;

    public AppointmentEnt()
    {
        Random rng = new Random();
        Faker nameGen = new Faker();
        client_name = nameGen.name().fullName();
        comment = nameGen.ancient().titan();
        date = LocalDate.parse("2024-12-31")
            .plusYears(rng.nextInt(0, 2))
            .minusMonths(rng.nextInt(0, 12))
            .minusDays(rng.nextInt(0, 31));
        time = LocalTime.now().minusHours(rng.nextInt(0, 12));
    }

}
