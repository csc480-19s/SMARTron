import React, { Component } from 'react';
import Exam from "./Exam";

class ExamList extends Component{
    constructor(props) {
        super(props);
        this.state ={
            list:props.exams
        }

    }
    render() {

        return(
            this.state.list
        )
    }

}
export default ExamList;