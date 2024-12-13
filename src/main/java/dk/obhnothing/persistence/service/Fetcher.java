package dk.obhnothing.persistence.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import dk.obhnothing.persistence.dao.PokemonDAO;
import dk.obhnothing.persistence.dto.NamedApiResource;
import dk.obhnothing.persistence.dto.PokemonDTO;
import dk.obhnothing.persistence.ent.Ability;
import dk.obhnothing.persistence.ent.EvolutionChain;
import dk.obhnothing.persistence.ent.Habitat;
import dk.obhnothing.persistence.ent.Move;
import dk.obhnothing.persistence.ent.Pokemon;
import dk.obhnothing.persistence.ent.Type;
import dk.obhnothing.utilities.Utils;

public class Fetcher
{

    private static ObjectMapper jsonMapper = Utils.getObjectMapper();
    private static Logger logger = LoggerFactory.getLogger(Fetcher.class);

    public static Move fetchMove(String url)
    {
        Move move = new Move();

        try
        {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest req = HttpRequest.newBuilder().uri(URI.create(url)).build();
            HttpResponse<String> res = client.send(req, BodyHandlers.ofString());
            String res_str = res.body();
            //System.out.printf("%s%n", res_str.substring(0, 50));
            JsonNode json = jsonMapper.readTree(res_str);

            JsonNode flavor_text_entries = json.get("flavor_text_entries");
            for (JsonNode ent : flavor_text_entries) {
                JsonNode lang = ent.get("language");
                JsonNode lang_name = lang.get("name");
                if (lang_name.asText().equals("en")) {
                    move.flavor_text = ent.get("flavor_text").asText();
                    break;
                }
            }

            JsonNode effect_text_entries = json.get("effect_entries");
            for (JsonNode ent : effect_text_entries) {
                JsonNode lang = ent.get("language");
                JsonNode lang_name = lang.get("name");
                if (lang_name.asText().equals("en")) {
                    move.effect_text = ent.get("effect").asText();
                    break;
                }
            }

            //move.id = json.get("id").asInt();
            move.name = json.get("name").asText();
            move.power = json.get("power").asDouble();
            move.target = json.get("target").get("name").asText();

            return move;
        }

        catch (Exception e)
        {
            logger.warn(e.getMessage());
            return null;
        }
    }

    public static Ability fetchAbility(String url)
    {
        Ability ability = new Ability();

        try
        {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest req = HttpRequest.newBuilder().uri(URI.create(url)).build();
            HttpResponse<String> res = client.send(req, BodyHandlers.ofString());
            String res_str = res.body();
            //System.out.printf("%s%n", res_str.substring(0, 50));
            JsonNode json = jsonMapper.readTree(res_str);

            JsonNode flavor_text_entries = json.get("flavor_text_entries");
            for (JsonNode ent : flavor_text_entries) {
                JsonNode lang = ent.get("language");
                JsonNode lang_name = lang.get("name");
                if (lang_name.asText().equals("en")) {
                    ability.flavor_text = ent.get("flavor_text").asText();
                    break;
                }
            }

            JsonNode effect_text_entries = json.get("effect_entries");
            for (JsonNode ent : effect_text_entries) {
                JsonNode lang = ent.get("language");
                JsonNode lang_name = lang.get("name");
                if (lang_name.asText().equals("en")) {
                    ability.effect_text = ent.get("effect").asText();
                    break;
                }
            }

            ability.name = json.get("name").asText();

            return ability;
        }

        catch (Exception e)
        {
            logger.warn(e.getMessage());
            return null;
        }
    }

