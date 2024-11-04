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

import dk.obhnothing.utilities.Utils;

public class Fetcher
{

    private static ObjectMapper jsonMapper = Utils.getObjectMapper();
    private static Logger logger = LoggerFactory.getLogger(Fetcher.class);


//    public static HeadlineListDTO headlines(String country_code)
//    {
//        String api_key = Utils.getPropertyValue("NEWS_API_KEY", "config.properties");
//        String base_url = "https://newsapi.org/v2/top-headlines?country=%s&apiKey=%s";
//
//        try
//        {
//            String url = String.format(base_url, country_code, api_key);
//            HttpClient client = HttpClient.newHttpClient();
//            HttpRequest req = HttpRequest.newBuilder().uri(URI.create(url)).build();
//            HttpResponse<String> res = client.send(req, BodyHandlers.ofString());
//
//            String res_str = res.body();
//            return jsonMapper.readValue(res_str, HeadlineListDTO.class);
//        }
//
//        catch (Exception e)
//        {
//            logger.warn("error in headlines: " + e.getMessage());
//            return null;
//        }
//    }
}



























