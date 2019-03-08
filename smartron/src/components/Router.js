import React, { Component } from "react";
import { BrowserRouter, Route } from "react-router-dom";
import Home from './Home';
import Results from "./Results";
import Login from "./Login";

class Router extends Component {
    render() {
        return (
            <BrowserRouter>
                <div style={{ height: "100%" }}>
                    <Route exact path="/" component={Login} />
                    <Route path="/home" component={Home} />
                    <Route exact path="/results" component={Results} />
                </div>
            </BrowserRouter>
        )
    }
}

export default Router;