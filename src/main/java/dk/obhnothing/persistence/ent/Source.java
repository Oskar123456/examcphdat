package dk.obhnothing.persistence.ent;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;

import java.util.Set;

import org.hibernate.annotations.NaturalId;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Exclude;
import lombok.NoArgsConstructor;

/*
 * Cph Business School....
 * Datamatiker 3. sem.....
 * -----------------------
 * Oskar Bahner Hansen....
 * cph-oh82@cphbusiness.dk
 * 2024-11-04.............
 * -----------------------
 */

@Entity
@NoArgsConstructor
@EqualsAndHashCode
public class Source
{

    @Id @GeneratedValue public Integer id;
    public String id_str;
    public String name;

    @Exclude
    @OneToMany(mappedBy = "source", cascade = { PERSIST, MERGE, REMOVE })
    public Set<Headline> headlines;

    public Source(String id_string, String name) { this.id_str = id_string == null ? "NULL" : id_string; this.name = name; }

}


