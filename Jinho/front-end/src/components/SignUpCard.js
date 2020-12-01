import React from 'react';
import '../App.css'
import './Cards.css';
import CardItem from './AtagCardItem';
import naverImage from  './images/naver.png'
import kakaoImage from  './images/kakao.jpg'

function SignUpCard() {

    return (
        <div className='cards'>
            <h1>회원가입할 수단을 고르세요!</h1>
            <div className='cards__container'>
                <div className='cards__wrapper'>
                    <ul className='cards__items'>
                        <CardItem
                            src={naverImage}
                            text='네이버로 회원가입하기.'
                            label='Naver'
                            path='https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=NpWqZIHItQqZii0GRios&redirect_uri=http://localhost:8080/ncallback&state=130'
                        />
                        
                        <CardItem
                            src={kakaoImage}
                            text='카카오로 회원가입하기.'
                            label='Kakao'
                            path='https://kauth.kakao.com/oauth/authorize?client_id=a863152a6c9a88819b4482a0b970723a&redirect_uri=http://localhost:8080/kcallback&response_type=code'
                        />
                    </ul>
                </div>
            </div>
        </div>
    );
}
export default SignUpCard;
