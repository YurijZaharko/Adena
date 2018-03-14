package main.service;

import main.entity.ProductL2;

import java.util.List;

public interface ProductL2Service {
    void save(ProductL2 productL2);

    List<ProductL2> findAll();

    ProductL2 findOne(Long id);

    void delete(Long id);
}
