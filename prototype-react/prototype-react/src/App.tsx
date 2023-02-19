import { useState } from 'react'
import './App.css'
import Header from './components/webshop/header/Header'
import ProductList from './components/webshop/productList/ProductList'
import Categories from './components/webshop/categories/Categories'
import InTrasitDisplay from './components/webshop/inTrasitDisplay/InTransitDisplay'
import SystemStatus from './components/systemStatus/SystemStatus'
import Basket from './components/basket/Basket'
import Footer from './components/webshop/footer/Footer'

export const DISPLAYS = {
  products: "products",
  systemstatus: "systemstatus",
  basket: "basket"
}
export let CURRENT_DISPLAY = DISPLAYS.products;

function App() {
  const [count, setCount] = useState(0);
  const [display, setDisplay] = useState(DISPLAYS.products);
  const [query, setQuery] = useState("");

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

  return (
    <div className="App">
      <Header setDisplay={setDisplay} setQuery={setQuery} />
      {getBody()}
      <Footer />
    </div>
  )
}

export default App
