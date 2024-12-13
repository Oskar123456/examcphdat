package dk.obhnothing.persistence.ent;

import java.util.Set;

import org.hibernate.annotations.NaturalId;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class Move
{
    @Id @NaturalId public Integer id;
    public String name;
    public String flavor_text;
    public String effect_text;
    public String target;
    public Double power;
    @ManyToMany @JoinTable(name = "pokemon_move") public Set<Pokemon> pokemons;
}

