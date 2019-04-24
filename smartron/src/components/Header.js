import React, {Component} from 'react';
import logo from '../assets/logo.svg';
import '../css/App.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faPowerOff } from '@fortawesome/free-solid-svg-icons'
import { GoogleLogout } from 'react-google-login';


class Header extends Component{
    constructor(props) {
        super();

    }

    render(){
    return(
      <header className={"topBar"}>
          <h1 className={"leftA"}>SMART</h1><h1 className={"leftB"}>RON</h1>
          <GoogleLogout clientId={"121480712018-0f6fb2fh7kbms55bno97g3hiju99n8oo.apps.googleusercontent.com"} buttonText={"Logout"} onLogoutSuccess={()=>{this.props.history.push("/")}} render={ renderProps =>(<button className={"logout"} onClick={()=>{this.props.history.push("/")}}><FontAwesomeIcon icon={faPowerOff} size={"lg"}/></button>)}/>
          <h1 className={"right"}>{this.props.email} </h1>

      </header>
    );
  }
}
export default Header;
