import React, { Component } from 'react';
import logo from '../assets/logo.svg';
import '../css/App.css';
import Exam from "./Exam";
import ExamList from "./ExamList"
import examJSON from "../JSON/Mainpage"
import Popup from "reactjs-popup";

class Home extends Component {
    //Constructor binds methods and creates an exam list used by the ExamList component
    constructor(props) {
        super()

        this.state = {
            exams:[],
            random:"",
            newName:"",
            newNum:"",
        }

        this.navResults = this.navResults.bind(this);
        this.launchExam = this.launchExam.bind(this)
        this.generateCode =this.generateCode.bind(this)
        this.handleName = this.handleName.bind(this)
        this.handleNum = this.handleNum.bind(this)

    }
    componentDidMount() {
        examJSON.examList.forEach((exam)=>
            this.state.exams.push(<Exam problem={false} text={exam.examName} scanCode={exam.scanCode} history={this.props.history}/>)
    );
        this.setState(this.state)
    }


    //Navigates to results page
    navResults(){
        this.props.history.push("/results");
    }

    //Creates a new exam via popup dialog
    launchExam(name,id){

        this.state.exams.push(<Exam problem={false} text={name} id={id} history={this.props.history}/>)
        this.setState(this.state)
    }
    generateCode(){
        var id = ""
        var poss = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        for (let i = 0; i < 5; i++) {
            id += poss.charAt(Math.floor(Math.random()*25))
        }
        console.log(id)
        this.state.random = id
        this.setState(this.state)
    }
    handleName(event) {
        this.setState({newName: event.target.value});
    }
    handleNum(event) {
        this.setState({newNum: event.target.value});
    }

    render() {
        return (
            <div className={"topBar"}>
                <h1 className={"leftA"}>SMART</h1><h1 className={"leftB"}>RON</h1>
                <h1 className={"right"}>{this.props.location.state.email} </h1>
                <img className={"logout"} src={logo} height={40} />
                <h1 className={"welcome"}> Welcome, {this.props.location.state.loginName}</h1>
                <div className={"buttons"}>
                    <Popup  onClose={()=>{this.setState(this.state)}} onOpen={this.generateCode} modal trigger={<button   className={"scanButton"}>New Test Scan</button>}>
                        {close =>
                            <div className={"modal"}>

                                <h1> New Test Scan</h1>
                                <form>
                                    <label> Enter name of test: <input name={"newName"} value={this.state.newName}
                                                                       onChange={this.handleName}/></label> <br/>
                                    <label> Enter number of questions: <input name={"newNum"} value={this.state.newNum}
                                                                              onChange={this.handleNum}/></label>
                                </form>

                                <Popup onOpen={this.generateCode} modal trigger={<button>Submit</button>}>
                                    <h1>{this.state.random}</h1>
                                    <button onClick={() => {
                                        this.state.exams.push(<Exam problem={false} text={this.state.newName} id={this.state.random} numQuest={this.state.newNum} history={this.props.history}/>);
                                        close()
                                    }}>Ok
                                    </button>
                                </Popup>
                            </div>
                        }
                    </Popup>
                    <select className={"select"} onChange={this.sort}><option value={"recent"}>Most Recent </option> <option value={"alpha"}>Alphanumeric</option></select>
                </div>
                <ExamList loginName={this.props.location.state.loginName} email={this.props.location.state.email} exams={this.state.exams} history={this.props.history}>{this.state.exams}</ExamList>


            </div>
        );
    }

}

export default Home;