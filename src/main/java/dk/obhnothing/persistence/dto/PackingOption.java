package dk.obhnothing.persistence.dto;

import java.time.LocalDateTime;

import dk.obhnothing.persistence.enums.Category;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode.Exclude;

@NoArgsConstructor
@EqualsAndHashCode
public class PackingOption
{

    public String name;
    public Integer weightInGrams;
    public Integer quantity;
    public String description;
    public Category category;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
    @Exclude public BuyingOption[] buyingOptions;

    public static class BuyingOption {
        public String shopName;
        public String shopUrl;
        public Double price;
    }

}





