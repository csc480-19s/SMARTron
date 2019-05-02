import React, { Component } from 'react';
import '../../css/App.css';
import '../../css/styles.css';
import { Link } from "react-router-dom";
import Tabs from './Tabs';
import Center from 'react-center';
import Header from "../Header";
import LoadingScreen from 'react-loading-screen';

class Results extends Component {
    constructor(props) {
        super(props)

        this.state = {
            loading: true
        }
    }

    componentDidMount() {
        // fake promise
        setTimeout(() =>
            this.setState({ loading: false })
            , 10000)
    }

    render() {
        const { loading } = this.state

        return (
            <div>

                <LoadingScreen
                    loading={loading}
                    bgColor='#f1f1f1'
                    spinnerColor='#9ee5f8'
                    textColor='#676767'
                    text='Loading...'>

                    <h1 style={{ color: '#00ccbc', paddingLeft: '50px' }}>Results</h1>
                    <Link to={{
                        pathname: '/home', state: {
                            loginName: this.props.location.state.loginName,
                            email: this.props.location.state.email
                        }
                    }} style={{ textDecoration: 'none', color: '#003C60' }}>&lt;Back to Dashboard</Link>

                    <p style={{ color: '#00ccbc', paddingLeft: '50px' }}>{this.props.location.state.text}</p>

                    <Header history={this.props.history} email={this.props.location.state.email} />

                    <Center><Tabs /></Center>

                </LoadingScreen>

            </div>

        );
    }
}

const container = document.createElement('div');
document.body.appendChild(container);

export default Results;
