package com.example.DecorEcomerceProject.Controller;

import com.example.DecorEcomerceProject.Entities.DTO.DiscountDTO;
import com.example.DecorEcomerceProject.Entities.Discount;
import com.example.DecorEcomerceProject.Entities.Product;
import com.example.DecorEcomerceProject.Service.IDiscountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DiscountController {
    private IDiscountService discountService;
    public DiscountController(IDiscountService discountService) {
        this.discountService = discountService;
    }
    @GetMapping("/discounts")
    public ResponseEntity<?> getAllDiscount(){
        if (discountService.getAllDiscount().size()==0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("List discount is empty!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(discountService.getAllDiscount());
    }
    @GetMapping("/discounts/get_by_product/{id}")
    public ResponseEntity<?> getAllDiscountByProductId(@PathVariable Long id){
        if (discountService.getAllDiscountByProductId(id).size()==0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No discount for product with id: "+ id);
        }
        return ResponseEntity.status(HttpStatus.OK).body(discountService.getAllDiscountByProductId(id));
    }
    @GetMapping("/discount/{id}")
    public ResponseEntity<?> getDiscountById(@PathVariable Long id){
        if(!discountService.getDiscountById(id).isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Discount with id "+id+" is not existed !");
        }else {
            return ResponseEntity.ok().body(discountService.getDiscountById(id));
        }
    }
    @PostMapping("/discount/create")
    public Discount createDiscount(@Validated @RequestBody DiscountDTO discountDTO) {
        return discountService.createDiscount(discountDTO);
    }
}
