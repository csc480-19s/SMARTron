import React, { Component } from 'react';
import logo from '../assets/logo.svg';
import '../css/App.css';
import alert from '../assets/logo.svg'
import Exam from "./Exam";
import ExamList from "./ExamList"


class Home extends Component {
    constructor(props) {
        super()
        this.state = {
            title1: "Exam 2",
            title2: "Exam 1",
            exams:[<Exam problem={true} text={"Exam 0"} history={props.history}/>]
        }

        this.navResults = this.navResults.bind(this);
        this.launchExam = this.launchExam.bind(this)
    }

    sort = () => {
        var swap = this.state.title1
        this.setState({ title1: this.state.title2 })
        this.setState({ title2: swap })
    }



    navResults(){
        // createBrowserHistory().push("/results");
        this.props.history.push("/results");
    }

    launchExam(){
        this.state.exams.push(<Exam problem={false} text={"Exam " + this.state.exams.length} history={this.props.history}/>)
        this.setState(this.state)
    }

    render() {
        return (
            <div className={"topBar"}>
                <h1 className={"leftA"}>SMART</h1><h1 className={"leftB"}>RON</h1>
                <h1 className={"right"}>Username </h1>
                <img className={"logout"} src={logo} height={40} />
                <h1 className={"welcome"}> Welcome, Bastian Tenbergen</h1>
                <div className={"buttons"}>
                    <button onClick={this.launchExam} className={"scanButton"}>New Test Scan</button>
                    <select className={"select"} onChange={this.sort}><option value={"recent"}>Most Recent </option> <option value={"alpha"}>Alphanumeric</option></select>
                </div>
                <ExamList exams={this.state.exams} history={this.props.history}/>


            </div>
        );
    }

}

export default Home;


/*                    {this.state.show2 ? <p  className={"sec"}>{this.state.title2}  <a onClick={this.showbutton(2)} className={"editName"}><img src={logo} height={25}/></a> </p>:null } {this.state.show2 == false ? <form> <input formAction={this.showbutton(2)} defaultValue={this.state.title2}  type="text"/> <input type="submit"/></form>:null}<img className={"alert"} src={alert} height={25}/><button>Edit Answer Key</button><button>View Results</button>
*/