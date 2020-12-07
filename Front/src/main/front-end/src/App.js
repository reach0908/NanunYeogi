import React from 'react'
import './App.css';
import Navbar from './components/Navbar';
import {BrowserRouter as Router, Switch, Route} from 'react-router-dom';
import Home from './components/pages/Home';
import MyPage from './components/pages/MyPage';
import SelfCheck from './components/pages/SelfCheck';
import SignUp from './components/pages/SignUp';
import Login from './components/pages/Login';
import QrCheckIn from './components/pages/QrCheckIn';
import AboutUs from './components/pages/AboutUs'
import PhoneRegister from './components/pages/PhoneRegister'
import GPSLocation from './components/pages/GPSLocation';

function App() {

    return (
        <>
            <Router>
                <Navbar/>
                <Switch>
                    <Link path='/' exact component={Home}/>
                    <Link path='/mypage' component={MyPage}/>
                    <Link path='/qrcheckin' component={QrCheckIn}/>
                    <Link path='/selfcheck' component={SelfCheck}/>
                    <Link path='/sign-up' component={SignUp}/>
                    <Link path='/login' component={Login}/>
                    <Link path='/aboutus' component={AboutUs}/>
                    <Link path='/gpslocation' component={GPSLocation}/>
                    <Link path='/phoneregister/' component={PhoneRegister}/>
                    <Link path='/getlocations' component={MyPage}/>
                </Switch>
            </Router>
        </>
    );
}

export default App;
