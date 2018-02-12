package main.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode(exclude = {"id"})
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductAndPriceHolder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProductL2 productL2;

    @ManyToOne(fetch = FetchType.LAZY)
    private AdenaReport adenaReport;

    private Long productQuantity;
    private Long productPrice;
}
