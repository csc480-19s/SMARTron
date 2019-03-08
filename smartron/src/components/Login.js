import React, { Component } from 'react';
import logo from '../assets/logo.svg';
import '../css/App.css';
import {GoogleLogin} from "react-google-login";

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

    //TO-DO render home once login is satisfied
)
class Login extends Component {

    render() {
        return (
            loginButton()
        );
    }
}

export default Login;
