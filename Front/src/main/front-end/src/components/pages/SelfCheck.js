import React from 'react';
import '../../App.css'
import Cards from '../Cards';
import Footer from '../Footer';
import SelfCheckSection from '../SelfCheckSection'

function SelfCheck() {
    return (
        <div>
            <SelfCheckSection></SelfCheckSection>
            <Cards></Cards>
            <Footer></Footer>
        </div>
    )
}

export default SelfCheck
