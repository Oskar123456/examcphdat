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

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode.Exclude;

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
@EqualsAndHashCode
public class Plant
{

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) public Integer id;
    public String planttype;
    public String name;
    public int maxheight;
    public double price;

    @Exclude
    @ManyToMany(cascade = { PERSIST, MERGE, REMOVE }, fetch = FetchType.EAGER)
    @JoinTable(name = "reseller_plant",
        joinColumns = @JoinColumn(name = "plant_id"),
        inverseJoinColumns = @JoinColumn(name = "reseller_id"))
    public Set<Reseller> resellers = new HashSet<>();

}

