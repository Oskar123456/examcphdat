package dk.obhnothing.persistence.dao;

import java.util.List;

/*
 * Cph Business School....
 * Datamatiker 3. sem.....
 * -----------------------
 * Oskar Bahner Hansen....
 * cph-oh82@cphbusiness.dk
 * 2024-11-02.............
 * -----------------------
 */

public interface iDao_Garden<T>
{

    public List<T> getAll();
    public T getById(int id);
    public List<T> getByType(String type);
    public T add(T t);
    public List<T> getMaxHeightIs100();
    public List<String> getNames(List<T> DTOs);
    public List<T> getSortedByNames(List<T> DTOs);
    public int populate(int n);

}
