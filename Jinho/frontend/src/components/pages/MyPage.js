import React from 'react';
import '../../App.css'
import Footer from '../Footer';
import CovidState from '../CovidState';
import MapSlider from '../MapSlider';


function Home (){

    return(
        <>
            <MapSlider></MapSlider>
            <CovidState></CovidState>
            <Footer></Footer>
        </>
    );
}

export default Home;