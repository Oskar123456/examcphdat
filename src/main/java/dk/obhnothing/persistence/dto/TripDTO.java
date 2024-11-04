package dk.obhnothing.persistence.dto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import dk.obhnothing.persistence.enums.Category;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

import static jakarta.persistence.CascadeType.*;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode.Exclude;

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

    @Exclude public GuideDTO guide;

}



