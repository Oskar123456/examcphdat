package dk.obhnothing.persistence.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dk.obhnothing.persistence.dto.DoctorDTO;
import dk.obhnothing.persistence.ent.AppointmentEnt;
import dk.obhnothing.persistence.ent.DoctorEnt;
import dk.obhnothing.persistence.enums.DoctorSpeciality;
import dk.obhnothing.persistence.service.Mapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.NoArgsConstructor;

/*
 * Cph Business School....
 * Datamatiker 3. sem.....
 * -----------------------
 * Oskar Bahner Hansen....
 * cph-oh82@cphbusiness.dk
 * 2024-10-31.............
 * -----------------------
 */

@NoArgsConstructor
public class DoctorDAO_DB implements iDao<DoctorDTO>
{

    private static Logger logger = LoggerFactory.getLogger(DoctorDAO_DB.class);
    private static EntityManagerFactory EMF;
    public static void Init(EntityManagerFactory e) {EMF = e;}

    public List<DoctorDTO> readAll()
    {
        try (EntityManager EM = EMF.createEntityManager())
        {
            EM.getTransaction().begin();
            List<DoctorEnt> results = EM.createQuery("from DoctorEnt", DoctorEnt.class).getResultList();
            return results.stream().map(Mapper::DoctorEnt_DoctorDTO).toList();
        }

        catch (Exception e)
        {
            logger.info(e.getMessage());
            return new ArrayList<DoctorDTO>();
        }
    }

    public DoctorDTO read(int id)
    {
        try (EntityManager EM = EMF.createEntityManager())
        {
            EM.getTransaction().begin();
            return Mapper.DoctorEnt_DoctorDTO(EM.createQuery("from DoctorEnt where id = :id", DoctorEnt.class)
                .setParameter("id", id).getSingleResult());
        }

        catch (Exception e)
        {
            logger.info(e.getMessage());
            return null;
        }
    }

    public List<DoctorDTO> doctorBySpeciality(DoctorSpeciality speciality)
    {
        try (EntityManager EM = EMF.createEntityManager())
        {
            EM.getTransaction().begin();
            List<DoctorEnt> results = EM.createQuery("select d from DoctorEnt d where speciality = :spec", DoctorEnt.class)
                .setParameter("spec", speciality)
                .getResultList();
            return results.stream().map(Mapper::DoctorEnt_DoctorDTO).toList();
        }

        catch (Exception e)
        {
            logger.info(e.getMessage());
            return new ArrayList<DoctorDTO>();
        }
    }

    public List<DoctorDTO> doctorByBirthdateRange(LocalDate from, LocalDate to)
    {
        try (EntityManager EM = EMF.createEntityManager())
        {
            EM.getTransaction().begin();
            List<DoctorEnt> results =
                EM.createQuery("select d from DoctorEnt d where date_of_birth <= :to AND date_of_birth >= :from", DoctorEnt.class)
                .setParameter("from", from).setParameter("to", from).getResultList();
            return results.stream().map(Mapper::DoctorEnt_DoctorDTO).toList();
        }

        catch (Exception e)
        {
            logger.info(e.getMessage());
            return new ArrayList<DoctorDTO>();
        }
    }

    public DoctorDTO create(DoctorDTO doctor)
    {
        try (EntityManager EM = EMF.createEntityManager())
        {
            EM.getTransaction().begin();
            DoctorEnt de = new DoctorEnt();
            EM.persist(de);
            de.name = doctor.name;
            de.name_of_clinic = doctor.name_of_clinic;
            de.date_of_birth = doctor.date_of_birth;
            de.speciality = doctor.speciality;
            EM.getTransaction().commit();
            return Mapper.DoctorEnt_DoctorDTO(de);
        }

        catch (Exception e)
        {
            logger.info(e.getMessage());
            return null;
        }
    }

    public DoctorDTO update(int id, DoctorDTO doctor)
    {
        try (EntityManager EM = EMF.createEntityManager())
        {
            EM.getTransaction().begin();
            DoctorEnt doctor_from_db = EM.find(DoctorEnt.class, id);
            doctor_from_db.date_of_birth = doctor.date_of_birth;
            doctor_from_db.year_of_graduation = doctor.year_of_graduation;
            doctor_from_db.name_of_clinic = doctor.name_of_clinic;
            doctor_from_db.speciality = doctor.speciality;
            EM.merge(doctor_from_db);
            EM.getTransaction().commit();
            return Mapper.DoctorEnt_DoctorDTO(doctor_from_db);
        }

        catch (Exception e)
        {
            logger.info(e.getMessage());
            return null;
        }
    }

    public DoctorDTO delete(int id)
    {
        try (EntityManager EM = EMF.createEntityManager())
        {
            EM.getTransaction().begin();
            DoctorEnt doctor_from_db = EM.find(DoctorEnt.class, id);
            EM.remove(doctor_from_db);
            EM.getTransaction().commit();
            return Mapper.DoctorEnt_DoctorDTO(doctor_from_db);
        }

        catch (Exception e)
        {
            logger.info(e.getMessage());
            return null;
        }
    }

    public void populate(int n)
    {
        List<DoctorEnt> docs_in_db = readAll().stream().map(Mapper::DoctorDTO_DoctorEnt).toList();
        n = n - docs_in_db.size();
        try (EntityManager EM = EMF.createEntityManager())
        {
            EM.getTransaction().begin();
            for (int i = 0; i < n; i++) {
                DoctorEnt doc = new DoctorEnt();
                int n_apps = new Random().nextInt(1, 5);
                for (int j = 0; j < n_apps; j++) {
                    doc.addAppointment(new AppointmentEnt());
                }
                EM.persist(doc);
            }
            EM.getTransaction().commit();
        }

        catch (Exception e)
        {
            logger.info(e.getMessage());
        }
    }

    public void deleteAll()
    {
        try (EntityManager EM = EMF.createEntityManager())
        {
            EM.getTransaction().begin();
            EM.createQuery("delete from DoctorEnt").executeUpdate();
            EM.getTransaction().commit();
        }

        catch (Exception e)
        {
            logger.info(e.getMessage());
        }
    }

}
