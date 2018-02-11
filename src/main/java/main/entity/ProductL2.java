package main.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(exclude = {"id", "productAndPriceHolders"})
@ToString(exclude = {"id", "productAndPriceHolders"})
@NoArgsConstructor
public class ProductL2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "productL2")
    private Set<ProductAndPriceHolder> productAndPriceHolders;

    @NonNull
    private String productName;
}
