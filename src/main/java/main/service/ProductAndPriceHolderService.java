package main.service;

import main.entity.ProductAndPriceHolder;

import java.util.Set;

public interface ProductAndPriceHolderService {
    void save(Set<ProductAndPriceHolder> set);
}
