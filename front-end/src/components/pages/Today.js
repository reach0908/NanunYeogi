import React, { useState, useEffect,Component } from 'react';
import {Line} from 'react-chartjs-2';
import axios from 'axios';
import { Link } from 'react-router-dom';
import '../Cards.css';
import Table from '@material-ui/core/Table';
import TableRow from '@material-ui/core/TableRow';
import TableHead from '@material-ui/core/TableHead';
import TableCell from '@material-ui/core/TableCell';
import TableBody from '@material-ui/core/TableBody';

class CovidState extends Component{
    constructor(props){
        super(props);
    
        this.state={
          data: {
            labels :["1","2","3","4","5"],
            datasets:[
              {
                label: "전국 전체확진자 수",
                backgroundColor: "rgba(255,0,255,0.75)",
                data: [4,5,1,10,32,2,12]
              },
              {
                label: "전국 신규확진자 수",
                backgroundColor: "rgba(0,255,0,0.75)",
                data: [14,15,21,0,12,4,2]
              }
            ]
          }
        }
    }
    setGradientColor = (canvas, color)=>{
        const ctx = canvas.getContext('2d');
        const gradient = ctx.createLinearGradient(0,0,0,400);
        gradient.addColorStop(0, color);
        gradient.addColorStop(0.95,"rgba(133,255,144,0.85)");
        return gradient;
      }
    
      getChartData = canvas => {
        const data = this.state.data;
        if(data.datasets){
          let colors = ["rgba(255,0,255,0.75)","rgba(0,255,0,0.75)"];
          data.datasets.forEach((set, i)=>{
            set.backgroundColor = this.setGradientColor(canvas,colors[i]);
            set.borderColor = "white";
            set.borderWidth = 2;
          })
        }
        return data;
      }
    
      render(){
        return(
          <div style={{position:"relative",width:800,height:750}}>
            <h3>Chart Samples</h3>
            <Line
              options={{
                responsive: true,
              }}
              data={this.getChartData}
            />
          </div>
        );
      }
}

