package dk.obhnothing.persistence.ent;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Sprite
{
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) public Integer id;
    public String back_default;
    public String back_female;
    public String back_shiny;
    public String back_shiny_female;
    public String front_default;
    public String front_female;
    public String front_shiny;
    public String front_shiny_female;

    @OneToOne(mappedBy = "sprites") public Pokemon pokemon;
}


