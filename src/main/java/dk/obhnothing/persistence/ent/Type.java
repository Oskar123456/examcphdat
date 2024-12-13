package dk.obhnothing.persistence.ent;

import java.util.Set;

import org.hibernate.annotations.NaturalId;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class Type
{
    @Id @NaturalId public String name;

    @ManyToMany @JoinTable(name = "double_damage_from",
    joinColumns = @JoinColumn(name = "this"),
    inverseJoinColumns = @JoinColumn(name = "other")) public Set<Type> double_damage_from;

    @ManyToMany @JoinTable(name = "double_damage_to",
    joinColumns = @JoinColumn(name = "this"),
    inverseJoinColumns = @JoinColumn(name = "other")) public Set<Type> double_damage_to;

    @ManyToMany @JoinTable(name = "half_damage_from",
    joinColumns = @JoinColumn(name = "this"),
    inverseJoinColumns = @JoinColumn(name = "other")) public Set<Type> half_damage_from;

    @ManyToMany @JoinTable(name = "half_damage_to",
    joinColumns = @JoinColumn(name = "this"),
    inverseJoinColumns = @JoinColumn(name = "other")) public Set<Type> half_damage_to;

    @ManyToMany @JoinTable(name = "no_damage_from",
    joinColumns = @JoinColumn(name = "this"),
    inverseJoinColumns = @JoinColumn(name = "other")) public Set<Type> no_damage_from;

    @ManyToMany @JoinTable(name = "no_damage_to",
    joinColumns = @JoinColumn(name = "this"),
    inverseJoinColumns = @JoinColumn(name = "other")) public Set<Type> no_damage_to;

    @ManyToMany @JoinTable(name = "pokemon_type") public Set<Pokemon> pokemons;
}


