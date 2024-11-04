package dk.obhnothing.persistence.dto;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/*
 * Cph Business School....
 * Datamatiker 3. sem.....
 * -----------------------
 * Oskar Bahner Hansen....
 * cph-oh82@cphbusiness.dk
 * 2024-11-02.............
 * -----------------------
 */

@EqualsAndHashCode
@NoArgsConstructor
public class ResellerDTO
{

    public int id;
    public String name;
    public String address;
    public String phone;

}
