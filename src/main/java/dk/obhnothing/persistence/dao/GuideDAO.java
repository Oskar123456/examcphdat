package dk.obhnothing.persistence.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dk.obhnothing.persistence.dto.GuideDTO;
import dk.obhnothing.persistence.dto.TripDTO;
import dk.obhnothing.persistence.ent.Guide;
import dk.obhnothing.persistence.ent.Trip;
import dk.obhnothing.persistence.service.Mapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class GuideDAO implements IDAO<GuideDTO, Integer>
{

    private static Logger logger = LoggerFactory.getLogger(GuideDAO.class);
    private static EntityManagerFactory EMF;
    public static void Init(EntityManagerFactory e) {EMF = e;}

    public List<GuideDTO> getAll()
    {
        try (EntityManager EM = EMF.createEntityManager())
        {
            EM.getTransaction().begin();
            return EM.createQuery("from Guide", Guide.class).getResultList()
                .stream().map(Mapper::Guide_GuideDTO).toList();
        }

        catch (Exception e)
        {
            logger.info(e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public GuideDTO getById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }
    @Override
    public GuideDTO create(GuideDTO dto)
    {
        try (EntityManager EM = EMF.createEntityManager())
        {
            Guide g = Mapper.GuideDTO_Guide(dto);
            EM.getTransaction().begin();
            EM.persist(g);
            EM.getTransaction().commit();
            return Mapper.Guide_GuideDTO(g);
        }

        catch (Exception e)
        {
            logger.info(e.getMessage());
            return null;
        }
    }
    @Override
    public GuideDTO update(Integer id, GuideDTO t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
    @Override
    public GuideDTO delete(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

}


