package g7.sp4.services;

import g7.sp4.common.models.Part;
import g7.sp4.common.models.Recipe;
import g7.sp4.repositories.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PartService implements IPartService{

    @Autowired
    private PartRepository partRepo;

    /**
     * Creates a part and stores it in the DB
     * @param name of the part
     * @param count available in stock
     * @param description of the part
     * @param recipe that creates the part
     * @return the part as stored in DB
     */
    @Override
    public Part create(String name, int count, String description, Recipe recipe) {
        return partRepo.save(new Part(name,count,description,recipe));
    }

    @Override
    public void delete(Part part) {
        partRepo.delete(part);
    }

    /**
     * Sets the values if valid (not null, not empty... etc.) and saves the part in the DB.
     */
    @Override
    public Part update(Part part, String name, int count, String description, Recipe recipe) {
        if(name != null && !name.isEmpty())
            part.setName(name);
        if(count >= 0)
            part.setCount(count);
        if(description != null && !description.isEmpty())
            part.setDescription(description);
        if(recipe != null)
            part.setRecipe(recipe);

        return partRepo.save(part);
    }
}
