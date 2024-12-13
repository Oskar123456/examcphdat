package dk.obhnothing.persistence.ent;

import java.util.Set;

import org.hibernate.annotations.NaturalId;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class Ability
{
    @Id @NaturalId public String name;
    public String flavor_text;
    public String effect_text;
    @ManyToMany @JoinTable(name = "pokemon_ability") public Set<Pokemon> pokemons;
}

