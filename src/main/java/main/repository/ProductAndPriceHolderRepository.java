package main.repository;

import main.entity.ProductAndPriceHolder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductAndPriceHolderRepository extends JpaRepository<ProductAndPriceHolder, Long>{
}
