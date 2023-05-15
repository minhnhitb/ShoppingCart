package com.example.DecorEcomerceProject.Controller;

import com.example.DecorEcomerceProject.Entities.Category;
import com.example.DecorEcomerceProject.Entities.DTO.ProductDto;
import com.example.DecorEcomerceProject.Entities.Product;
import com.example.DecorEcomerceProject.Repositories.ProductRepository;
import com.example.DecorEcomerceProject.Service.ICategoryService;
import com.example.DecorEcomerceProject.Service.IProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProductsController {
    private IProductService productService;
    private ICategoryService categoryService;
    public ProductsController(IProductService productService, ICategoryService categoryService){
        this.productService = productService;
        this.categoryService = categoryService;
    }
    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
    @GetMapping("/products/search")
    public List<Product> getAllProductsByKeyword(@RequestParam("keyword") String keyword) {
        return productService.getAllProductsByKeyword(keyword);
    }
    @GetMapping("/product/{id}")
    public ResponseEntity<?> getProductByID(@PathVariable Long id){
        if(productService.getProductByID(id) == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product with id "+id+" is not existed !");
        }else {
            return ResponseEntity.ok().body(productService.getProductByID(id));
        }
    }
    @GetMapping("/products/category/{cateId}")
    public ResponseEntity<?> getProductByCateID(@PathVariable Long cateId){
        if(productService.getAllProductByCategoryID(cateId) == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("List products is empty!");
        }else{
            return ResponseEntity.ok().body(productService.getAllProductByCategoryID(cateId));
        }
    }
    @GetMapping("/products/cateID-search")
    public ResponseEntity<?> getProductsByCateIDAndKeyword(@RequestParam("cateID") Long cateID,
                                                       @RequestParam("keyword") String keyword){
        if(productService.getAllProductByCateIDAndKeyword(cateID, keyword) == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("List products is empty!");
        }else{
            return ResponseEntity.ok().body(productService.getAllProductByCateIDAndKeyword(cateID, keyword));
        }
    }
    @PostMapping("/products/add")
        public ResponseEntity<Product> createProduct(@RequestBody  ProductDto productDto){
            Product newProduct = productService.createProduct(productDto);
            return ResponseEntity.ok(newProduct);
    }
    @DeleteMapping("/products/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.deleteProduct(id));
    }
    @PutMapping("/products/save/{id}")
    public ResponseEntity<Product> updateBook(@PathVariable Long id, @RequestBody ProductDto productDto) {
        Product product = productService.updateProduct(id, productDto);
        return ResponseEntity.ok(product);
    }
}
