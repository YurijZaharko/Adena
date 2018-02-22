package main.service.implamentation;

import main.entity.ProductAndPriceHolder;
import main.repository.ProductAndPriceHolderRepository;
import main.service.ProductAndPriceHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ProductAndPriceHolderImpl implements ProductAndPriceHolderService {

    private ProductAndPriceHolderRepository productAndPriceHolderRepository;

    @Override
    public void save(Set<ProductAndPriceHolder> set) {
        productAndPriceHolderRepository.save(set);
    }

    @Autowired
    public void setProductAndPriceHolderRepository(ProductAndPriceHolderRepository productAndPriceHolderRepository) {
        this.productAndPriceHolderRepository = productAndPriceHolderRepository;
    }
}
