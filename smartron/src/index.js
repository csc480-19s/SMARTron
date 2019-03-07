import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import routes from './routes'
import {
    BrowserRouter as Router,
    Link,
    Route,
    Switch,
} from 'react-router-dom';
import * as serviceWorker from './serviceWorker';
import Home from "./Home";


ReactDOM.render(<Home/>, document.getElementById('root'));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: http://bit.ly/CRA-PWA
serviceWorker.unregister();
