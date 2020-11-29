import React from 'react';
import './Footer.css';
import {Link} from 'react-router-dom';

function Footer() {
    return (
        
        <div className='footer-container'>
            <div className="footer-links">
                <div className="footer-link-wrapper">
                    <div className="footer-link-items">
                        <h2>About us</h2>
                        <Link to='/aboutus'>1.asdasd</Link>
                        <Link to='/'>2.asdasd</Link>
                    </div>
                </div>
                <div className="footer-link-wrapper">
                    <div className="footer-link-items">
                        <h2>사용한 API</h2>
                        <Link to='/aboutus'>1.asdasd</Link>
                        <Link to='/'>2.asdasd</Link>
                    </div>
                </div>
                <div className="footer-link-wrapper">
                    <div className="footer-link-items">
                        <h2>메뉴</h2>
                        <Link to='/aboutus'>1.asdasd</Link>
                        <Link to='/'>2.asdasd</Link>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Footer
