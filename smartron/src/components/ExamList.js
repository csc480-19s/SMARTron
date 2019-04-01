import React, { Component } from 'react';
import Exam from "./Exam";

class ExamList extends Component{
    constructor(props) {
        super();
        this.state ={
            list:props.exams,
            loginName:props.loginName,
            email:props.email

        }

    }
    render() {


        return(
            <>{this.props.children}</>

        )
    }

}
export default ExamList;
/*
*       const children =React.children.map(this.props.children,child =>{
            return React.cloneElement(child,{
                list:this.state.list,
                loginName:this.state.loginName,
                email:this.state.email
            });
        });
* */