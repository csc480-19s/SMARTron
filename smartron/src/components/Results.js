import React, { Component } from 'react';
import { PieChart, Pie } from 'recharts';
import Tabs from './Tabs';

require('../css/styles.css');

const data01 = [
    {
        "name": "Group A",
        "value": 400
    },
    {
        "name": "Group B",
        "value": 300
    },
    {
        "name": "Group C",
        "value": 300
    },
    {
        "name": "Group D",
        "value": 200
    }
];
const data02 = [
    {
        "name": "Group A",
        "value": 2400
    },
    {
        "name": "Group B",
        "value": 4567
    },
    {
        "name": "Group C",
        "value": 1398
    },
    {
        "name": "Group D",
        "value": 9800
    }
];

class Results extends Component {

    render() {
        return (
            <div>
                <div style={{ backgroundColor: 'Gray' }}><h1 style={{ paddingLeft: '50px' }}>SMARTron</h1></div>
                <a style={{ paddingLeft: '50px' }}>Back to Dashboard</a><h1 style={{ color: '#00ccbc' }}>Results</h1>
                <h3 style={{ color: '#00ccbc' }}>Final - 11250 05152018 13:06</h3>
                <Tabs>
                    <div label="Statistics Report">
                        Mean, Median and Mode information
                    </div>
                    <div label="By Question">
                        <PieChart width={730} height={250}>
                            <Pie data={data01} dataKey="value" nameKey="name" cx="50%" cy="50%" outerRadius={50} fill="#8884d8" />
                            <Pie data={data02} dataKey="value" nameKey="name" cx="50%" cy="50%" innerRadius={60} outerRadius={80} fill="#82ca9d" label />
                        </PieChart>
                        <PieChart width={730} height={250}>
                            <Pie data={data01} dataKey="value" nameKey="name" cx="50%" cy="50%" outerRadius={50} fill="#F08080" />
                            <Pie data={data02} dataKey="value" nameKey="name" cx="50%" cy="50%" innerRadius={60} outerRadius={80} fill="#20B2AA" label />
                        </PieChart>
                        <PieChart width={730} height={250}>
                            <Pie data={data01} dataKey="value" nameKey="name" cx="50%" cy="50%" outerRadius={50} fill="#F4A460" />
                            <Pie data={data02} dataKey="value" nameKey="name" cx="50%" cy="50%" innerRadius={60} outerRadius={80} fill="#EE82EE" label />
                        </PieChart>
                    </div>
                    <div label="By Student">
                        Tabular information
                    </div>
                </Tabs>
            </div>
        );
    }
}

const container = document.createElement('div');
document.body.appendChild(container);

export default Results;