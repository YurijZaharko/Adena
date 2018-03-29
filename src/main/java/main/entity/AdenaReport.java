package main.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode(exclude = {"id", "productAndPriceHolders", "calendar"})
@ToString(exclude = {"productAndPriceHolders"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdenaReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private CharacterL2 characterL2;

    @OneToMany(mappedBy = "adenaReport",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<ProductAndPriceHolder> productAndPriceHolders;

    private long adenaQuantity;

    private long adenaSold;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar calendar;
}