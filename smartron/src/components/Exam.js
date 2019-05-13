import React, { Component } from 'react';
import logo from '../assets/logo.svg';
import alert from '../assets/alert.png';
import '../css/exam.css';
import Popup from "reactjs-popup";

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faPencilAlt } from '@fortawesome/free-solid-svg-icons'
class Exam extends Component{
    constructor(props) {
        super()
        this.state = {
            title1: props.text,
            problem: props.problem,
            exid: props.scanCode,
            newName:"",
            visible:true
        }
        console.log(props.problem)

        this.navResults = this.navResults.bind(this);
        this.navAnswerKey = this.navAnswerKey.bind(this);
        this.handleName = this.handleName.bind(this);
        this.suppressEnter = this.suppressEnter.bind(this);
        this.resetNewNames = this.resetNewNames.bind(this);
    }




    navResults(){
        const his = this.props.history
        // createBrowserHistory().push("/results");
        this.props.history.push({pathname:"/results",state:{id:this.state.exid, text:this.state.title1 ,loginName:this.props.loginName,exams:this.props.list,email:this.props.email}});

        fetch(`http://pi.cs.oswego.edu:13126/result?examID=${this.state.exid}&instID=${this.props.email}`, {
            method: 'post'
        })
    }

    navAnswerKey(){
      this.props.history.push({pathname:"/answerKey",state:{id:this.state.exid,text:this.state.title1, loginName:this.props.loginName,exams:this.props.list,email:this.props.email}});
    }
    handleName(event) {
        if (event.target.value.length < 100) {
            this.setState({ newName: event.target.value });

        }
    }
    suppressEnter(event){
        if (event.which === 13) {
            event.preventDefault();
        }
    }
    resetNewNames(){
        this.state.newName = ""
        this.setState(this.state)
    }
    render() {
        return(
            <div>{this.state.visible ?
                <div className={"exam"}>
                    <Popup modal trigger={<a className={"closeButton2"}> &times; </a>}>
                        {close =>
                        <div className={"changeName"}>
                            <h1> Do you wish to delete test {this.state.title1}?</h1>
                            <h2>This will PERMANENTLY erase this test and its results from your account </h2>
                            <h2>This action cannot be undone</h2>
                            <button onClick={()=>{
                                fetch(`http://pi.cs.oswego.edu:13126/namechange?id=${this.state.exid}`,{
                                    method: 'delete'
                                })
                                this.setState({visible:false})
                            }} style={{background: '#aa0000',color:'white'}}>Delete</button>
                            <button onClick={close}>Cancel</button>


                        </div>
                        }

                    </Popup>
                    <a className={"editName"}>{this.state.title1} </a>
                    <Popup onClose={this.resetNewNames} modal trigger={<FontAwesomeIcon className={"edit"} icon={faPencilAlt}/>}>
                        {close =>
                            <div className={"changeName"}>
                                <h1>Change name of test {this.state.title1}</h1>
                                <form onKeyPress={this.suppressEnter}>
                                    <label> Enter new name of test: <input name={"newName"} value={this.state.newName}
                                                                           onChange={this.handleName}/></label> <br/>
                                </form>
                                <button onClick={()=>{
                                    fetch(`http://pi.cs.oswego.edu:13126/namechange?id=${this.state.exid}&name=${this.state.newName}`,{
                                        method: 'post'
                                    })
                                    this.state.title1 = this.state.newName
                                    this.resetNewNames()
                                    close()
                                }
                                }>Submit</button>


                                <button onClick={()=>{
                                    this.resetNewNames()
                                    close()

                                }
                                }>
                                Cancel</button>

                            </div>

                        }

                    </Popup>


                    <>    Scan Code: {this.props.scanCode}</>
                    <button onClick={this.navResults}>View Results</button>
                    <button onClick={this.navAnswerKey}>Edit Answer Key</button>
                    {this.props.problem ? <img className={"alert"} src={alert} height={25} /> : null}

                </div>: null}
            </div>

        )
    }


}
export default Exam;
