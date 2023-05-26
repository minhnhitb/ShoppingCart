package com.example.DecorEcomerceProject.Service.Impl;

import com.example.DecorEcomerceProject.Entities.DTO.DiscountDTO;
import com.example.DecorEcomerceProject.Entities.Discount;
import com.example.DecorEcomerceProject.Entities.DiscountHistory;
import com.example.DecorEcomerceProject.Entities.Product;
import com.example.DecorEcomerceProject.Repositories.DiscountHistoryRepository;
import com.example.DecorEcomerceProject.Repositories.DiscountRepository;
import com.example.DecorEcomerceProject.Service.IDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service

public class DiscountServiceImpl implements IDiscountService {
    private DiscountRepository discountRepository;
    private DiscountHistoryRepository discountHistoryRepository;
    public DiscountServiceImpl(DiscountRepository discountRepository, DiscountHistoryRepository discountHistoryRepository) {
        this.discountRepository = discountRepository;
        this.discountHistoryRepository = discountHistoryRepository;
    }
    @Override
    public List<Discount> getAllDiscount() {
        return discountRepository.findAll();
    }
    @Override
    public Optional<DiscountDTO> getDiscountById(Long id) {
        Optional<Discount> discount = discountRepository.findById(id);
        if (discount.isPresent()) {
            DiscountDTO discountDTO = new DiscountDTO();
            List<Product> products = discountRepository.getAllProductByDiscountId(id);
            discountDTO.setDiscount(discount.get());
            discountDTO.setProducts(products);
            return Optional.of(discountDTO);
        }
        return Optional.empty();
    }
    @Override
    @Transactional
    public Discount createDiscount(DiscountDTO discountDTO) {
        Discount createdDiscount = discountRepository.save(discountDTO.getDiscount());
        for (Product product : discountDTO.getProducts()) {
            DiscountHistory discountHistory = new DiscountHistory();
            discountHistory.setDiscount(createdDiscount);
            discountHistory.setProduct(product);
            discountHistoryRepository.save(discountHistory);
        }
        return createdDiscount;
    }
    @Override
    public List<Discount> getAllDiscountByProductId(Long id) {
        return discountRepository.getAllDiscountByProductId(id);
    }

}
