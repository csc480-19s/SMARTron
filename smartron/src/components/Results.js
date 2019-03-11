import React, { Component } from 'react';
import { PieChart, Pie, Sector, Cell } from 'recharts';
import Tabs from './Tabs';

require('../css/styles.css');

const COLORS = ['#0088FE', '#00C49F', '#FFBB28', '#FF8042'];

const data = [
    {"name": "Group A", "value": 10},
    {"name": "Group B", "value": 20},
    {"name": "Group C", "value": 5},
    {"name": "Group D", "value": 2}
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
                            <Pie data={data} dataKey="value" nameKey="name" cx="50%" cy="50%" fill="#82ca9d" label>
                                {
                                    data.map((entry, index) => <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />)
                                }
                            </Pie>
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