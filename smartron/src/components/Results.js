import React, { Component } from 'react';
import '../css/App.css';
import'../css/styles.css';
import {Link} from "react-router-dom";
import Tabs from './Tabs';
import Center from 'react-center';

class Results extends Component {

    render() {
        return (
            <div>
                <div style={{background: '#f2f2f2'}}><h1 style={{ paddingLeft: '50px',color: '#003C60', fontFamily:"Helvetica Neue"}}>SMART<span style={{color:'#00CCBC', fontFamily:"Helvetica Neue"}}>RON</span></h1></div>
                <Link to={'/home'} style={{ textDecoration: 'none', color: '#003C60'}}>&lt;Back to Dashboard</Link><h1 style={{ color: '#00ccbc', paddingLeft: '50px' }}>Results</h1>
                <p style={{ color: '#00ccbc', paddingLeft: '50px' }}>Final - 11250 05152018 13:06</p>
                <Center><Tabs /></Center>
            </div>

        );
    }
}

const container = document.createElement('div');
document.body.appendChild(container);

export default Results;