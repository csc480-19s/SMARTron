import React, { Component } from 'react';
import Header from "./Header";
import Center from 'react-center';
import { Link } from "react-router-dom";
import '../css/App.css';

class About extends Component {
    render() {
        return (
            <div>
                <Header history={this.props.history} email={this.props.location.state.email} />
                <Center>
                    <div>
                        <h1> SMARTron Developers </h1>
                        <div>
                            <h2> Requirements </h2>
                            <p> Kim Fischer- Team Lead <br />
                                Stephen Iskander <br />
                                Zak Gudlin<br />
                                Josh Reiss<br />
                                Ryan Collins<br />
                            </p>
                        </div>
                        <div>
                            <h2> Engine </h2>
                            <p> Khairunisa Sharif- Team Lead<br />
                                John Carlo- Team Lead<br />
                                Robert Sgroi<br />
                                Matthew Fernandez<br />
                                Vincent Dinh<br />
                                Zach Reiss<br />
                                William Pierce<br />
                            </p>
                        </div>
                        <div>
                            <h2> Quality and Assurance </h2>
                            <p> Rachel Hernon- Team Lead<br />
                                Colleen McClure- Team Lead<br />
                                Jondn Tryniski<br />
                                Quinn Riley<br />
                                Sean McGrath<br />
                                Tim Gass<br />
                            </p>
                        </div>
                        <div>
                            <h2> Database </h2>
                            <p> Anne Reynolds- Team Lead<br />
                                Nate OLeary<br />
                                Robert Kilmer<br />
                                Sergio Valoy<br />
                            </p>
                        </div>
                        <div>
                            <h2> GUI </h2>
                            <p> Kristen Ray- Team Lead<br />
                                Sushmita Banjaree<br />
                                Nicholas Esposito <br />
                                Narayan Neopane<br />
                            </p>
                        </div>
                        <div>
                            <h2> Usability </h2>
                            <p> Khushboo Panchal- Team Lead<br />
                                Kristen Ray<br />
                                Kim Fischer<br />
                                Colleen McClure<br />
                                Rachel Hernon<br />
                                Khairunisa Sharif<br />
                                Anne Reynolds <br />
                                John Carlo<br />
                            </p>
                        </div>
                    </div>
                    </Center>
                    
                    <Center>

                    <Link className="credit" to={{
                        pathname: '/home', state: {
                            loginName: this.props.location.state.loginName,
                            email: this.props.location.state.email
                        }
                    }}>&lt;Back to Dashboard</Link>

                </Center>
            </div>
        )
    }
}

export default About;