import React from  'react' ;
import './Confirmation.css' ;
//@ts-ignore
import checkMarkImage from  '../../../images/checkmark.png' ;

interface ConfirmationProps{
    deselect: () => void;
    message: string;
}

const Confirmation = ({deselect, message}: ConfirmationProps): JSX.Element => {

    return (
        <div className= "Confirmation" onClick={() => deselect()}>
            <img src={checkMarkImage} alt= "Checkmark"  className= "checkmark-image" />
            <h2>{message}</h2>
            <p>Click to close</p>
        </div>
    )
}
export default Confirmation;