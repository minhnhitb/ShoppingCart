package com.example.DecorEcomerceProject.Repositories;

import com.example.DecorEcomerceProject.Entities.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage,Long> {
}
