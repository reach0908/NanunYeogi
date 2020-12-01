import React, {useState, useEffect} from 'react';
import {Link} from 'react-router-dom';
import {Button} from './Button';
import './Navbar.css';
import LogoImage from './images/app-logo.png';

function Navbar() {
    const [click, setClick] = useState(false);
    const [button, setButton] = useState(true);

    const handleClick = () => setClick(!click);
    const closeMobileMenu = () => setClick(false);

    const showButton = () => {
        if(window.innerWidth<=960){
            setButton(false);
        }else{
            setButton(true);
        }
    }

    useEffect(() =>{
        showButton();
    },[]);

    window.addEventListener('resize',showButton);
    return (
        <>
            <nav className="navbar">
                <div className="navbar-container">
                   <Link to="/" className="navbar-logo" onClick={closeMobileMenu}>
                        <img className="logo-image" src={LogoImage} alt="Logo_image"/>
                   </Link>
                   <div className="menu-icon" onClick={handleClick}>
                   <i className={click ? 'fas fa-times' : 'fas fa-bars'}></i>
                   </div>
                   <ul className={click ? 'nav-menu active' : 'nav-menu'}>
                       <li className='nav-item'>
                        <Link to='/qrcheckin' className='nav-links' onClick={closeMobileMenu}>
                            QR Check-in
                        </Link>
                       </li>
                       <li className='nav-item'>
                        <Link to='/mypage' className='nav-links' onClick={closeMobileMenu}>
                            My Page
                        </Link>
                       </li>
                       <li className='nav-item'>
                        <Link to='/selfcheck' className='nav-links' onClick={closeMobileMenu}>
                            Self-Check
                        </Link>
                       </li>
                       <li className='nav-item'>
                        <Link to='/sign-up' className='nav-links-mobile' onClick={closeMobileMenu}>
                            SIGN-UP
                        </Link>
                       </li>
                   </ul>
                   {button && <Button buttonStyle='btn--outline'>SIGN-UP</Button>}
                </div>
            </nav>
        </>
    )
}

export default Navbar
