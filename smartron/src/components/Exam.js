import React, { Component } from 'react';
import logo from '../assets/logo.svg';
import alert from '../assets/alert.png';
import '../css/exam.css';
class Exam extends Component{
    constructor(props) {
        super()
        this.state = {
            title1: props.text,
            problem: props.problem

        }
        console.log(props.problem)

        this.navResults = this.navResults.bind(this);
        this.navAnswerKey = this.navAnswerKey.bind(this);
    }




    navResults(){
        // createBrowserHistory().push("/results");
        this.props.history.push("/results");
    }
    
    navAnswerKey(){
      this.props.history.push("/answerKey");
    }
    render() {
        return(
            <div>
                <div className={"exam"}>
                    <p className={"fir"}>{this.state.title1}
                        <a className={"editName"}><img src={logo} height={25} /></a>
                    </p>
                    <button onClick={this.navResults}>View Results</button>
                    <button onClick={this.navAnswerKey}>Edit Answer Key</button>

                    {this.props.problem ? <img className={"alert"} src={alert} height={25} /> : null}

                </div>
            </div>

        )
    }


}
export default Exam;
