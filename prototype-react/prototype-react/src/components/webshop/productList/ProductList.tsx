import React from 'react';
import './ProductList.css';
import ProductThumbnail from '../productThumbnail/ProductThumbnail';

const ProductList = (props: any): JSX.Element => {

    const [size, _] = React.useState(100);

    return (
        <div className="ProductList">
            {
                Array(size).fill(0).map((_, index) => {
                    return <ProductThumbnail key={index} image="https://www.irisvision.com/wp-content/uploads/2019/01/iris-drone-1.jpg" name="Drone" price="$1000" />
                })
            }
        </div>
    )
}
export default ProductList;