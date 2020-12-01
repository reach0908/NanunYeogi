import React from 'react'
import {RenderAfterNavermapsLoaded, NaverMap, Marker,Polyline} from 'react-naver-maps';




function NaverMapAPI3() {
    return (
      <NaverMap
        id="map3" // default: react-naver-map
        style={{
          width: '100%', // 네이버지도 가로 길이
          height: '100vh' // 네이버지도 세로 길이
        }}
        defaultCenter={{lat:37.3685882848096, lng: 127.1088123321533}} // 지도 초기 위치
        defaultZoom={15} // 지도 초기 확대 배율
      >
        <Polyline 
        path={[
          {lat:37.359924641705476, lng: 127.1148204803467},
          {lat:37.36343797188166, lng: 127.11486339569092},
          {lat:37.368520071054576, lng: 127.11473464965819},
          {lat:37.3685882848096, lng: 127.1088123321533},
        ]}
        strokeColor={'#f04da8'}
        strokeOpacity={0.7}
        strokeWeight={3}
      />
      <Marker
        key={1}
        position={{lat:37.359924641705476, lng: 127.1148204803467}}
        animation={2}
        onClick={() => {alert('확진자 발생 지역입니다.');}}
      />
      <Marker
        key={2}
        position={{lat:37.36343797188166, lng: 127.11486339569092}}
        animation={2}
        onClick={() => {alert('확진자 발생 지역입니다.');}}
      />
      <Marker
        key={3}
        position={{lat:37.368520071054576, lng: 127.11473464965819}}
        animation={2}
        onClick={() => {alert('확진자 발생 지역입니다.');}}
      />
      </NaverMap>
      
    );
  }

function MapCard3() {
    return (
        <RenderAfterNavermapsLoaded
          ncpClientId={'fgocun0nnd'} // 자신의 네이버 계정에서 발급받은 Client ID
          error={<p>Maps Load Error</p>}
          loading={<p>Maps Loading...</p>}
        >
          <NaverMapAPI3 />
        </RenderAfterNavermapsLoaded>
      );
}

export default MapCard3
