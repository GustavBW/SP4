import { useState } from 'react'
import './App.css'
import Header from './components/webshop/header/Header'
import ProductList from './components/webshop/productList/ProductList'
import Categories from './components/webshop/categories/Categories'
import InTrasitDisplay from './components/webshop/inTrasitDisplay/InTransitDisplay'
import SystemStatus from './components/systemStatus/SystemStatus'
import Basket from './components/basket/Basket'
import Footer from './components/webshop/footer/Footer'

export const displays = {
  products: "products",
  systemstatus: "systemstatus",
  basket: "basket"
}
export let CURRENT_DISPLAY = displays.products;

function App() {
  const [count, setCount] = useState(0);
  const [display, setDisplay] = useState("products");
  const [query, setQuery] = useState("");

  const getBody = () => {
    switch (display) {
      case displays.products: {
        CURRENT_DISPLAY = displays.products;
        return (
          <div className="App-body">
            <Categories setQuery={setQuery} />
            <ProductList />
            <InTrasitDisplay />
          </div>
        )
      };
      case displays.systemstatus: {
        CURRENT_DISPLAY = displays.systemstatus;
        return (
          <SystemStatus />
        )
      };
      case displays.basket: {
        CURRENT_DISPLAY = displays.basket;
        return (
          <Basket />
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
