package dk.obhnothing.control;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dk.obhnothing.persistence.dao.PokemonDAO;
import dk.obhnothing.persistence.dto.PokemonDTO;
import dk.obhnothing.persistence.ent.Pokemon;
import dk.obhnothing.persistence.service.Fetcher;
import dk.obhnothing.security.enums.Role;
import dk.obhnothing.security.exceptions.ApiException;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.http.Context;

public class PokemonController
{

    private static Logger logger = LoggerFactory.getLogger(PokemonController.class);

    public static EndpointGroup getPokemonRoutes(){
        return ()->{
            path("/pokemon", ()->{
                get("/{id}", PokemonController::getPokemon, Role.ANYONE);
                get("/", PokemonController::getPokemons, Role.ANYONE);
            });
        };
    }

    public static void getPokemons(Context ctx)
    {
        try {
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






























