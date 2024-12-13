package dk.obhnothing.persistence.ent;

import java.util.Set;

import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class Type
{
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) public Integer id;
    public String name;

    @JsonIgnore @ManyToMany(cascade = CascadeType.PERSIST) @JoinTable(name = "double_damage_from",
    joinColumns = @JoinColumn(name = "this"),
    inverseJoinColumns = @JoinColumn(name = "other")) public Set<Type> double_damage_from;

    @JsonIgnore @ManyToMany(cascade = CascadeType.PERSIST) @JoinTable(name = "double_damage_to",
    joinColumns = @JoinColumn(name = "this"),
    inverseJoinColumns = @JoinColumn(name = "other")) public Set<Type> double_damage_to;

    @JsonIgnore @ManyToMany(cascade = CascadeType.PERSIST) @JoinTable(name = "half_damage_from",
    joinColumns = @JoinColumn(name = "this"),
    inverseJoinColumns = @JoinColumn(name = "other")) public Set<Type> half_damage_from;

    @JsonIgnore @ManyToMany(cascade = CascadeType.PERSIST) @JoinTable(name = "half_damage_to",
    joinColumns = @JoinColumn(name = "this"),
    inverseJoinColumns = @JoinColumn(name = "other")) public Set<Type> half_damage_to;

    @JsonIgnore @ManyToMany(cascade = CascadeType.PERSIST) @JoinTable(name = "no_damage_from",
    joinColumns = @JoinColumn(name = "this"),
    inverseJoinColumns = @JoinColumn(name = "other")) public Set<Type> no_damage_from;

    @JsonIgnore @ManyToMany(cascade = CascadeType.PERSIST) @JoinTable(name = "no_damage_to",
    joinColumns = @JoinColumn(name = "this"),
    inverseJoinColumns = @JoinColumn(name = "other")) public Set<Type> no_damage_to;

    @JsonIgnore @ManyToMany @JoinTable(name = "pokemon_type",
    joinColumns = @JoinColumn(name = "pokemon_id"),
    inverseJoinColumns = @JoinColumn(name = "type_id")) public Set<Pokemon> pokemons;
}


