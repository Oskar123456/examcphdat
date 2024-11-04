package dk.obhnothing.persistence.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
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

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class HeadlineDTO
{

    public int id;
    public Source source;
    public String author;
    public String title;
    public String description;
    public String url;
    public String urlToImage;
    public LocalDateTime publishedAt;
    public String content;

    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class Source {
        public String id;
        public String name;
    }

}
