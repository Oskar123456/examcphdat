package dk.obhnothing.persistence.ent;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
public class Headline
{

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) public Integer id;
    public String author;
    @Column(columnDefinition = "TEXT")public String title;
    @Column(columnDefinition = "TEXT")public String description;
    public String url;
    public String urlToImage;
    public LocalDateTime publishedAt;
    @Column(columnDefinition = "TEXT") public String content;

    @Exclude
    @ManyToOne(cascade = { PERSIST, MERGE }, fetch = FetchType.EAGER)
    public Source source;

}


