import React from 'react';
import '../../App.css'
import Footer from '../Footer';
import CovidState from '../CovidState';
import MapSlider from '../MapSlider';
// import GetMyLocations from '../GetLocations';


function Home (){
    return(
        <>
            <MapSlider></MapSlider>
            {/* <GetMyLocations></GetMyLocations> */}
            <CovidState></CovidState>
            <Footer></Footer>
            
        </>
    );
}

export default Home;