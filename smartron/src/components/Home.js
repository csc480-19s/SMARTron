import React, { Component } from 'react';
import logo from '../assets/logo.svg';
import '../css/App.css';
import Exam from "./Exam";
import ExamList from "./ExamList"
import Popup from "reactjs-popup";
import Header from "./Header";

class Home extends Component {
    //Constructor binds methods and creates an exam list used by the ExamList component
    constructor(props) {
        super()

        this.state = {
            exams:[],
            exams2:[],
            random:"",
            newName:"",
            newNum:"",
            swap:false
        }

        this.generateCode =this.generateCode.bind(this)
        this.handleName = this.handleName.bind(this)
        this.handleNum = this.handleNum.bind(this)
        this.sort = this.sort.bind(this)
        this.resetNewTest = this.resetNewTest.bind(this)

    }
    componentDidMount() {

            this.setState(this.state)
            var ex1 = []
            var ex2 = []
            const email = this.props.location.state.email
            console.log(`pi.cs.oswego.edu:13126/exam?em=${email}`)
            fetch(`http://pi.cs.oswego.edu:13126/exam?em=${email}`)
                .then(response => response.json())
                .then(result =>{
                    result.examList.forEach((exam =>
                            this.state.exams.push(<Exam problem={false} loginName={this.props.location.state.loginName}  list={[]}
                                                        email={this.props.location.state.email} text={exam.examName} scanCode={exam.examCode}
                                                        history={this.props.history}/>)

                    )); this.setState(this.state)
                })
            console.log(this.state.exams)

            this.state.exams = ex2
            this.state.exams2= ex2.reverse()
                this.setState(this.state)

    }





    generateCode(){
        var id = ""
        var poss = "BCDFGHJKLMNPQRSTVWXYZ"
        for (let i = 0; i < 5; i++) {
            id += poss.charAt(Math.floor(Math.random()*20))
        }
        this.state.random = id
        this.setState(this.state)
    }
    handleName(event) {
        this.setState({newName: event.target.value});
    }
    handleNum(event) {
        this.setState({newNum: event.target.value});
    }
    sort(){
        var tmp = this.state.swap
        this.state.swap = !tmp
        this.setState(this.state)
    }
    resetNewTest(){
        this.state.newName = ""
        this.state.newNum = ""
        this.setState(this.state)
    }

    render() {
        return (
            <div>
                <h1 className={"welcome"}> Welcome, {this.props.location.state.loginName}</h1>
                <div className={"buttons"}>
                    <Popup  onClose={this.resetNewTest} onOpen={this.generateCode} modal trigger={<button   className={"scanButton"}>New Test Scan</button>}>
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
                                    <h1 className={"id"}>{this.state.random}</h1>
                                    <p className={"scanCode"}>Scan Code</p>
                                    <p>

                                        1. Enter this code in the NAME FIELD of the ANSWER KEY scantron <br/>

                                        2. Only enter 25 SCANTRONS in the scanner at a time <br/>
                                        3. Place ANSWER KEY scantron on the TOP of each pile of answer sheets <br/>
                                        4. Students MUST fill out the ID FIELD on their Scantron Sheets

                                    </p>
                                    <button onClick={() => {
                                        fetch(`http://pi.cs.oswego.edu:13126/exam?email=${this.props.location.state.email}&id=${this.state.random}&name=${this.state.newName}`,{
                                            method: 'post'
                                        })
                                        this.state.exams.push(<Exam problem={false} loginName={this.props.location.state.loginName} scanCode={this.state.random} email={this.props.location.state.email} list={[]} text={this.state.newName} id={this.state.random} numQuest={this.state.newNum} history={this.props.history}/>); this.setState(this.state)
                                        this.resetNewTest()
                                        close()
                                    }}>Ok
                                    </button>
                                </Popup>
                            </div>
                        }
                    </Popup>
                    <select className={"select"} onChange={this.sort}><option value={"recent"}>Most Recent </option> <option value={"alpha"}>Alphanumeric</option></select>
                </div>
                {this.state.swap ? <ExamList loginName={this.props.location.state.loginName} email={this.props.location.state.email} exams={this.state.exams} history={this.props.history}>{this.state.exams}</ExamList> : <ExamList loginName={this.props.location.state.loginName} email={this.props.location.state.email} exams={this.state.exams} history={this.props.history}>{this.state.exams2}</ExamList>}

                <Header history={this.props.history} email={this.props.location.state.email}/>
            </div>
        );
    }

}

export default Home;
