import React, { Component } from 'react';
import '../css/App.css';
import Router from '../components/Router'

class App extends Component {
    constructor(props) {
        super(props);
        this.state={
            loginName:"hey"
        }
    }


    render() {
        return (
            <Router/>
        );
    }
}

export default App;