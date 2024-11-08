package dk.obhnothing.persistence.ent;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;

import static jakarta.persistence.CascadeType.*;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode.Exclude;

@Entity
@NoArgsConstructor
@EqualsAndHashCode
public class Guide
{

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) public Integer id;
    public String firstname;
    public String lastname;
    public String email;
    public String phone;
    public Integer yearsOfExperience;

    @Exclude
    @OneToMany(mappedBy = "guide", fetch = FetchType.EAGER)
    public Set<Trip> trips;

}



