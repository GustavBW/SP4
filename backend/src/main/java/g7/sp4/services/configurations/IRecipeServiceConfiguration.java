package g7.sp4.services.configurations;

import g7.sp4.services.IRecipeService;
import g7.sp4.services.RecipeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IRecipeServiceConfiguration {

    @Bean
    public IRecipeService recipeService() {
        return new RecipeService();
    }
}
