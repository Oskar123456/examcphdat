package dk.obhnothing.utilities;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.javafaker.Faker;

import lombok.ToString;

/*
 * Cph Business School....
 * Datamatiker 3. sem.....
 * -----------------------
 * Oskar Bahner Hansen....
 * cph-oh82@cphbusiness.dk
 * 2024-11-04.............
 * -----------------------
 */

public class JsonUtils
{

    private static int MAX_DEPTH = 256;

    public static JsonObject toClass(String json, String objectName)
    {
        JsonObject jo = new JsonObject(objectName);
        return jo;
    }

    public static void fillOutJsonObjectStr(char[] jsonStr, JsonObjectStr JO)
    {

    }

    private static String showWhere(String jsonStr, int idx)
    {
        return null;
    }

    public enum TYPE { NULL, NUMBER, STRING, BOOL, OBJECT }

    public static class JsonObject
    {
        public String id;
        public TYPE type;
        public boolean isArray;
        public List<JsonObject> children;
        public JsonObject(String id) { this.id = id; this.children = new ArrayList<>(); }
    }

    public static class JsonObjectStr
    {
        public boolean isObj;
        public boolean isArr;
        public String K;
        public String V;
        public List<JsonObjectStr> fields;
    }

}




































