
import React from 'react';
import './Form.css';
import PhoneRegisterForm from './PhoneRegisterForm';
import appLogo from './images/app-logo.png'

const Form = () => {
  return (
    <>
      <div className='form-container'>
        <span className='close-btn'>×</span>
        <div className='form-content-left'>
          <img className='form-img' src={appLogo} alt='spaceship' />
        </div>
          <PhoneRegisterForm/>
      </div>
    </>
  );
};

export default Form;