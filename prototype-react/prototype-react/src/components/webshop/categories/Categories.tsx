import React from 'react';
import './Categories.css';

const Categories = (props: any): JSX.Element => {
    return (
        <div className="Categories">
            <button className="chip category-chip-first">All</button>
            <button className="chip category-chip">Drones</button>
            <button className="chip category-chip">Parts</button>
            <button className="chip category-chip">Accessories</button>
            <button className="chip category-chip">Software</button>
            
        </div>
    )
}
export default Categories;