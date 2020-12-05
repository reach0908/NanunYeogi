import React, {useState} from 'react'
import MapCard from './MapCard';
import MapCard2 from './MapCard2';
import MapCard3 from './MapCard3';
import './MapSlider.css'



function MapSlider() {
    
    var sliderArr = [
        <MapCard/>,
        <MapCard2/>,
        <MapCard3/>
    ]

    const [x,setX] = useState(0);

    const goLeft =()=>{
        console.log(x);
        x === 0 ? setX(-100*(sliderArr.length-1)) : setX(x+100);
    };
    const goRight =()=>{
        console.log(x);
        x === -100*(sliderArr.length-1) ? setX(0) : setX(x-100);
    };

    return (
        <div>
        <div className='slider'>
            {sliderArr.map((item,index)=>{
                return(
                    <div key={index} className="slide" style={{transform:`translateX(${x}%)`}}>
                        {item}
                    </div>
                );
            })}
            <button id="goLeft" onClick={goLeft}>
                <i class="fas fa-chevron-left"/>
            </button>
            <button id="goRight" onClick={goRight}>
                <i class="fas fa-chevron-right"/>
            </button>
        </div>
        </div>
    );
}

export default MapSlider
