import React, { Component } from "react";
import axios from "axios";
import { Bar, Line } from "react-chartjs-2";

class CovidState extends Component {
    constructor(props) {
        super(props);
        this.state = {
            chartData: {}
        };
    }
    componentDidMount() {
        axios.get("http://nanserver.paas-ta.org/covid")
            .then(res => {
                this.getChartData(res.data);
                console.log(res.data);
            })
            .catch(err => console.log(err));
    }
    getChartData(data) {
        const cityNames = data.item.reduce((acc, cur) => [...acc, cur.gubun], []);
        const cityPop = data.item.reduce((acc, cur) => [...acc, cur.incDec], []);
        console.log(cityNames);
        console.log(cityPop[0]);
        this.setState({
            ...this.state,
            chartData: {
                labels: [cityNames[0], cityNames[1], cityNames[2], cityNames[3], cityNames[4], cityNames[5], 
                cityNames[6], cityNames[7], cityNames[8], cityNames[9], cityNames[10], cityNames[11], 
                    cityNames[12], cityNames[13], cityNames[14], cityNames[15], cityNames[16], cityNames[17]],
                datasets: [
                    {
                        label: "오늘 :" + data.item[0].createDt + " 기준",
                        data: [cityPop[0], cityPop[1], cityPop[2], cityPop[3], cityPop[4], cityPop[5], cityPop[6], cityPop[7], cityPop[8], cityPop[9], cityPop[10]
                            , cityPop[11], cityPop[12], cityPop[13], cityPop[14], cityPop[15], cityPop[16], cityPop[17]],
                        backgroundColor: "rgba(54, 162, 235, 0.6)"                       
                    },
                    {
                    label: "전날 :" + data.item[19].createDt + " 기준",
                    data: [cityPop[19], cityPop[20], cityPop[21], cityPop[22], cityPop[23], cityPop[24], cityPop[25], cityPop[26], cityPop[27], cityPop[28], cityPop[29]
                        , cityPop[30], cityPop[31], cityPop[32], cityPop[33], cityPop[34], cityPop[35], cityPop[36]],
                        backgroundColor: "rgba(255, 99, 132, 0.6)"
                    }
                ]
            }
        })
    }
    render() {
        return (
            <>
            <div style={{ position: "relative", width: 800, height: 500 }}>
                <h1>전일 대비 감염자 증가추이-1</h1>
                <Bar data={this.state.chartData} />
            </div>
            <div style={{ position: "relative", width: 800, height: 500 }}>
                <h1>전일 대비 감염자 증가추이-2</h1>
                <Line data={this.state.chartData}/>
            </div>
            </>
        );
    }
}
export default CovidState;