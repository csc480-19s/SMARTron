import React, { Component } from 'react';
import logo from '../assets/logo.svg';
import '../css/App.css';
import Exam from "./Exam";
import ExamList from "./ExamList"


class Home extends Component {
    //Constructor binds methods and creates an exam list used by the ExamList component
    constructor(props) {
        super()
        this.state = {
            exams:[<Exam problem={true} text={"Exam 0"} history={props.history}/>]
        }

        this.navResults = this.navResults.bind(this);
        this.launchExam = this.launchExam.bind(this)
    }



    //Navigates to results page
    navResults(){
        this.props.history.push("/results");
    }

    //Creates a new exam via popup dialog
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