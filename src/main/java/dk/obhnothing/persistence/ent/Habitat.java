package dk.obhnothing.persistence.ent;

import java.util.Set;

import org.hibernate.annotations.NaturalId;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Habitat
{
    @Id @NaturalId public String name;

    @OneToMany(mappedBy = "habitat") public Set<Pokemon> pokemons;

    public Habitat(String name) { this.name = name; }
}


