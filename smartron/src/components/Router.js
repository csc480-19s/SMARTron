import React, { Component } from "react";
import { BrowserRouter, Route } from "react-router-dom";
import Home from './Home';
import Results from "./ResultsPage/Results";
import Login from "./Login";
import AnswerKey from "./AnswerKey";

class Router extends Component {
    render() {
        return (
            <BrowserRouter basename={process.env.PUBLIC_URL}>
                <div style={{ height: "100%" }}>
                    <Route exact path="/" component={Login} />
                    <Route path="/home" component={Home} />
                    <Route exact path="/results" component={Results} />
                    <Route exact path="/answerKey" component={AnswerKey}/>
                </div>
            </BrowserRouter>
        )
    }
}

export default Router;
