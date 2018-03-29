package main.validators;

import main.dto.AdenaReportForm;
import main.entity.ProductAndPriceHolder;
import main.entity.ProductL2;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class AdenaReportFormValidator implements Validator {
    private static final String EMPTY_ERROR_MASSAGE = "Adena Quantity or Adena Sold or products must be filled";
    private List<ProductAndPriceHolder> productAndPriceHolders;
    @Override
    public boolean supports(Class<?> aClass) {
        return AdenaReportForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        AdenaReportForm adenaReportForm = (AdenaReportForm) o;
        final long adenaQuantity = adenaReportForm.getAdenaQuantity();
        final long adenaSold = adenaReportForm.getAdenaSold();
        final Set<ProductL2> productL2s = Optional.ofNullable(adenaReportForm.getProductL2s()).orElse(Collections.emptySet());
        this.productAndPriceHolders = Optional.ofNullable(adenaReportForm.getProductAndPriceHolders()).orElse(Collections.emptyList());

        if (adenaQuantity == 0 && adenaSold == 0 && productL2s.isEmpty()) {
            errors.rejectValue("adenaQuantity", "", EMPTY_ERROR_MASSAGE);
            errors.rejectValue("adenaSold", "", EMPTY_ERROR_MASSAGE);
            errors.rejectValue("productL2s", "", EMPTY_ERROR_MASSAGE);
        }

        if (!productAndPriceHolders.isEmpty() && hasEmptyFields()){
            errors.rejectValue("productAndPriceHolders","" ,"Quantity and Price must be filled");
        }
    }

    private boolean hasEmptyFields() {
        return isNull(productAndPriceHolders) || isBelowZero(productAndPriceHolders);
    }

    private boolean isNull(List<ProductAndPriceHolder> productAndPriceHolders){
        return productAndPriceHolders.stream().anyMatch(productAndPriceHolder -> productAndPriceHolder.getProductQuantity() == null || productAndPriceHolder.getProductPrice() == null);
    }

    private boolean isBelowZero(List<ProductAndPriceHolder> productAndPriceHolders){
        return productAndPriceHolders.stream().
                filter(productAndPriceHolder -> productAndPriceHolder.getProductPrice() != null || productAndPriceHolder.getProductQuantity() != null).
                anyMatch(productAndPriceHolder -> productAndPriceHolder.getProductQuantity() <= 0 || productAndPriceHolder.getProductPrice() <= 0);
    }
}