    public static Type fetchType(String url)
    {
        Type type = new Type();

        try
        {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest req = HttpRequest.newBuilder().uri(URI.create(url)).build();
            HttpResponse<String> res = client.send(req, BodyHandlers.ofString());
            String res_str = res.body();
            //System.out.printf("%s%n", res_str.substring(0, 50));
            JsonNode json = jsonMapper.readTree(res_str);

            JsonNode damage_relations = json.get("damage_relations");
            JsonNode double_damage_from = damage_relations.get("double_damage_from");
            JsonNode double_damage_to = damage_relations.get("double_damage_to");
            JsonNode half_damage_from = damage_relations.get("half_damage_from");
            JsonNode half_damage_to = damage_relations.get("half_damage_to");
            JsonNode no_damage_from = damage_relations.get("no_damage_from");
            JsonNode no_damage_to = damage_relations.get("no_damage_to");

            type.double_damage_from = new HashSet<>();
            type.double_damage_to = new HashSet<>();
            type.half_damage_from = new HashSet<>();
            type.half_damage_to = new HashSet<>();
            type.no_damage_from = new HashSet<>();
            type.no_damage_to = new HashSet<>();

            if (double_damage_from != null) {
                for (JsonNode ent : double_damage_from) {
                    Type t = new Type();
                    t.name = ent.get("name").asText();
                    type.double_damage_from.add(t);
                }
            }

            if (double_damage_to != null) {
                for (JsonNode ent : double_damage_to) {
                    Type t = new Type();
                    t.name = ent.get("name").asText();
                    type.double_damage_to.add(t);
                }
            }

            if (half_damage_from != null) {
            for (JsonNode ent : half_damage_from) {
                Type t = new Type();
                t.name = ent.get("name").asText();
                type.half_damage_from.add(t);
            }
            }

            if (half_damage_to != null) {
            for (JsonNode ent : half_damage_to) {
                Type t = new Type();
                t.name = ent.get("name").asText();
                type.half_damage_to.add(t);
            }
            }

            if (no_damage_from != null) {
            for (JsonNode ent : no_damage_from) {
                Type t = new Type();
                t.name = ent.get("name").asText();
                type.no_damage_from.add(t);
            }
            }

            if (no_damage_to != null) {
            for (JsonNode ent : no_damage_to) {
                Type t = new Type();
                t.name = ent.get("name").asText();
                type.no_damage_to.add(t);
            }
            }

            type.name = json.get("name").asText();

            return type;
        }

        catch (Exception e)
        {
            logger.warn(e.getMessage());
            return null;
        }
    }

