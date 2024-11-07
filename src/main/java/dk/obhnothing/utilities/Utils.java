package dk.obhnothing.utilities;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dk.obhnothing.security.exceptions.ApiException;
import io.javalin.http.Context;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Purpose: Utility class to read properties from a file
 * Author: Thomas Hartmann
 */
public class Utils {
    public static void main(String[] args) {
        System.out.println(getPropertyValue("db.name", "properties-from-pom.properties"));
    }
    public static String getPropertyValue(String propName, String resourceName)  {
        // REMEMBER TO BUILD WITH MAVEN FIRST. Read the property file if not deployed (else read system vars instead)
        // Read from ressources/config.properties or from pom.xml depending on the ressourceName
        try (InputStream is = Utils.class.getClassLoader().getResourceAsStream(resourceName)) {
            Properties prop = new Properties();
            prop.load(is);

            String value = prop.getProperty(propName);
            if (value != null) {
                return value.trim();  // Trim whitespace
            } else {
                throw new ApiException(500, String.format("Property %s not found in %s", propName, resourceName));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new ApiException(500, String.format("Could not read property %s. Did you remember to build the project with MAVEN?", propName));
        }
    }

    public static ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // Ignore unknown properties in JSON
        objectMapper.registerModule(new JavaTimeModule()); // Serialize and deserialize java.time objects
        objectMapper.writer(new DefaultPrettyPrinter());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
        return objectMapper;
    }

    public static String convertToJsonMessage(Context ctx, String property, String message) {
        Map<String, String> msgMap = new HashMap<>();
        //msgMap.put(property, message);  // Put the message in the map
        msgMap.put(property, ctx.status().toString());
        msgMap.put("status", String.valueOf(ctx.statusCode()));  // Put the status in the map
        msgMap.put("timestamp", new DateTimeFormatterBuilder()
                .appendPattern("yyyy-mm-dd HH:mm:ss")
                .appendFraction(ChronoField.MILLI_OF_SECOND, 2, 3, true)
                .toFormatter()
                .format(LocalDateTime.now()));
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(msgMap);  // Convert the map to JSON
        } catch (Exception e) {
            return "{\"error\": \"Could not convert  message to JSON\"}";
        }
    }

    public static DateTimeFormatter dtformatter()
    {
        return new DateTimeFormatterBuilder()
                .appendPattern("yyyy-mm-dd HH:mm:ss")
                .appendFraction(ChronoField.MILLI_OF_SECOND, 2, 3, true)
                .toFormatter();
    }

    /**
     * @Author https://stackoverflow.com/users/56285/jonik
     *
     * taken from: https://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places
     */
    public static double round(double num, int places) {
        BigDecimal val = BigDecimal.valueOf(num);
        val = val.setScale(places, RoundingMode.HALF_UP);
        return val.doubleValue();
    }

}
