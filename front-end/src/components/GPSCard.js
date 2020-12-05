import axios from "axios";
import React, { Component } from "react";

export default class GPSCard extends Component {

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

    moveToMypage(){
        window.location.href = '/mypage';
    }

    wrapperFunction = () =>{
        this.handleSubmit();
        this.moveToMypage();
    }

    render() {
        return (
            <div>
                 <a href='/mypage' onClick={this.handleSubmit}>확인하였습니다</a>
            </div>
        )
    }
}