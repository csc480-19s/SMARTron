import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import {GoogleLogout} from "react-google-login";
import alert from './alert.png'







class Home extends Component {
    constructor(props) {

        super()
        this.state ={
            title1:"Exam 2",
            title2:"Exam 1",
            show1: true,
            show2: true
        }
    }
    sort = () =>{
        var swap =  this.state.title1
        this.setState({title1:this.state.title2})
        this.setState({title2:swap})
    }

    showbutton = (num) =>{
        if(num==1){
            var swap =  this.state.show1
            this.setState({show1:!swap})
        }else{
            var swap = this.state.show2
            this.setState({show2:!swap})
        }
    }




  render() {
    return (
        <div className={"topBar"}>
            <h1 className={"leftA"}>SMART</h1><h1 className={"leftB"}>ron</h1>
            <h1 className={"right"}>Username </h1>
            <img className={"logout"} src={logo} height={40}/>
            <h1 className={"welcome"}> Welcome, Bastian Tenbergen</h1>
            <div className={"buttons"}>
                <button className={"scanButton"}>New Test Scan</button>
                <select className={"select"} onChange={this.sort}><option value={"recent"}>Most Recent </option> <option value={"alpha"}>Alphanumeric</option></select>
            </div>
            <div id={"testList"} className={"testList"}>
                <div className={"test1"}>
                    <p  className={"fir"}>{this.state.title1} <a onClick={this.sort} className={"editName"}><img src={logo} height={25}/></a> </p>{this.state.title1.localeCompare("Exam 2") ? <img className={"alert"} src={alert} height={25}/>:null}<button>Edit Answer Key</button><button>View Results</button>
                </div>
            </div>
            <div className={"testList2"}>
                <div className={"test1"}>
                    <p  className={"sec"}>{this.state.title2} <a onClick={this.sort} className={"editName"}><img src={logo} height={25}/></a> </p>{this.state.title2.localeCompare("Exam 2") ? <img className={"alert"} src={alert} height={25}/>:null}<button>Edit Answer Key</button><button>View Results</button>
                </div>
            </div>
        </div>    );
  }

}

export default Home;


/*                    {this.state.show2 ? <p  className={"sec"}>{this.state.title2}  <a onClick={this.showbutton(2)} className={"editName"}><img src={logo} height={25}/></a> </p>:null } {this.state.show2 == false ? <form> <input formAction={this.showbutton(2)} defaultValue={this.state.title2}  type="text"/> <input type="submit"/></form>:null}<img className={"alert"} src={alert} height={25}/><button>Edit Answer Key</button><button>View Results</button>
*/