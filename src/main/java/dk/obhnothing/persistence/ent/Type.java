package dk.obhnothing.persistence.ent;

import java.util.Set;

import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Transient;

@Entity
public class Type
{
    //@Id @GeneratedValue(strategy = GenerationType.SEQUENCE) public Integer id;
    @Id @NaturalId public String name;

    @JsonIgnore @Transient public Set<String> double_damage_from;
    @JsonIgnore @Transient public Set<String> double_damage_to;
    @JsonIgnore @Transient public Set<String> half_damage_from;
    @JsonIgnore @Transient public Set<String> half_damage_to;
    @JsonIgnore @Transient public Set<String> no_damage_from;
    @JsonIgnore @Transient public Set<String> no_damage_to;

    @JsonIgnore @ManyToMany @JoinTable(name = "pokemon_type",
    joinColumns = @JoinColumn(name = "pokemon_id"),
    inverseJoinColumns = @JoinColumn(name = "type_id")) public Set<Pokemon> pokemons;
}


