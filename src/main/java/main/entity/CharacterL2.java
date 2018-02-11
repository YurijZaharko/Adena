package main.entity;

import lombok.*;
import main.entity.constants.ServerNames;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode(exclude = {"id", "adenaReports"})
@ToString(exclude = {"adenaReports"})
@NoArgsConstructor
public class CharacterL2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ServerNames serverNames;

    @NonNull
    private String name;

    @OneToMany(mappedBy = "characterL2", cascade = CascadeType.ALL)
    private Set<AdenaReport> adenaReports;
}
