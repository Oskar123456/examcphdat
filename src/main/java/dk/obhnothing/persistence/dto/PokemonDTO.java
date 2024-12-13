package dk.obhnothing.persistence.dto;

import dk.obhnothing.persistence.ent.Sprite;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class PokemonDTO
{

    public Integer id;
    public Boolean is_default;
    public String name;
    public String location_area_encounters;
    public Integer base_experience;
    public Double height;
    public Double weight;
    public Ability[] abilities;
    public NamedApiResource[] forms;
    public Move[] moves;
    public NamedApiResource species;
    public Type[] types;
    public Sprite sprites;
    public Stat[] stats;

    public static class Move {
        public NamedApiResource move;
        public VersionGroupDetails[] version_group_details;

        public static class VersionGroupDetails {
            public Integer level_learned_at;
            public NamedApiResource move_learn_method;
            public NamedApiResource version_group;
        }
    }

    public static class Ability {
        public Boolean is_hidden;
        public Integer slot;
        public NamedApiResource ability;
    }

    public static class Stat {
        public Integer base_stat;
        public Integer effort;
        public NamedApiResource stat;
    }

    public static class Type {
        public Integer slot;
        public NamedApiResource type;
    }

}
