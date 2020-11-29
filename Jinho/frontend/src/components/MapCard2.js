import React from 'react'
import {RenderAfterNavermapsLoaded, NaverMap, Polyline} from 'react-naver-maps';


function NaverMapAPI2() {
    
    return (
      <NaverMap
        id="map2" // default: react-naver-map
        style={{
          width: '100%', // 네이버지도 가로 길이
          height: '100vh' // 네이버지도 세로 길이
        }}
        defaultCenter={{lat:37.359924641705476, lng: 127.1148204803467}} // 지도 초기 위치
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
      </NaverMap>
    );
  }

function MapCard2() {
    return (
        <RenderAfterNavermapsLoaded
          ncpClientId={'fgocun0nnd'} // 자신의 네이버 계정에서 발급받은 Client ID
          error={<p>Maps Load Error</p>}
          loading={<p>Maps Loading...</p>}
        >
          <NaverMapAPI2 />
        </RenderAfterNavermapsLoaded>
      );
}

export default MapCard2
