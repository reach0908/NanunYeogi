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
import Today from './components/pages/Today';

function App() {

  return (
    <>
      <Router>
        <Navbar />
        <Switch>
          <Route path='/' exact component={Home} />
          <Route path='/mypage' component={MyPage} />
          <Route path='/qrcheckin' component={QrCheckIn} />
          <Route path='/selfcheck' component={SelfCheck} />
          <Route path='/sign-up' component={SignUp} />
          <Route path='/login' component={Login} />
          <Route path='/aboutus' component={AboutUs} />
          <Route path='/gpslocation' component={GPSLocation} />
          <Route path='/phoneregister' component={PhoneRegister}/>
          <Route path='/getlocations' component={MyPage}/>
          <Route path='/today' component={Today} />
        </Switch>
      </Router>
    </>
  );
}

export default App;
