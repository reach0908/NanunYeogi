import React from 'react';
import { Link } from 'react-router-dom';

function MyPageCard(props) {
  return (
    <>
      <li className='cards__item'>
        <Link className='mypagecards__item__link' to={props.path}>
          <figure className='cards__item__pic-wrap' data-category={props.label}>
            <img
              className='mypagecards__item__img'
              alt='mypageLink'
              src={props.src}
            />
          </figure>
          <div className='cards__item__info'>
            <h5 className='cards__item__text'>{props.text}</h5>
          </div>
        </Link>
      </li>
    </>
  );
}

export default MyPageCard;