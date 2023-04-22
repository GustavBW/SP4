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

    @OneToOne(optional = true)
    private Part partMade;

    @OneToMany(fetch=FetchType.EAGER)
    private Set<RecipeComponent> componentsRequired;

    public Recipe(){}
    public Recipe(Part partMade, Set<RecipeComponent> componentsRequired)
    {
        this.partMade = partMade;
        this.componentsRequired = componentsRequired;
    }

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
