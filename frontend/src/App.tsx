import { useState, useRef } from 'react';
import './App.css';
import Header from './components/webshop/header/Header';
import ProductList from './components/webshop/productList/ProductList';
import InTrasitDisplay from './components/webshop/inTransitDisplay/InTransitDisplay';
import SystemStatus from './components/systemStatus/SystemStatus';
import Basket from './components/basket/Basket';
import Footer from './components/webshop/footer/Footer';

//@ts-ignore
import FacilityImage from './images/manufacturing-facility.webp';
import { Batch } from './ts/webshop';
import EventList from './components/webshop/event/EventList';
import BatchView from './components/webshop/event/batchView/batchView';
import {IBasketItem} from './ts/webshop';
import BatchHistory from './components/history/BatchHistory';

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
  const [basketContent, setBasketContent] = useState<IBasketItem[]>([]);

  const addToBasket = (item: IBasketItem) => {
    setBasketContent([...basketContent, item]);
  }

  const getBody = () => {
    switch (display) {
      case DISPLAYS.products: {
        CURRENT_DISPLAY = DISPLAYS.products;
        return (
          <div className="App-body">
            <EventList setDisplay={setDisplay} setSelectedBatch={setSelectedBatch}/>
            <ProductList addToBasket={addToBasket} />
            <InTrasitDisplay />
          </div>
        )
      };
      case DISPLAYS.blueprints: {
        CURRENT_DISPLAY = DISPLAYS.blueprints;
        return (
          <div className="App-body">
            <EventList setDisplay={setDisplay} setSelectedBatch={setSelectedBatch} />
            <BatchHistory setSelectedBatch={setSelectedBatch} />
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
          <Basket setBasketContent={setBasketContent} basketContent={basketContent} setDisplay={setDisplay}/>
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
      <img className="background-image" src={FacilityImage} alt="stars" />
    </div>
  )
}

export default App
