import React from 'react';
import {Link} from 'react-router-dom';

function KaKaoQrCardItem(props) {
    return (
        <>
            <li className='cards__item'>
                <Link className='cards__item__link' to={props.path}
                      onClick={() => window.open("https://accounts.kakao.com/qr_check_in#page-qr-check-in", "_blank")}>
                    <figure className='cards__item__pic-wrap' data-category={props.label}>
                        <img
                            className='cards__item__img'
                            alt='Example_Image'
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

export default KaKaoQrCardItem;