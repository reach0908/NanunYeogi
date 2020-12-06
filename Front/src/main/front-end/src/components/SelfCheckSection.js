import React from 'react';
import '../App.css';
import {Button} from './Button';
import {LoginButton} from './LoginButton';
import './HeroSection.css';

function SelfCheckSection() {
    return (
        <div className='self-container'>
            <h1 className='title'>Self-Check</h1>
            <p>현재 코로나 거리두기 상태 : 2.5단계</p>
            <p>2주간 사용자 외출 횟수 : 24회</p>
            <p>일일 최대 시설 방문 횟수 : 5회</p>
            <p className="self-alert">외출이 잦습니다. 코로나 감염예방을 위해 집콕을 추천드려요!</p>
        </div>
    )
}

export default SelfCheckSection
