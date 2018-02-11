package main.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode(exclude = {"id", "productAndPriceHolders"})
@ToString(exclude = {"productAndPriceHolders"})
@NoArgsConstructor
public class AdenaReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private CharacterL2 characterL2;

    @OneToMany(mappedBy = "adenaReport", cascade = CascadeType.ALL)
    private Set<ProductAndPriceHolder> productAndPriceHolders;

    private long adenaQuantity;

    private long adenaSold;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar calendar;
}