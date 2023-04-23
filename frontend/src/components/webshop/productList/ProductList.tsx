import React from 'react';
import './ProductList.css';
import ProductThumbnail from '../productThumbnail/ProductThumbnail';
import { IBasketItem, Part } from '../../../ts/webshop';
import { getAvailableParts, getWarehouseInventory } from '../../../ts/api';
import PartPage from '../partPage/PartPage';

interface ProductListProps{
    addToBasket: (item: IBasketItem) => void;
}

const ProductList = ({addToBasket}: ProductListProps): JSX.Element => {

    const [size, _] = React.useState(100);
    const [parts, setParts] = React.useState<Part[]>([]);
    const [selectedPart, setSelectedPart] = React.useState<Part | null>(null);

    React.useEffect(() => {
        getAvailableParts()
        .catch(error => console.log(error))
        .then((parts: Part[] | void) => {

            //console.log(parts);
            if (parts) {
                setParts(parts);
            }
        });
    }, []);

    const getPartView = (): JSX.Element => {
        if (selectedPart) {
            return (
                <PartPage addToBasket={addToBasket} part={selectedPart} deselect={handleDeselect}/>
            )
        }
        return <></>
    }

    const handleDeselect = (): void => {
        setSelectedPart(null);
    }

    return (
        <div className="ProductList">
            {parts.map((part: Part, index: number) => {
                return (
                    <div key={index} onClick={e => setSelectedPart(part)}>
                        <ProductThumbnail part={part}/>
                    </div>
                )
            })}
            {getPartView()}
        </div>
    )
}
export default ProductList;