import React, { Component } from 'react';
import '../../css/App.css';
import'../../css/styles.css';
//import {Link} from "react-router-dom";
import Tabs from './Tabs';
import Center from 'react-center';
import Header from "../Header";
import { createBrowserHistory } from 'history';

class Results extends Component {

    render() {
        return (
            <div>
                <Header history={createBrowserHistory()} email={this.props.location.state.email}/>
                <h1 style={{ color: '#00ccbc', paddingLeft: '50px' }}>Results</h1>
                <p style={{ color: '#00ccbc', paddingLeft: '50px' }}>{this.props.location.state.text}</p>
                <Center><Tabs /></Center>
            </div>

        );
    }
}

const container = document.createElement('div');
document.body.appendChild(container);

export default Results;

/*
        <Link to={'/home'} style={{ textDecoration: 'none', color: '#003C60'}}>&lt;Back to Dashboard</Link>
* */