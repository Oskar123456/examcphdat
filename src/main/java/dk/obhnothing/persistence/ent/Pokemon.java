package dk.obhnothing.persistence.ent;

import java.util.Set;

import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Pokemon
{

    @Id @NaturalId public Integer id;
    public String name;
    public Integer base_experience; // base xp from defeating this pokemon
    public Double height; // unit: 0.1m
    public Double weight; // unit: 0.1kg
    public String flavor_text;
    public String evolve_from;
    public String evolve_to;
    public Integer min_level;

    public Double hp;
    public Double attack;
    public Double defense;
    public Double special_attack;
    public Double special_defense;
    public Double speed;

    public Boolean is_baby;
    public Boolean is_legendary;
    public Boolean is_mythical;

    @ManyToOne public Habitat habitat;
    @ManyToMany @JoinTable(name = "pokemon_ability",
    joinColumns = @JoinColumn(name = "ability_id"),
    inverseJoinColumns = @JoinColumn(name = "pokemon_id"))
    public Set<Ability> abilities;
    @ManyToMany @JoinTable(name = "pokemon_move",
    joinColumns = @JoinColumn(name = "move_id"),
    inverseJoinColumns = @JoinColumn(name = "pokemon_id"))
    public Set<Move> moves;
    @ManyToMany @JoinTable(name = "pokemon_type",
    joinColumns = @JoinColumn(name = "type_id"),
    inverseJoinColumns = @JoinColumn(name = "pokemon_id"))
    public Set<Type> types;
    @OneToOne public Sprite sprites;
}

