import React from 'react';
import '../../App.css'
import Footer from '../Footer';
import MapSlider from '../MapSlider';
import Today from './Today'



function Home (){
    return(
        <>
            <MapSlider></MapSlider>
            <Today></Today>
            <Footer></Footer>
        </>
    );
}

export default Home;