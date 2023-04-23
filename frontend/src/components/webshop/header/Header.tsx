import React from 'react';
import './Header.css';
import SearchField from '../search/SearchField';
import { DISPLAYS, CURRENT_DISPLAY } from '../../../App';
import gearsImage from '../../../images/gears.png';
import basketImage from '../../../images/basket.png';
import warehouseImage from '../../../images/warehouseStock.png';
import blueprintImage from '../../../images/blueprint.png';

const Header = (
    props: { setDisplay: (display: string) => void, 
            setQuery: (query: string) => void}
    ): JSX.Element => {

    const [currentPage, setCurrentPage] = React.useState<string>(DISPLAYS.products);

    const handleDisplayChange = (display: string) => {
        setCurrentPage(display);
        props.setDisplay(display);
    }

    return(
        <div className="Header">
            <div className="titleAndSubtitle">
                <h1 className="title" onClick={e => handleDisplayChange("products")}>D.P.S.</h1>
                <h2>Drone Parts' Service</h2>
            </div>
            <SearchField onSearch={(query: string) => {console.log("You searched for: " + query)}}/>
            <button className={`change-page-button ${currentPage === DISPLAYS.blueprints ? "active-page" : ""}`}  onClick={e => handleDisplayChange(DISPLAYS.blueprints)} >
                <img className="basket-image" src={blueprintImage} alt="Assembler Blueprints" />
            </button>
            <button className={`change-page-button ${currentPage === DISPLAYS.products ? "active-page" : ""}`}  onClick={e => handleDisplayChange(DISPLAYS.products)} >
                <img className="basket-image" src={warehouseImage} alt="Warehouse" />
            </button>
            <button className={`change-page-button ${currentPage === DISPLAYS.basket ? "active-page" : ""}`}  onClick={e => handleDisplayChange(DISPLAYS.basket)} >
                <img className="basket-image" src={basketImage} alt="Basket" />
            </button>
            <button className={`change-page-button ${currentPage === DISPLAYS.systemstatus ? "active-page" : ""}`} onClick={e => handleDisplayChange(DISPLAYS.systemstatus)} >
                <img className="gears-image" src={gearsImage} alt="System Status" />
            </button>
        </div>
    )
}
export default Header;