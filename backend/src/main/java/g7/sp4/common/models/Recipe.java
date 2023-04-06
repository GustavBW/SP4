package g7.sp4.common.models;

import jakarta.persistence.*;

import java.util.Map;
import java.util.Set;

@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    private Part partMade;

    @OneToMany
    private Set<RecipeComponent> componentsRequired;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Part getPartMade() {
        return partMade;
    }

    public void setPartMade(Part partMade) {
        this.partMade = partMade;
    }

    public Set<RecipeComponent> getComponentsRequired() {
        return componentsRequired;
    }

    public void setComponentsRequired(Set<RecipeComponent> componentsRequired) {
        this.componentsRequired = componentsRequired;
    }

}
