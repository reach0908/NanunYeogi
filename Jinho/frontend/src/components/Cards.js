import React from 'react';
import './Cards.css';
import CardItem from './CardItem';
import covidCheck from './images/covid-check.jpg'
import appLogo from './images/app-logo.png'
import pathImg from './images/covid-path.jpg'
import covidState from './images/covid-state.jpg'
import qrcheckImg from './images/qrcheck.PNG'

function Cards() {
  return (
    <div className='cards'>
      <h1>나는여기의 활용법을 구경해 보세요!</h1>
      <div className='cards__container'>
        <div className='cards__wrapper'>
          <ul className='cards__items'>
            <CardItem
              src={appLogo}
              text='나는 여기 는 코로나 확산을 막기위한 자기주도적 진단 앱입니다.'
              label='IMHERE'
              path='/home'
            />
            <CardItem
              src={qrcheckImg}
              text='QR체크인을 하면서 자신의 동선을 기록하세요!'
              label='QR Check-in'
              path='/qrcheckin'
            />
          </ul>
          <ul className='cards__items'>
            <CardItem
              src={pathImg}
              text='나의 경로와 겹치는 확진자에 대하여 알림을 받으세요!'
              label='Path-check'
              path='/mypage'
            />
            <CardItem
              src={covidState}
              text='현재 우리나라의 확진자 현황을 체크하세요!'
              label='COVID-19 State'
              path='/mypage'
            />
            <CardItem
              src={covidCheck}
              text='나의 증상을 확인하고 자가진단을 해보세요!'
              label='Check my symptom'
              path='/selfcheck'
            />
          </ul>
        </div>
      </div>
    </div>
  );
}

export default Cards;