package g7.sp4.common.models;

import jakarta.persistence.*;

@Entity
@Table(name="recipe_component")
public class RecipeComponent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(optional = true, fetch=FetchType.EAGER)
    private Component component;

    @ManyToOne(optional = true, fetch=FetchType.EAGER)
    private Recipe recipe;

    private int count;

    public RecipeComponent(){}
    public RecipeComponent(Component comp, Recipe recipe, int count){
        this.component = comp;
        this.recipe = recipe;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
