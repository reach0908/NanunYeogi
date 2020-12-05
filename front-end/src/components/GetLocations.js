// import React, { Component } from 'react';
// import axios from 'axios';
// import moment from 'moment'
// import "react-datepicker/dist/react-datepicker.css";

// export default class GetMyLocations extends Component {

//   state = {
//     startDate: new Date(),
//     loading: false,
//     locationList: [],
//   };
  
//   loadLocations = async () => {
    
//     const date = this.state.startDate;
//     const dateTime = moment(date).format("YYYY-MM-DD HH:mm:ss");
//     console.log(dateTime);
//     axios.get('http://localhost:8080/getlocations/' + window.localStorage.getItem("id"),{params:{date:dateTime}}).then((response)=>{
//       this.setState(
//         {locationList:response.data}
//       )
//       console.log(this.state.locationList)
//     }).catch((error)=>{
//       console.log(error);
//     });
//   };
  
//   handleChange=(date) => {
//     this.setState({
//       startDate: date
//     });
//     this.loadLocations();
//   }

//   componentDidMount() {
//     this.loadLocations();
//   }

//   render() {

//     return (
//       <div>
        
//       </div>
//     );
//   }
// }