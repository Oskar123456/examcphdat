package dk.obhnothing.persistence.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@NoArgsConstructor
public class AppointmentDTO
{

    public int id;
    public String client_name;
    public String comment;
    public LocalDate date;
    public LocalTime time;

}
