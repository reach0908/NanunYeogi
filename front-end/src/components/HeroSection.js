import React from 'react';
import '../App.css';
import { Button } from './Button';
import {LoginButton} from './LoginButton';
import './HeroSection.css';

function HeroSection() {
    return (
        <div className='hero-container'>
            <h1 className='title'>나는 여기</h1>
            <p>사용자 이동경로 기반 자가진단 앱</p>
            <p>QR코드를 체크하면서 자신의 이동경로를 기록하고</p>
            <p>코로나 격상상태에 따른 자가진단을 해보세요!</p>
            <div className="hero-btns">
                <LoginButton className='btnsLogin' buttonStyle='btn--outline'buttonSize='btn--large'>
                    로그인
                </LoginButton>
                <Button className='btns' buttonStyle='btn--primary'buttonSize='btn--large'>
                    회원 가입
                </Button>
            </div>
        </div>
    )
}

export default HeroSection
