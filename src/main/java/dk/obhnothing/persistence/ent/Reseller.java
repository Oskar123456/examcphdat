package dk.obhnothing.persistence.ent;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinColumn;

import static jakarta.persistence.CascadeType.*;

import lombok.NoArgsConstructor;

/*
 * Cph Business School....
 * Datamatiker 3. sem.....
 * -----------------------
 * Oskar Bahner Hansen....
 * cph-oh82@cphbusiness.dk
 * 2024-11-04.............
 * -----------------------
 */

@Entity
@NoArgsConstructor
public class Reseller
{

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) public Integer id;
    public String name;
    public String address;
    public String phone;

    @ManyToMany(cascade = { PERSIST, MERGE, REMOVE }, fetch = FetchType.EAGER)
    @JoinTable(name = "reseller_plant",
        joinColumns = @JoinColumn(name = "reseller_id"),
        inverseJoinColumns = @JoinColumn(name = "plant_id"))
    public Set<Plant> plants = new HashSet<>();

}