    public static Pokemon fromDTO(PokemonDTO dto)
    {
        if (PokemonDAO.getById(dto.id) != null) {
            logger.info("pokemon " + dto.name + " (" + dto.id + ") found in db");
            return PokemonDAO.getById(dto.id);
        }

        Pokemon pokemon = new Pokemon();

        String url;
        HttpResponse<String> res;
        String res_str;
        HttpRequest req;
        JsonNode json;

        try
        {
            HttpClient client = HttpClient.newHttpClient();

            pokemon.id = dto.id;
            pokemon.name = dto.name;
            pokemon.base_experience = dto.base_experience;
            pokemon.weight = dto.weight;
            pokemon.height = dto.height;
            pokemon.sprites = dto.sprites;


            /* SPECIES */
            url = dto.species.url;
            //System.out.printf("species url: %s%n%n", url);
            req = HttpRequest.newBuilder().uri(URI.create(url)).build();
            res = client.send(req, BodyHandlers.ofString());
            res_str = res.body();
            //System.out.printf("%s%n", res_str.substring(0, 50));
            json = jsonMapper.readTree(res_str);

            JsonNode flavor_text_entries = json.get("flavor_text_entries");
            for (JsonNode ent : flavor_text_entries) {
                JsonNode lang = ent.get("language");
                JsonNode lang_name = lang.get("name");
                if (lang_name.asText().equals("en")) {
                    pokemon.flavor_text = ent.get("flavor_text").asText();
                    break;
                }
            }
            pokemon.habitat = new Habitat(json.get("habitat").get("name").asText());
            pokemon.is_baby = json.get("is_baby").asBoolean();
            pokemon.is_legendary = json.get("is_legendary").asBoolean();
            pokemon.is_mythical = json.get("is_mythical").asBoolean();
            /* EVOLUTION CHAIN */
            url = json.get("evolution_chain").get("url").asText();
            //System.out.printf("evolution_chain url: %s%n%n", url);
            req = HttpRequest.newBuilder().uri(URI.create(url)).build();
            res = client.send(req, BodyHandlers.ofString());
            res_str = res.body();
            json = jsonMapper.readTree(res_str);
            //System.out.printf("%s%n", res_str.substring(0, 50));

            int order = 0;
            List<String> evo_chain_names = new ArrayList<>();
            JsonNode evo_chain = json.get("chain");
            while (evo_chain != null && !evo_chain.isEmpty()) {
               // System.out.printf("evo chain: %s%n", evo_chain.get("species").get("name").asText());;
                JsonNode evolution_details = evo_chain.get("evolution_details");

                evo_chain_names.add(evo_chain.get("species").get("name").asText());

                if (dto.name.equals(evo_chain_names.get(evo_chain_names.size() - 1))) {
                    order = evo_chain_names.size() - 1;
                    if (evolution_details == null || evolution_details.isEmpty()) {
                        pokemon.min_level = 0;
                    }
                    else {
                        for (JsonNode ent : evolution_details) {
                            pokemon.min_level = Integer.parseInt(ent.get("min_level").asText());
                            break;
                        }
                    }
                }

                JsonNode tmparr = evo_chain.get("evolves_to");
                if (tmparr == null || tmparr.isEmpty()) break;
                for (JsonNode js : tmparr) {
                    evo_chain = js;
                    break;
                }
            }
            pokemon.evolve_to = (order < evo_chain_names.size() - 1) ?
                evo_chain_names.get(order + 1) : null;
            pokemon.evolve_from = (order > 0) ?
                evo_chain_names.get(order - 1) : null;
            /* TYPES */
            pokemon.types = new HashSet<>();
            for (PokemonDTO.Type tp : dto.types) {
                Type t = fetchType(tp.type.url);
                t.pokemons = new HashSet<>();
                t.pokemons.add(pokemon);
                pokemon.types.add(t);
            }
            /* ABILITIES */
            pokemon.abilities = new HashSet<>();
            for (PokemonDTO.Ability ab : dto.abilities) {
                Ability t = fetchAbility(ab.ability.url);
                t.pokemons = new HashSet<>();
                t.pokemons.add(pokemon);
                pokemon.abilities.add(t);
            }
            /* MOVES */
            pokemon.moves = new HashSet<>();
            for (PokemonDTO.Move mv : dto.moves) {
                Move t = fetchMove(mv.move.url);
                t.pokemons = new HashSet<>();
                t.pokemons.add(pokemon);
                pokemon.moves.add(t);
            }
            /* SIMPLE */
            for (PokemonDTO.Stat stat : dto.stats) {
                if (stat.stat.name.equals("hp"))
                    pokemon.hp = Double.valueOf(stat.base_stat);
                if (stat.stat.name.equals("attack"))
                    pokemon.attack = Double.valueOf(stat.base_stat);
                if (stat.stat.name.equals("defense"))
                    pokemon.defense = Double.valueOf(stat.base_stat);
                if (stat.stat.name.equals("special-attack"))
                    pokemon.special_attack = Double.valueOf(stat.base_stat);
                if (stat.stat.name.equals("special-defense"))
                    pokemon.special_defense = Double.valueOf(stat.base_stat);
                if (stat.stat.name.equals("speed"))
                    pokemon.speed = Double.valueOf(stat.base_stat);
            }

            return pokemon;
        }

        catch (Exception e)
        {
            logger.warn(e.getMessage());
            return null;
        }
    }

    public static PokemonDTO fetchPokemon(int id)
    {
        String base_url = "https://pokeapi.co/api/v2/pokemon/%d";

        try
        {
            String url = String.format(base_url, id);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest req = HttpRequest.newBuilder().uri(URI.create(url)).build();
            HttpResponse<String> res = client.send(req, BodyHandlers.ofString());

            String res_str = res.body();
            //System.out.printf("%s%n", res_str.substring(0, 50));
            return jsonMapper.readValue(res_str, PokemonDTO.class);
        }

        catch (Exception e)
        {
            logger.warn(e.getMessage());
            return null;
        }
    }
}



























