import React from  'react' ;
import './Confirmation.css' ;
import checkMarkImage from  '../../../images/checkmark.png' ;

const Confirmation = (props: {message: string, close: () => void}): JSX.Element => {

    return (
        <div className= "Confirmation" onClick={() => props.close()}>
            <img src={checkMarkImage} alt= "Checkmark"  className= "checkmark-image" />
            <h2>{props.message}</h2>
            <p>Click to close</p>
        </div>
    )
}
export default Confirmation;