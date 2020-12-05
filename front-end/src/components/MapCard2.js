
import { RenderAfterNavermapsLoaded, NaverMap, Polyline } from 'react-naver-maps';
import React, { Component } from 'react';
import axios from 'axios';
import moment from 'moment'

class GetMyPaths extends Component {

  state = {
    startDate: new Date(),
    loading: false,
    locationList: [],
    startLoca: {}
  };

  loadLocations = async () => {

    // const date = this.state.startDate;
    // console.log(date);

    const date = this.state.startDate;
    const dateTime = moment(date).format("YYYY-MM-DD HH:mm:ss");
    console.log(dateTime);
    axios.get('http://localhost:8080/getpaths/' + window.localStorage.getItem("id"), { params: { date: dateTime } }).then((response) => {
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
    const startloca = loca.slice(1,2);
    console.log(startloca);
    return (
          <div>
            {startloca.map(startMarker=>(
            <NaverMap key={startMarker.id}
              id="map2"// default: react-naver-map
              style={{
                width: '100%', // 네이버지도 가로 길이
                height: '100vh' // 네이버지도 세로 길이
              }}
              defaultCenter={startMarker} // 지도 초기 위치
              defaultZoom={15} // 지도 초기 확대 배율
            >
                <div>
                  <Polyline
                    path={loca}
                    strokeColor={'#f04da8'}
                    strokeOpacity={0.7}
                    strokeWeight={5}
                  />
                </div>
            </NaverMap>
            ))}
          </div>
    );
  }
}

function MapCard2() {
    return (
        <RenderAfterNavermapsLoaded
          ncpClientId={'fgocun0nnd'} // 자신의 네이버 계정에서 발급받은 Client ID
          error={<p>Maps Load Error</p>}
          loading={<p>Maps Loading...</p>}
        >
          <GetMyPaths/>
        </RenderAfterNavermapsLoaded>
      );
}

export default MapCard2
