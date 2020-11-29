import React from 'react';
import './Cards.css';
import CardItem from './CardItem';
import sampleImages1 from  './images/img-1.jpg'
import sampleImages2 from  './images/img-2.jpg'
import sampleImages3 from  './images/img-3.jpg'
import sampleImages4 from  './images/img-4.jpg'
import sampleImages5 from  './images/img-5.jpg'

function CovidState() {
  return (
    <div className='cards'>
      <h1>현재 코로나 현황</h1>
      <div className='cards__container'>
        <div className='cards__wrapper'>
          <ul className='cards__items'>
            <CardItem
              src={sampleImages1}
              text='Explore the hidden waterfall deep inside the Amazon Jungle'
              label='Adventure'
              path='/sign-up'
            />
            <CardItem
              src={sampleImages2}
              text='Travel through the Islands of Bali in a Private Cruise'
              label='Luxury'
              path='/sign-up'
            />
          </ul>
          <ul className='cards__items'>
            <CardItem
              src={sampleImages3}
              text='Set Sail in the Atlantic Ocean visiting Uncharted Waters'
              label='Mystery'
              path='/sign-up'
            />
            <CardItem
              src={sampleImages4}
              text='Experience Football on Top of the Himilayan Mountains'
              label='Adventure'
              path='/sign-up'
            />
            <CardItem
              src={sampleImages5}
              text='Ride through the Sahara Desert on a guided camel tour'
              label='Adrenaline'
              path='/sign-up'
            />
          </ul>
        </div>
      </div>
    </div>
  );
}

export default CovidState;