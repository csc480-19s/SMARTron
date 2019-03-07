import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import {GoogleLogin} from "react-google-login";
import {
    BrowserRouter as Router,
    Link,
    Route,
    Switch,
} from 'react-router-dom';
import Home from './Home.js';

const responseGoogleSucc = (response) => {
    console.log(response.getBasicProfile().getEmail())


}
const responseGoogleFail = (response) => {
    console.log("Error Signing in")
}
const loginButton = () =>(
    <div className={"loginButton"}>
    <img src={logo}/>
    <p>Welcome to SMARTron, please login with your LakerNet account</p>
    <GoogleLogin
        clientId="121480712018-0f6fb2fh7kbms55bno97g3hiju99n8oo.apps.googleusercontent.com"
        buttonText="Login with your LakerNet ID"
        onFailure={responseGoogleFail}
        onSuccess={responseGoogleSucc}
        className={"button"}
    />
    </div>
)
class App extends Component {

  render() {
    return (
        loginButton()
  );
  }
}

export default App;
