import React from 'react';
import './Header.css';
import SearchField from '../search/SearchField';
import { displays, CURRENT_DISPLAY } from '../../../App';
import gearsImage from '../../../images/gears.png';
import basketImage from '../../../images/basket.png';

const Header = (
    props: { setDisplay: (display: string) => void, 
            setQuery: (query: string) => void}
    ): JSX.Element => {

    const handleDisplayChange = (display: string) => {
        console.log("Current: " + CURRENT_DISPLAY + " setting to: " + display)
        props.setDisplay(display);
    }

    return(
        <div className="Header">
            <div className="titleAndSubtitle">
                <h1 className="title" onClick={e => handleDisplayChange("products")}>D.P.S.</h1>
                <h2>Drone Parts' Service</h2>
            </div>
            <SearchField onSearch={(query: string) => {console.log("You searched for: " + query)}}/>
            <button className="change-page-button basket-button" onClick={e => handleDisplayChange("basket")} >
                <img className="basket-image" src={basketImage} alt="Basket" />
            </button>
            <button className="change-page-button" onClick={e => handleDisplayChange("systemstatus")} >
                <img className="gears-image" src={gearsImage} alt="System Status" />
            </button>
        </div>
    )
}
export default Header;