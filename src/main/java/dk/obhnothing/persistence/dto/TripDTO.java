package dk.obhnothing.persistence.dto;

import java.time.LocalDateTime;

import dk.obhnothing.persistence.enums.Category;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Exclude;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@EqualsAndHashCode
public class TripDTO
{

    public Integer id;
    public LocalDateTime starttime;
    public LocalDateTime endtime;
    public String startposition;
    public String name;
    public Double price;
    public Category category;
    public PackingList packing_list;

    @Exclude public GuideDTO guide;

}



