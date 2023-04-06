package g7.sp4.common.models;

import jakarta.persistence.ManyToMany;

import java.util.Set;

public class Component {

    private int id;
    private String name;

    @ManyToMany
    private Set<Recipe> recipes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Set<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(Set<Recipe> recipes) {
        this.recipes = recipes;
    }
}
