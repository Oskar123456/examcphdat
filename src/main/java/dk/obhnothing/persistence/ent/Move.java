package dk.obhnothing.persistence.ent;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class Move
{
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) public Integer id;
    public String name;
    @Column(columnDefinition = "TEXT") public String flavor_text;
    @Column(columnDefinition = "TEXT") public String effect_text;
    public String target;
    public Double power;
    @JsonIgnore @ManyToMany @JoinTable(name = "pokemon_move",
    joinColumns = @JoinColumn(name = "pokemon_id"),
    inverseJoinColumns = @JoinColumn(name = "move_id")) public Set<Pokemon> pokemons;
}

