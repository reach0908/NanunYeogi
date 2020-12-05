
import '../App.css'
import './Cards.css';
import QrCardItem from './QrCardItem'
import naverImage from './images/naver.png'
import kakaoImage from './images/kakao.jpg'
import naverMap from './images/navermap.png'
import MyPageCard from './MyPageCard';
import React, {Component} from 'react'

export default class CheckinCard extends Component {
    
    componentDidMount(){
        console.log(window.localStorage.getItem("id"));
    }

    render(){
        return(
            <div className='cards'>
            <h1>체크인 QR코드를 고르세요!</h1>
            <div className='cards__container'>
                <div className='cards__wrapper'>
                    <ul className='cards__items'>
                        <QrCardItem
                            src={naverImage}
                            text='네이버 QR코드로 체크인하세요.'
                            label='Naver QR-CheckIN'
                            path='/gpslocation'
                        />
                        <QrCardItem
                            src={kakaoImage}
                            text='카카오 QR코드로 체크인하세요.'
                            label='Kakao QR-CheckIN'
                            path='/gpslocation'
                        />
                    </ul>
                    <h2>
                        내 이동경로 확인하기
            </h2>
                    <ul className='cards__items'>
                        <MyPageCard
                            src={naverMap}
                            text='내 이동경로를 확인하세요'
                            label='MyPage'
                            path='/mypage'
                        />
                    </ul>
                </div>
            </div>
        </div>
        )
    }
}

