import React, { Component } from 'react';
import '../../css/App.css';
import'../../css/styles.css';
import {Link} from "react-router-dom";
import Tabs from './Tabs';
import Center from 'react-center';
import Header from "../Header";

class Results extends Component {
    constructor(props){
        super()
    }

    render() {
        return (
            <div>

                <h1 style={{ color: '#00ccbc', paddingLeft: '50px' }}>Results</h1>
                <Link to={{pathname: '/home',state:{loginName:this.props.location.state.loginName,email:this.props.location.state.email}}} style={{ textDecoration: 'none', color: '#003C60'}}>&lt;Back to Dashboard</Link>

                <p style={{ color: '#00ccbc', paddingLeft: '50px' }}>{this.props.location.state.text}</p>
                <Center><Tabs /></Center>
                <Header history={this.props.history} email={this.props.location.state.email}/>

            </div>

        );
    }
}

const container = document.createElement('div');
document.body.appendChild(container);

export default Results;

/*
* */