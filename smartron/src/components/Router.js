import React, { Component } from "react";
import { HashRouter, Route } from "react-router-dom";
import Home from './Home';
import Results from "./Results";

class Router extends Component {
    render() {
        return (
            <HashRouter>
                <div style={{ height: "100%" }}>
                    <Route exact path="/" component={Home} />
                    <Route exact path="/results" component={Results} />
                </div>
            </HashRouter>
        )
    }
}

export default Router;