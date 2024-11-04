package dk.obhnothing.persistence.dao;

import java.util.List;

public interface IDAO<T, K>
{

    List<T> getAll();
    T getById(K id);
    T create(T t);
    T update(K id, T t);
    T delete(K id);

}

