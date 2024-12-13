package dk.obhnothing.persistence.ent;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class EvolutionChain
{
    @Id public Integer id;
    public String pokemon_name;
    public Integer pokemon_id;
    public Integer min_level;
    @OneToOne public EvolutionChain next;
    //@OneToOne(mappedBy = "next") public EvolutionChain prev;
}
