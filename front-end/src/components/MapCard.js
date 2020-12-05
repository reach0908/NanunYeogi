import React, { Component } from 'react';
import {RenderAfterNavermapsLoaded, NaverMap, Marker} from 'react-naver-maps';
import axios from 'axios';
import moment from 'moment'

class GetMyLocations extends Component{

  state = {
    startDate: new Date(),
    loading: false,
    locationList: [],
  };
  
  loadLocations = async () => {
    
    // const date = this.state.startDate;
    // console.log(date);

    const date = this.state.startDate;
    const dateTime = moment(date).format("YYYY-MM-DD HH:mm:ss");
    console.log(dateTime);
    axios.get('http://localhost:8080/getlocations/' + window.localStorage.getItem("id"),{params:{date:dateTime}}).then((response)=>{
      this.setState(
        {locationList:response.data}
      )
      console.log(this.state.locationList)
    }).catch((error)=>{
      console.log(error);
    });
  };
    
  handleChange=(date) => {
    this.setState({
      startDate: date
    });
    this.loadLocations();
  }

  componentDidMount() {
    this.loadLocations();
  }

  // getStartLat(){
  //   return this.state.locationList[0].latitude;
  // }
  // getStartLng(){
  //   return this.state.locationList[0].longitude;
  // }

  render() {
    
    const loca = this.state.locationList;
    return (
      <div>
        {loca.map(location=>(
        
        <NaverMap key={location.id}
          id="map1"// default: react-naver-map
          style={{
            width: '100%', // 네이버지도 가로 길이
            height: '100vh' // 네이버지도 세로 길이
          }}
         defaultCenter={{lng: location.longitude, lat: location.latitude}} // 지도 초기 위치
          defaultZoom={13} // 지도 초기 확대 배율
        >
          {loca.map(locationMarker => (
            <div key={locationMarker.id}>
              <Marker
                key={locationMarker.id}
                position={{ lng: locationMarker.longitude, lat: locationMarker.latitude }}
                animation={2}
                onClick={() => { alert('사용자의 방문지 입니다.'); }}
              />
            </div>
          ))}
        </NaverMap>
        ))}
      </div>
         
    );
  }
}

export default class MapCard extends Component{

  render(){
    return (
    
      <RenderAfterNavermapsLoaded
        ncpClientId={'fgocun0nnd'} // 자신의 네이버 계정에서 발급받은 Client ID
        error={<p>Maps Load Error</p>}
        loading={<p>Maps Loading...</p>}
      >
        <GetMyLocations />
      </RenderAfterNavermapsLoaded>
    );
  }
}
