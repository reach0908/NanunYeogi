import React from 'react';
import '../../App.css'
import CovidState from '../CovidState';
import Footer from '../Footer';
import MapSlider from '../MapSlider';


function Home() {
    return (
        <>
            <MapSlider></MapSlider>
            <div className='cards'>
                <div className='cards__container'>
                    <div className='cards__wrapper'>
                        <ul className='cards__items'>
                            <CovidState></CovidState>
                        </ul>
                    </div>

                </div>
            </div>
            <Footer></Footer>
        </>
    );
}

export default Home;