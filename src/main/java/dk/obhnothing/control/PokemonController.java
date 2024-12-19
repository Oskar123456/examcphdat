package dk.obhnothing.control;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.post;

import java.util.Collections;
import java.util.List;

import static io.javalin.apibuilder.ApiBuilder.path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dk.bugelhartmann.UserDTO;
import dk.obhnothing.persistence.dao.PokemonDAO;
import dk.obhnothing.persistence.dao.UserDAO;
import dk.obhnothing.persistence.dto.PokemonDTO;
import dk.obhnothing.persistence.ent.Habitat;
import dk.obhnothing.persistence.ent.Pokemon;
import dk.obhnothing.persistence.ent.Type;
import dk.obhnothing.persistence.service.Fetcher;
import dk.obhnothing.security.enums.Role;
import dk.obhnothing.security.exceptions.ApiException;
import dk.obhnothing.utilities.Utils;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.http.Context;

public class PokemonController
{

    private static Logger logger = LoggerFactory.getLogger(PokemonController.class);

    public static EndpointGroup getPokemonRoutes(){
        return ()->{
            path("/pokemon", ()->{
                get("/type/{name}", PokemonController::getBytype, Role.ANYONE);
                get("/type", PokemonController::getTypes, Role.ANYONE);
                get("/habitat/{name}", PokemonController::getByHabitat, Role.ANYONE);
                get("/habitat", PokemonController::getHabitats, Role.ANYONE);
                get("/mypokemon", PokemonController::getMyPokemons, Role.USER);
                get("/{id}", PokemonController::getPokemon, Role.ANYONE);
                get("/", PokemonController::getPokemons, Role.ANYONE);

                post("/getpack", PokemonController::getPack, Role.USER);
            });
        };
    }

    public static void getTypes(Context ctx)
    {
        try {
            ctx.json(PokemonDAO.getTypeDTOs());
            ctx.status(200);
        }
        catch (Exception e) {
            throw new ApiException(404, e.getMessage());
        }
    }

    public static void getBytype(Context ctx)
    {
        try {
            String path_param = ctx.pathParam("name");
            Type t = PokemonDAO.getTypeByName(path_param);
            System.out.println(Utils.getObjectMapper().writeValueAsString(t));
            ctx.json(PokemonDAO.getByType(t));
            ctx.status(200);
        }
        catch (Exception e) {
            throw new ApiException(404, e.getMessage());
        }
    }

    public static void getHabitats(Context ctx)
    {
        try {
            ctx.json(PokemonDAO.getAllHabitat());
            ctx.status(200);
        }
        catch (Exception e) {
            throw new ApiException(404, e.getMessage());
        }
    }

    public static void getByHabitat(Context ctx)
    {
        try {
            String path_param = ctx.pathParam("name");
            Habitat h = new Habitat(path_param);
            ctx.json(PokemonDAO.getByHabitat(h));
            ctx.status(200);
        }
        catch (Exception e) {
            throw new ApiException(404, e.getMessage());
        }
    }

    public static void getPack(Context ctx)
    {
        try {
            List<Pokemon> allP = PokemonDAO.getAll();
            Collections.shuffle(allP);
            List<Pokemon> pack = allP.subList(0, 5);

            UserDTO u = ctx.attribute("user");
            UserDAO.addPokemon(u, pack);

            ctx.json(pack);
            ctx.status(200);
        }
        catch (Exception e) {
            throw new ApiException(404, e.getMessage());
        }
    }

    public static void getMyPokemons(Context ctx)
    {
        try {
            UserDTO u = ctx.attribute("user");
            ctx.json(UserDAO.getPokemon(u));
            ctx.status(200);
        }
        catch (Exception e) {
            throw new ApiException(404, e.getMessage());
        }
    }

    public static void getPokemons(Context ctx)
    {
        try {
            ctx.json(PokemonDAO.getAll());
            ctx.status(200);
        }
        catch (Exception e) {
            throw new ApiException(404, e.getMessage());
        }
    }

    public static void getPokemon(Context ctx)
    {
        try {
            String path_param = ctx.pathParam("id");

            Pokemon p;
            if (strIsNumber(path_param))
                p = PokemonDAO.getById(Integer.parseInt(path_param));
            else
                p = PokemonDAO.getByName(path_param);

            if (p == null && strIsNumber(path_param)) {
                PokemonDTO dto = Fetcher.fetchPokemon(Integer.parseInt(path_param));
                if (dto != null) {
                    Pokemon p_new = Fetcher.fromDTO(dto);
                    PokemonDAO.create(p_new);
                    p = PokemonDAO.getById(Integer.parseInt(path_param));
                }
            }

            ctx.json(p);
            ctx.status(200);
        }
        catch (Exception e) {
            logger.info(e.getMessage());
            throw new ApiException(404, e.getMessage());
        }
    }

    private static boolean strIsNumber(String num)
    {
        try { Integer.parseInt(num); return true; }
        catch (Exception e) { return false; }
    }

}






























