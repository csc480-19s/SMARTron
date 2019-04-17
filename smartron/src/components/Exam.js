import React, { Component } from 'react';
import logo from '../assets/logo.svg';
import alert from '../assets/alert.png';
import '../css/exam.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faPencilAlt } from '@fortawesome/free-solid-svg-icons'
class Exam extends Component{
    constructor(props) {
        super()
        this.state = {
            title1: props.text,
            problem: props.problem,
            exid: props.scanCode
        }
        console.log(props.problem)

        this.navResults = this.navResults.bind(this);
        this.navAnswerKey = this.navAnswerKey.bind(this);
    }




    navResults(){
        // createBrowserHistory().push("/results");
        this.props.history.push({pathname:"/results",state:{id:this.state.id, text:this.state.title1 ,loginName:this.props.loginName,exams:this.props.list,email:this.props.email}});
    }

    navAnswerKey(){
      this.props.history.push({pathname:"/answerKey",state:{id:this.state.id,text:this.state.title1, loginName:this.props.loginName,exams:this.props.list,email:this.props.email}});
    }
    render() {
        return(
            <div>
                <div className={"exam"}>
                    {this.state.title1}
                    <a className={"editName"}><FontAwesomeIcon icon={faPencilAlt}/></a>
                    <button onClick={this.navResults}>View Results</button>
                    <button onClick={this.navAnswerKey}>Edit Answer Key</button>

                    {this.props.problem ? <img className={"alert"} src={alert} height={25} /> : null}

                </div>
            </div>

        )
    }


}
export default Exam;