function Today() {
    const [users, setUsers] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const arrays = [
        { id : 0, site:"https://search.naver.com/search.naver?where=nexearch&sm=top_sug.pre&fbm=1&acr=1&acq=%EC%BD%94%EB%A1%9C%EB%82%98&qdt=0&ie=utf8&query=%EC%BD%94%EB%A1%9C%EB%82%98+%ED%99%95%EC%A7%84%EC%9E%90", names : "해외"},
        { id : 1, site: "https://www.jeju.go.kr/corona19.jsp", names: "제주"},
        { id : 2, site: "http://xn--19-q81ii1knc140d892b.kr/main/main.do", names: "경남"},
        { id : 3, site: "https://www.gb.go.kr/Main/open_contents/section/wel/page.do?mnu_uid=5857&LARGE_CODE=360&MEDIUM_CODE=90&SMALL_CODE=10&mnu_order=2", names: "경북"},
        { id : 4, site: "https://www.jeonnam.go.kr/coronaMainPage.do", names: "전남" },
        { id : 5, site: "https://www.jeonbuk.go.kr/board/list.jeonbuk?boardId=BBS_0000105&menuCd=DOM_000000110001000000&contentsSid=1219&cpath=", names: "전북"},
        { id : 6, site: "http://www.chungnam.go.kr/coronaStatus.do", names: "충남"},
        { id : 7, site: "https://search.naver.com/p/crd/rd?m=1&px=154&py=1048&sx=154&sy=648&p=U8W5xlprvTVsssM%2BStRssssstOh-058750&q=%EC%BD%94%EB%A1%9C%EB%82%98+%ED%99%95%EC%A7%84%EC%9E%90&ie=utf8&rev=1&ssc=tab.nx.all&f=nexearch&w=nexearch&s=2DQLf%2FxYMpHkSMrF7QvgMg%3D%3D&time=1607158941407&bt=29&a=nco_xxe*1&r=1&i=80202lh0_000000D39981&u=http%3A%2F%2Fwww1.chungbuk.go.kr%2Fcovid-19%2Findex.do&cr=1", names: "충북"},
        { id : 8, site: "http://www.provin.gangwon.kr/covid-19.html", names: "강원"},
        { id : 9, site: "https://search.naver.com/p/crd/rd?m=1&px=164&py=837&sx=164&sy=437&p=U8W5xlprvTVsssM%2BStRssssstOh-058750&q=%EC%BD%94%EB%A1%9C%EB%82%98+%ED%99%95%EC%A7%84%EC%9E%90&ie=utf8&rev=1&ssc=tab.nx.all&f=nexearch&w=nexearch&s=2DQLf%2FxYMpHkSMrF7QvgMg%3D%3D&time=1607158967027&bt=29&a=nco_xxe*1&r=1&i=80202lh0_000000D39981&u=https%3A%2F%2Fwww.gg.go.kr%2Fbbs%2FboardView.do%3FbsIdx%3D464%26bIdx%3D2296956%26menuId%3D1535&cr=1", names: "경기"},
        { id : 10, site: "https://www.sejong.go.kr/bbs/R3273/list.do;jsessionid=GjeH5aBxfimtiCVnZldl5gWBveGmhXSKRdxmswBPK3Q9LX3oVgaZG5xxQZgZePEQ.Portal_WAS1_servlet_engine5?cmsNoStr=17465", names: "세종"},
        { id : 11, site: "https://www.ulsan.go.kr/corona.jsp", names: "울산"},
        { id : 12, site: "https://search.naver.com/p/crd/rd?m=1&px=154&py=915&sx=154&sy=515&p=U8W5xlprvTVsssM%2BStRssssstOh-058750&q=%EC%BD%94%EB%A1%9C%EB%82%98+%ED%99%95%EC%A7%84%EC%9E%90&ie=utf8&rev=1&ssc=tab.nx.all&f=nexearch&w=nexearch&s=2DQLf%2FxYMpHkSMrF7QvgMg%3D%3D&time=1607159001347&bt=29&a=nco_xxe*1&r=1&i=80202lh0_000000D39981&u=https%3A%2F%2Fwww.daejeon.go.kr%2Fcorona19%2Findex.do&cr=1", names: "대전"},
        { id : 13, site: "https://www.gwangju.go.kr/c19/", names: "광주"},
        { id : 14, site: "https://www.incheon.go.kr/health/HE020409", names: "인천"},
        { id : 15, site: "http://www.daegu.go.kr/dgcontent/index.do", names: "대구"},
        { id : 16, site: "http://www.busan.go.kr/covid19/Corona19.do", names: "부산"},
        { id : 17, site: "https://www.seoul.go.kr/coronaV/coronaStatus.do", names: "서울"},
        { id : 18, site: "https://search.naver.com/search.naver?where=nexearch&sm=top_sug.pre&fbm=1&acr=1&acq=%EC%BD%94%EB%A1%9C%EB%82%98&qdt=0&ie=utf8&query=%EC%BD%94%EB%A1%9C%EB%82%98+%ED%99%95%EC%A7%84%EC%9E%90", names: "합계"}
        
    ]

    useEffect(() => {
        const fetchUsers = async () => {
            try {
                // 요청이 시작 할 때에는 error 와 users 를 초기화하고
                setError(null);
                setUsers(null);
                // loading 상태를 true 로 바꿈
                setLoading(true);
                const response = await axios.get(
                    'http://nanunyeogi.paas-ta.org/covid'
                );
                setUsers(response.data.item); // 데이터는 response.data 안에^^
                console.log(users);
                console.log(arrays);
            } catch (e) {
                setError(e);
            }
            setLoading(false);
        };

        fetchUsers();
    }, []);

    if (loading) return <div>로딩중..</div>;
    if (error) return <div>에러가 발생했습니다</div>;
    if (!users) return null;
    
    return (
        <>
            <div className='cards'>
            <h1>실시간 코로나 확진자 통계</h1>
            
                <div className='cards__container'>
                    <div className='cards__wrapper'>
                        <h2>신규 확진자 그래프</h2>
                    <CovidState></CovidState>
                        <h5 >{users[0].createDt} 기준</h5>
                        <ul className='cards__items'>
                        
                            <Table>
                                <TableHead>
                                    <TableRow>
                                        <TableCell width='100'>지역</TableCell>
                                        <TableCell width='150'>총확진자 수</TableCell>
                                        <TableCell width='150'>신규확진자 수</TableCell>
                                        <TableCell width='180'>전일 대비 증가추이</TableCell>
                                        <TableCell width='150'>사이트</TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {arrays.map(array => (
                                        <TableRow>
                                            <TableCell>{users[array.id].gubun}</TableCell>
                                            <TableCell>{users[array.id].defCnt}</TableCell>
                                            <TableCell>{users[array.id].incDec}</TableCell>
                                            <TableCell>{users[array.id].incDec - users[array.id + 19].incDec > 0 ? <h5 style={{ color: "red" }}>{users[array.id].incDec - users[array.id + 19].incDec}▲ </h5> : <h5 style={{ color: "blue" }}>{users[array.id].incDec - users[array.id + 19].incDec}▼</h5> }</TableCell>
                                            <TableCell><a target="_blank" href={array.site}>{array.names}홈페이지</a></TableCell>
                                        </TableRow>
                                    ))}
                                </TableBody>
                            </Table>
                        </ul>
                    </div>
                </div>
            </div>
            
        </>
    );
}

export default Today;
