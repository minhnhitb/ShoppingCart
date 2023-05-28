package com.example.DecorEcomerceProject.Entities.DTO;

import com.example.DecorEcomerceProject.Entities.Enum.ProductStatus;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
@Data
public class ProductDto {
    @NotEmpty(message = "Thiếu tên sản phẩm")
    private String name;
    @NotNull
    @Min(value = 1, message= "Thiếu loại sản phẩm")
    private long category_id;
    private String description;
    @NotEmpty(message = "Thiếu số lượng sản phẩm")
    private int inventory;
    @NotEmpty(message = "Thiếu giá cả")
    private double price;
    private ProductStatus status;
    private String imageUrl;
}
