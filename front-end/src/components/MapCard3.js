
import {RenderAfterNavermapsLoaded, NaverMap, Marker} from 'react-naver-maps';
import React, { Component } from 'react';
import axios from 'axios';
import moment from 'moment'

class GetCovidandMyPath extends Component {

  state = {
    startDate: new Date(),
    loading: false,
    locationList: [],
    startLoca: {}
  };

  loadLocations = async () => {
    const date = this.state.startDate;
    const dateTime = moment(date).format("YYYY-MM-DD HH:mm:ss");
    console.log(dateTime);
    axios.get('http://localhost:8080/getCovidLocations', { params: { date: dateTime } }).then((response) => {
      this.setState(
        { locationList: response.data }
      )
      console.log(this.state.locationList)
    }).catch((error) => {
      console.log(error);
    });
  };

  handleChange = (date) => {
    this.setState({
      startDate: date
    });
    this.loadLocations();
  }

  componentDidMount() {
    this.loadLocations();
  }

  render() {

    const loca = this.state.locationList;
    const startloca = loca.slice(1, 2);
    console.log(startloca);
    return (
      <div>
        {startloca.map(startMarker=>(
          <NaverMap key={startMarker.id}
            id="map3"// default: react-naver-map
            style={{
              width: '100%', // 네이버지도 가로 길이
              height: '100vh' // 네이버지도 세로 길이
            }}
            defaultCenter={{lng: startMarker.longitude, lat: startMarker.latitude}} // 지도 초기 위치
            defaultZoom={13} // 지도 초기 확대 배율
          >
            {loca.map(locationMarker => (
            <div key={locationMarker.id}>
              <Marker
                key={locationMarker.id}
                position={{ lng: locationMarker.longitude, lat: locationMarker.latitude }}
                animation={2}
                onClick={() => { alert('확진자 발생지역입니다.'); }}
              />
            </div>
          ))}
          </NaverMap>
        ))}
      </div>
    );
  }
}

function MapCard3() {
    return (
        <RenderAfterNavermapsLoaded
          ncpClientId={'fgocun0nnd'} // 자신의 네이버 계정에서 발급받은 Client ID
          error={<p>Maps Load Error</p>}
          loading={<p>Maps Loading...</p>}
        >
          <GetCovidandMyPath></GetCovidandMyPath>
        </RenderAfterNavermapsLoaded>
      );
}

export default MapCard3
