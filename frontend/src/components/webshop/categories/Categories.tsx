import React from 'react';
import './Categories.css';
import { getWarehouseCategories } from '../../../ts/api';

const Categories = (props: {setQuery: (query: string) => void}): JSX.Element => {

    const [categories, setCategories] = React.useState<string[]>([]);
    const [selectedCategory, setSelectedCategory] = React.useState<string>('');

    React.useEffect(() => {
        getWarehouseCategories()
            .catch(error => console.log(error))
            .then((categories: string[] | void) => {
                if (categories) {
                    setCategories(categories);
                }
            });
    }, []);

    const handleCategoryClick = (category: string) => {
        setSelectedCategory(category);
        props.setQuery(category);
    }

    return (
        <div className="Categories">
            {categories.map((category: string, index: number) => {
                return (
                    <button className={`chip category-chip ${selectedCategory === category ? "selected-category" : ""}`}  key={index} onClick={e => handleCategoryClick(category)}>{category}</button>
                )
            })}
        </div>
    )
}
export default Categories;