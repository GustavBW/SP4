import { useState, useRef } from 'react';
import './App.css';
import Header from './components/webshop/header/Header';
import ProductList from './components/webshop/productList/ProductList';
import Categories from './components/webshop/categories/Categories';
import InTrasitDisplay from './components/webshop/inTransitDisplay/InTransitDisplay';
import SystemStatus from './components/systemStatus/SystemStatus';
import Basket from './components/basket/Basket';
import Footer from './components/webshop/footer/Footer';
import StyleWheel from './components/StyleWheel';
import StarsImage from './images/2kStars.jpg';

export const DISPLAYS = {
  products: "products",
  systemstatus: "systemstatus",
  basket: "basket"
}
export let CURRENT_DISPLAY = DISPLAYS.products;

const App = (): JSX.Element => {
  const [display, setDisplay] = useState(DISPLAYS.products);
  const [query, setQuery] = useState("");
  const [isBeyond, setBeyond] = useState(false);
  const appRef = useRef<HTMLDivElement>(null);

  const getBody = () => {
    switch (display) {
      case DISPLAYS.products: {
        CURRENT_DISPLAY = DISPLAYS.products;
        return (
          <div className="App-body">
            <Categories setQuery={setQuery} />
            <ProductList />
            <InTrasitDisplay />
          </div>
        )
      };
      case DISPLAYS.systemstatus: {
        CURRENT_DISPLAY = DISPLAYS.systemstatus;
        return (
          <SystemStatus />
        )
      };
      case DISPLAYS.basket: {
        CURRENT_DISPLAY = DISPLAYS.basket;
        return (
          <Basket return={setDisplay}/>
        )
      };
    }
  }

  const ifIsBeyond = (): JSX.Element =>{
    if(isBeyond){
      return(
        <div className="beyond-container">
          <img src={StarsImage} alt="stars" />
        </div>
      )
    }else{
      return(<></>)
    }
  }

  return (
    <div className="App" ref={appRef}>
      <Header setDisplay={setDisplay} setQuery={setQuery} />
      {getBody()}
      <Footer />
      <StyleWheel goBeyond={setBeyond}/>
      {ifIsBeyond()}
    </div>
  )
}

export default App
