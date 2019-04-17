import React, {Component} from 'react';
import logo from '../assets/logo.svg';
import '../css/App.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faPowerOff } from '@fortawesome/free-solid-svg-icons'
class Header extends Component{
    constructor(props) {
        super(props);

    }

    render(){
    return(
      <header className={"topBar"}>
      <h1 className={"leftA"}>SMART</h1><h1 className={"leftB"}>RON</h1>
      <h1 className={"right"}>{this.props.email} </h1>

      <button className={"logout"} onClick={()=>{this.props.history.push("/")}}><FontAwesomeIcon icon={faPowerOff} size={"lg"}/></button>
      </header>
    );
  }
}
export default Header;
