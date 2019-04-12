import React, {Component} from 'react';
import logo from '../assets/logo.svg';
import '../css/App.css';

class Header extends Component{
    constructor(props) {
        super(props);

    }

    render(){
    return(
      <header className={"topBar"}>
      <h1 className={"leftA"}>SMART</h1><h1 className={"leftB"}>RON</h1>
      <h1 className={"right"}>{this.props.email} </h1>
      <img className = "logout" onClickclassName={"logout"} src={logo} height={40} />
      </header>
    );
  }
}
export default Header;
