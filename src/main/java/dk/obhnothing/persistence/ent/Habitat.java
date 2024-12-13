package dk.obhnothing.persistence.ent;

import java.util.Set;

import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Habitat
{
    @Id @NaturalId public String name;

    @OneToMany(mappedBy = "habitat") @JsonIgnore public Set<Pokemon> pokemons;

    public Habitat() {}
    public Habitat(String name) { this.name = name; }
}


