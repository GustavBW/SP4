package g7.sp4.common.models;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;

import java.util.List;
import java.util.Set;

@Entity
public class Part{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "count")
    private int count; //in stock count

    @Column(name = "name")
    private String name;

    @Column(name = "description", length = 25_000)
    private String description;

    @OneToOne(optional = true)
    private Recipe recipe;

    @ManyToMany(fetch=FetchType.EAGER)
    private Set<BatchPart> batchParts;

    public Part(){}
    public Part(String name, int count, String description, Recipe recipe){
        this.name = name;
        this.count = count;
        this.description = description;
        this.recipe = recipe;
    }


    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Set<BatchPart> getBatchParts() {
        return batchParts;
    }

    public void setBatchParts(Set<BatchPart> batchParts) {
        this.batchParts = batchParts;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Recipe getRecipe() {
        return recipe;
    }

}
