package dk.obhnothing.persistence.dao;

import java.util.List;

public interface iDao<T>
{

    List<T> readAll();
    T read(int id);
    T create(T t);
    T update(int id, T t);
    void populate(int n);

}
