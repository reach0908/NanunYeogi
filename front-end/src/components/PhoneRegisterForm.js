import React, {Component} from 'react'
import axios from 'axios'
import queryString from 'query-string';
// import ApiService from '../ApiService'

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
            <div>
                <form onSubmit={this.handleSubmit}>
                    <label>
                        Phone number:
                        <input type="text" name="phoneNumber" onChange={this.handleChange}/>
                    </label>
                    <button type="submit">Add</button>
                </form>
            </div>
        )
    }
}