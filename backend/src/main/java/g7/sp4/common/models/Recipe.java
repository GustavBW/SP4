package g7.sp4.common.models;

import jakarta.persistence.*;

import java.util.List;
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

    @ManyToMany(fetch=FetchType.EAGER)
    private List<Component> componentsRequired;

    public Recipe(){}
    public Recipe(Part partMade, List<Component> componentsRequired)
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

    public List<Component> getComponentsRequired() {
        return componentsRequired;
    }

    public void setComponentsRequired(List<Component> componentsRequired) {
        this.componentsRequired = componentsRequired;
    }

}
