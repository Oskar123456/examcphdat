package dk.obhnothing.persistence.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import dk.obhnothing.persistence.dto.HeadlineDTO;
import dk.obhnothing.persistence.dto.HeadlineListDTO;
import dk.obhnothing.utilities.Utils;

/*
 * Cph Business School....
 * Datamatiker 3. sem.....
 * -----------------------
 * Oskar Bahner Hansen....
 * cph-oh82@cphbusiness.dk
 * 2024-11-04.............
 * -----------------------
 */

public class Fetcher
{

    private static ObjectMapper jsonMapper = Utils.getObjectMapper();
    private static Logger logger = LoggerFactory.getLogger(Fetcher.class);

//    public static Pokemon pokemonById(int id)
//    {
//        try
//        {
//            if (PokeDAO.findById(id) != null) {
//                System.out.println("fetching cached pokemon (" + id + ")");
//                return PokeDAO.findById(id);
//            }
//            else
//                System.out.println("fetching new pokemon (" + id + ")");
//
//            HttpClient client = HttpClient.newHttpClient();
//            HttpRequest req = HttpRequest.newBuilder()
//                .uri(URI.create("https://pokeapi.co/api/v2/pokemon/" + id))
//                .build();
//            HttpResponse<String> res = client.send(req, BodyHandlers.ofString());
//
//            String res_str = res.body();
//
//            PokemonDTO pokDTO = jsonMapper.readValue(res_str, PokemonDTO.class);
//            Pokemon pokEnt = Mapper.PokemonDTO_Pokemon(pokDTO);
//
//            return PokeDAO.create(pokEnt);
//        }
//
//        catch (Exception e)
//        {
//            return null;
//        }
//    }

    public static HeadlineListDTO headlines(String country_code)
    {
        String api_key = Utils.getPropertyValue("NEWS_API_KEY", "config.properties");
        String base_url = "https://newsapi.org/v2/top-headlines?country=%s&apiKey=%s";

        try
        {
            String url = String.format(base_url, country_code, api_key);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest req = HttpRequest.newBuilder().uri(URI.create(url)).build();
            HttpResponse<String> res = client.send(req, BodyHandlers.ofString());

            String res_str = res.body();
            return jsonMapper.readValue(res_str, HeadlineListDTO.class);
        }

        catch (Exception e)
        {
            logger.warn("error in headlines: " + e.getMessage());
            return null;
        }
    }
}



























