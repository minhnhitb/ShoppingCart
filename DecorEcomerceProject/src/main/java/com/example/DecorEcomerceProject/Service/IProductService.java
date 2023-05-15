package com.example.DecorEcomerceProject.Service;

import com.example.DecorEcomerceProject.Entities.Category;
import com.example.DecorEcomerceProject.Entities.DTO.ProductDto;
import com.example.DecorEcomerceProject.Entities.DTO.ProductTopSellerDto;
import com.example.DecorEcomerceProject.Entities.Product;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    Product createProduct(ProductDto productDto);

    List<Product> getAllProducts();
    List<Product> getAllProductByCategoryID(Long cateID);
    Optional<Product> getProductByID(long id);

    String deleteProduct(Long id);

    Product updateProduct(Long id, ProductDto productDto);

    List<Product> getAllProductsByKeyword(String keyword);
    List<Product> getAllProductByCateIDAndKeyword(Long cateID, String keyword);
//    List<ProductTopSellerDto> getTopSellerOfBook(int topNumber);
//    List<Book> getListBook_InOrder(String orderId);
}
