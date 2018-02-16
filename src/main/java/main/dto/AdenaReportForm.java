package main.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import main.entity.CharacterL2;
import main.entity.ProductAndPriceHolder;
import main.entity.ProductL2;

import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

@Data
@ToString(exclude = {"id", "productAndPriceHolders", "adenaQuantity", "productL2s"})
@EqualsAndHashCode(exclude = {"id", "productAndPriceHolders", "productL2s"})
public class AdenaReportForm {
    private long id;
    private List<ProductAndPriceHolder> productAndPriceHolders;
    private long adenaQuantity;
    private long adenaSold;
    private Calendar calendar;
    private Set<ProductL2> productL2s;

    @NotNull
    private CharacterL2 characterL2;
}
