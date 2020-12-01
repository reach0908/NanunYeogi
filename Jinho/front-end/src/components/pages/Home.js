import React from 'react';
import '../../App.css'
import Cards from '../Cards';
import Footer from '../Footer';
import HeroSection from '../HeroSection'

function Home (){

    return(
        <>
            <HeroSection></HeroSection>
            <Cards></Cards>
            <Footer></Footer>
        </>
    );
}

export default Home;