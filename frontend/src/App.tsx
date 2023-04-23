import { useState, useRef } from 'react';
import './App.css';
import Header from './components/webshop/header/Header';
import ProductList from './components/webshop/productList/ProductList';
import Categories from './components/webshop/event/EventList';
import InTrasitDisplay from './components/webshop/inTransitDisplay/InTransitDisplay';
import SystemStatus from './components/systemStatus/SystemStatus';
import Basket from './components/basket/Basket';
import Footer from './components/webshop/footer/Footer';
import StyleWheel from './components/StyleWheel';
import FacilityImage from './images/manufacturing-facility.webp';
import { Batch } from './ts/webshop';
import EventList from './components/webshop/event/EventList';
import BatchView from './components/webshop/event/batchView/batchView';

export const DISPLAYS = {
  products: "products",
  systemstatus: "systemstatus",
  basket: "basket",
  blueprints: "blueprints"
}
export let CURRENT_DISPLAY = DISPLAYS.products;

const App = (): JSX.Element => {
  const [display, setDisplay] = useState(DISPLAYS.products);
  const [query, setQuery] = useState("");
  const appRef = useRef<HTMLDivElement>(null);
  const [selectedBatch, setSelectedBatch] = useState<Batch | null>();

  const getBody = () => {
    switch (display) {
      case DISPLAYS.products: {
        CURRENT_DISPLAY = DISPLAYS.products;
        return (
          <div className="App-body">
            <EventList setDisplay={setDisplay} setSelectedBatch={setSelectedBatch}/>
            <ProductList />
            <InTrasitDisplay />
          </div>
        )
      };
      case DISPLAYS.blueprints: {
        CURRENT_DISPLAY = DISPLAYS.blueprints;
        return (
          <div className="App-body">
            <EventList setDisplay={setDisplay} setSelectedBatch={setSelectedBatch} />
            <div className="blueprints"></div>
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

  const appendBatchView = () => {
    if (!(selectedBatch == null || selectedBatch == undefined)) {
      return (
        <BatchView batch={selectedBatch} onDeselect={() => setSelectedBatch(null)}/>
      )
    }
  }

  return (
    <div className="App" ref={appRef}>
      <Header setDisplay={setDisplay} setQuery={setQuery} />
      {getBody()}
      {appendBatchView()}
      <Footer />
      <div className="background-image-container">
          <img className="background-image" src={FacilityImage} alt="stars" />
      </div>
    </div>
  )
}

export default App
