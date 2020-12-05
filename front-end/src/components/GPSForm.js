
import React from 'react';
import './Form.css';
import GPSRegisterForm from './GPSRegisterForm';
import appLogo from './images/app-logo.png'

const Form = () => {
  return (
    <>
      <div className='form-container'>
        <div className='form-content-left'>
          <img className='form-img' src={appLogo} alt='spaceship' />
        </div>
          <GPSRegisterForm/>
      </div>
    </>
  );
};

export default Form;