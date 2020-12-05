import React, {Component} from 'react'
import axios from 'axios'
import './Form.css';

export default class PhoneRegisterForm extends Component {
    
  constructor(props) {
    super(props);
    this.state = {
        isStored: false
    }
}

componentDidMount() {
    navigator.geolocation.getCurrentPosition(function (position) {
        console.log("Latitude is :", position.coords.latitude);
        console.log("Longitude is :", position.coords.longitude);
        window.localStorage.setItem("lat", position.coords.latitude);
        window.localStorage.setItem("lng", position.coords.longitude);
    });


}
handleSubmit() {
  axios.post('http://localhost:8080/setlocations/' + window.localStorage.getItem("id"),
      {
          latitude: window.localStorage.getItem("lat"),
          longitude: window.localStorage.getItem("lng")
      }).then(response => {
      }).catch(error => {
          console.log(error);
      });
}

windowchange(){
  this.window.location.replace('/mypage');
}
    render(){
        return(
            <>
            <div className='form-content-right'>
            <form onSubmit={this.handleSubmit} className='form' noValidate>
              <h1>
                내 QR체크인 위치가 저장되었습니다!
              </h1>
              <a className='form-input-btn' href='/mypage' onClick={this.windowchange}>
                내 이동경로 확인하기
              </a>
              <span className='form-input-login'>
              확인 후 QR체크인 화면으로 이동 <a href='/qrcheckin' onClick={this.handleSubmit}>here</a>
              </span>
              <span className='form-input-login'>
              현재 위치를 저장하지 않겠습니다. <a href='/mypage'>here</a>
              </span>
            </form>
          </div>
          </>
        );
    }
}