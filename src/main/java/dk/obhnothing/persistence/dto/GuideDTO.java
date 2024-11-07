package dk.obhnothing.persistence.dto;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode.Exclude;

@NoArgsConstructor
@EqualsAndHashCode
public class GuideDTO
{

    public Integer id;
    public String firstname;
    public String lastname;
    public String email;
    public String phone;
    public Integer yearsOfExperience;
    @Exclude public TripDTO[] trips;

}




