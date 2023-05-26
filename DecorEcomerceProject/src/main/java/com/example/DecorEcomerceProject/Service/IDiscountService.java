package com.example.DecorEcomerceProject.Service;

import com.example.DecorEcomerceProject.Entities.DTO.DiscountDTO;
import com.example.DecorEcomerceProject.Entities.Discount;
import com.example.DecorEcomerceProject.Entities.Product;

import java.util.List;
import java.util.Optional;

public interface IDiscountService {
    Discount createDiscount(DiscountDTO discountDTO);
    List<Discount> getAllDiscount();
    Optional<DiscountDTO> getDiscountById(Long id);
    List<Discount> getAllDiscountByProductId(Long id);

}
