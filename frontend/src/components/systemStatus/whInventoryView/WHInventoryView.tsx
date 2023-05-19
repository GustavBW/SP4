import React from 'react';
import './WHInventoryView.css';
import { WHItem } from '../../../ts/types';
import { getWarehouseInventory } from '../../../ts/api';

interface Props {
    onDeselect: () => void;
}

export default function WHInventoryView({ onDeselect }: Props): JSX.Element {
    const [inventory, setInventory] = React.useState<WHItem[]>([]);

    React.useEffect(() => {
        const timer = setInterval(() => {
            getWarehouseInventory().then(items => {
                setInventory(items);
            })
        }, 1000);

        return () => clearInterval(timer);
    },[]);

    return (
        <div className="WHInventoryView">
            <div className="inventory-header">
                <h1>Inventory</h1>
                <button className="chip" onClick={onDeselect}>X</button>
            </div>
            <div className="inventory-body">
                <div className="inventory-item">
                    <h3>Tray</h3>
                    <h3>Content</h3>
                </div>
                {inventory.map((item, index) => {
                    return (
                        <div className="inventory-item" key={index}>
                            <h3>{item.id}</h3>
                            <h3>{item.content}</h3>
                        </div>
                    )
                })}

            </div>

        </div>
    )
}