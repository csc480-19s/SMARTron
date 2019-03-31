import React, { Component } from 'react';
import logo from '../assets/smartron-logo.png';
import '../css/App.css';
import {GoogleLogin} from "react-google-login";

class Login extends Component {
    constructor(props) {
        super(props);

    }

    responseGoogleSucc = (response) => {
        console.log(response.getBasicProfile().getEmail())
        this.props.history.push({pathname:"/home", state:{loginName:response.getBasicProfile().getName(), email:response.getBasicProfile().getEmail(),exams:[]}});
    }

    responseGoogleFail = (response) => {
        console.log("Error Signing in")
    }

    render() {
        return (
            <div className={"loginButton"}>
                <img src={logo} height={400}/>
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
