import React, { Component } from 'react';
import Exam from "./Exam";

class ExamList extends Component{
    constructor(props) {
        super(props);

    }
    render() {
        return(
            <>
            <Exam text={"Exam 1"} history={this.props.history}/>
            <Exam text={"Exam 2"} history={this.props.history}/>
            <Exam text={"Exam 3"} history={this.props.history}/>
            </>
        )
    }

}
export default ExamList;