import React from 'react';
import './Categories.css';
import { getRecipes } from '../../../ts/api';
import { Recipe } from '../../../ts/webshop';

const Categories = (props: {setQuery: (query: string) => void}): JSX.Element => {

    const [categories, setCategories] = React.useState<Recipe[]>([]);
    const [selectedCategory, setSelectedCategory] = React.useState<Recipe | null>(null);

    React.useEffect(() => {
        getRecipes()
            .catch(error => console.log(error))
            .then((categories: Recipe[] | void) => {
                if (categories) {
                    setCategories(categories);
                }
            });
    }, []);

    const handleCategoryClick = (category: Recipe) => {
        setSelectedCategory(category);
        props.setQuery("");
    }

    return (
        <div className="Categories">
            {categories.map((category: Recipe, index: number) => {
                return (
                    <button className={`chip category-chip ${selectedCategory === category ? "selected-category" : ""}`}  key={index} onClick={e => handleCategoryClick(category)}>{category.id}</button>
                )
            })}
        </div>
    )
}
export default Categories;