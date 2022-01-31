package monprojet.entity;

import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;

import org.hibernate.annotations.Proxy;

import lombok.*;

// Un exemple d'entité
// On utilise Lombok pour auto-générer getter / setter / toString...
// cf. https://examples.javacodegeeks.com/spring-boot-with-lombok/
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
@Proxy(lazy = false)
@Entity // Une entité JPA
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    @NonNull
    private String code;

    @Column(unique = true)
    @NonNull
    private String name;

    // Lombok https://www.projectlombok.org/features/ToString

    @OneToMany(mappedBy = "country")
    @ToString.Exclude
    private List<City> cities = new ArrayList<>();
}
