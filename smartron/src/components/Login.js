import React, { Component } from 'react';
import logo from '../assets/logo.svg';
import '../css/App.css';
import {GoogleLogin} from "react-google-login";

class Login extends Component {

    responseGoogleSucc = (response) => {
        console.log(response.getBasicProfile().getEmail())
        this.props.history.push("/home");
    }

    responseGoogleFail = (response) => {
        console.log("Error Signing in")
    }

    render() {
        return (
            <div className={"loginButton"}>
                <img src={logo}/>
                <p>Welcome to SMARTron, please login with your LakerNet account</p>
                <GoogleLogin
                    clientId="121480712018-0f6fb2fh7kbms55bno97g3hiju99n8oo.apps.googleusercontent.com"
                    buttonText="Login with your LakerNet ID"
                    onFailure={this.responseGoogleFail}
                    onSuccess={this.responseGoogleSucc}
                    className={"button"}
                />
            </div>
        );
    }
}

export default Login;
