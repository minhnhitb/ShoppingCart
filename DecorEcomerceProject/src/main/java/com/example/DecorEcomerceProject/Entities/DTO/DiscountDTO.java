package com.example.DecorEcomerceProject.Entities.DTO;

import com.example.DecorEcomerceProject.Entities.Discount;
import com.example.DecorEcomerceProject.Entities.Product;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class DiscountDTO {

    private Discount discount;

    private List<Product> products;
}
