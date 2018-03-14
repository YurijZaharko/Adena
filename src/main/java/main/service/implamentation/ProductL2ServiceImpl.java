package main.service.implamentation;

import main.entity.ProductL2;
import main.repository.ProductL2Repository;
import main.service.ProductL2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductL2ServiceImpl implements ProductL2Service {

    private ProductL2Repository productL2Repository;

    @Override
    public void save(ProductL2 productL2) {
        productL2Repository.save(productL2);
    }

    @Override
    public List<ProductL2> findAll() {
        return productL2Repository.findAll();
    }

    @Override
    public ProductL2 findOne(Long id) {
        return productL2Repository.findOne(id);
    }

    @Override
    public void delete(Long id) {
        productL2Repository.delete(id);
    }

    @Autowired
    public void setProductL2Repository(ProductL2Repository productL2Repository) {
        this.productL2Repository = productL2Repository;
    }
}
