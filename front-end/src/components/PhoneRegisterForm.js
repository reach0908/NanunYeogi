import React, {Component} from 'react'
import axios from 'axios'
import queryString from 'query-string';
import { Link } from 'react-router-dom';
import './Form.css';

export default class PhoneRegisterForm extends Component {
    
    componentDidMount(){
        const {search} = window.location;
        const query = queryString.parse(search);
        const {id} = query;
        console.log(id);
        window.localStorage.setItem("id",id);
        console.log(localStorage.getItem("id"));
    }
    
    state = {
        phoneNumber: '',
    }

    handleChange = event => {
        this.setState({phoneNumber: event.target.value});
        
    }
    
    handleSubmit = event =>{     

        event.preventDefault();
        // console.log(this.state.phoneNumber);
        const phone = this.state.phoneNumber;
        
        console.log(phone);
        
        axios.post('http://localhost:8080/phoneregister/'+window.localStorage.getItem("id"),{phoneNumber: this.state.phoneNumber})
        .then((res)=>{
            window.location.href='/qrcheckin';
        }).catch((err)=>{
            console.log(err);
        })
    }
    
  
    render(){
        return(
            <>
            <div className='form-content-right'>
            <form onSubmit={this.handleSubmit} className='form' noValidate>
              <h1>
                알림을 받기 위한 핸드폰 번호를 입력해 주세요!
              </h1>
              <div className='form-inputs'>
                <label className='form-label'>Email</label>
                <input
                  className='form-input'
                  type='text'
                  name='phoneNumber'
                  placeholder='- 없이 입력해주세요!'
                  onChange={this.handleChange}
                />
              </div>
              <button className='form-input-btn' type='submit'>
                폰 정보 업데이트
              </button>
              <span className='form-input-login'>
                알림을 받지 않고 이용하시겠어요? Just Login <Link to='/qrcheckin'>here</Link>
              </span>
            </form>
          </div>
          </>
        );
    }
}