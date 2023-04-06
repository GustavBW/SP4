package g7.sp4.common.models;

import java.util.Set;

public record Recipe(Part partMade, Set<PartComponent> componentsRequired) {
}
