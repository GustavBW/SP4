import React from 'react';
import './StyleWheel.css';
import { CONFIGURATIONS, applyConfiguration } from '../ts/dynamicStyling';

const StyleWheel = (props: {appRef: React.RefObject<HTMLDivElement>}): JSX.Element => {

    const configARef = React.useRef<HTMLButtonElement>(null);
    const configBRef = React.useRef<HTMLButtonElement>(null);
    const configCRef = React.useRef<HTMLButtonElement>(null);

    const handleConfigChange = (config: number) => {
        switch (config) {
            case 0: {
                deactivateAll();
                if (configARef.current) {
                    configARef.current.classList.add("active");
                    applyConfiguration(CONFIGURATIONS.plain);
                }
                break;
            }
            case 1: {
                deactivateAll();
                if (configBRef.current) {
                    configBRef.current.classList.add("active");
                    applyConfiguration(CONFIGURATIONS.wasp);
                }
                break;
            }
            case 2: {
                deactivateAll();
                if (configCRef.current) {
                    configCRef.current.classList.add("active");
                    applyConfiguration(CONFIGURATIONS.dark);
                }
                break;
            }
        }
    }

    const deactivateAll = () => {
        if (configARef.current) {
            configARef.current.classList.remove("active");
        }
        if (configBRef.current) {
            configBRef.current.classList.remove("active");
        }
        if (configCRef.current) {
            configCRef.current.classList.remove("active");
        }
    }

    return (
        <div className="style-wheel">
            <button onClick={e => handleConfigChange(0)} className="configuration-item config-a" 
                ref={configARef} title="Plain">P</button>
            <button onClick={e => handleConfigChange(1)} className="configuration-item config-b" 
                ref={configBRef} title="Wasp - High Contrast">W</button>
            <button onClick={e => handleConfigChange(2)} className="configuration-item config-c" 
                ref={configCRef} title="Dark">D</button>
        </div>
    )
}
export default StyleWheel;