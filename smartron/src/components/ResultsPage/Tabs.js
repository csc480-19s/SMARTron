import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import SwipeableViews from 'react-swipeable-views';
import AppBar from '@material-ui/core/AppBar';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import Typography from '@material-ui/core/Typography';
import { Cell, BarChart, Bar, CartesianGrid, XAxis, YAxis, Tooltip, Legend } from "recharts";
import StudentTable from './StudentTable';
import Center from 'react-center';
import QuestionTable from "./QuestionTable";
import statsJSON from '../../JSON/Statistics';
import StatsTable from './StatsTable';

function TabContainer({ children, dir }) {
    return (
        <Typography component="div" dir={dir} style={{ padding: 8 * 3 }}>
            {children}
        </Typography>
    );
}

const data = [
    { "name": "A", "value": 10 },
    { "name": "B", "value": 20 },
    { "name": "C", "value": 5 },
    { "name": "D", "value": 2 },
    { "name": "E", "value": 1 }
];

let id = 0;
function createData(grade, percent) {
    id += 1;
    return { grade, percent };
}

const data1 = [];

statsJSON.gradeDistribution.forEach((stat) => {
    data1.push(createData(stat.grade, stat.percent));
});

TabContainer.propTypes = {
    children: PropTypes.node.isRequired,
    dir: PropTypes.string.isRequired,
};

const styles = theme => ({
    root: {
        backgroundColor: '#fff',
        width: 1000,
    },
});

const COLORS = ['#0088FE', '#00C49F', '#FFBB28', '#FF8042', '#E6E6FA'];

class CenteredTabs extends React.Component {
    state = {
        value: 0,
    };

    handleChange = (event, value) => {
        this.setState({ value });
    };

    render() {
        const { classes, theme } = this.props;

        return (
            <div className={classes.root}>
                <AppBar position="static" color="#fff">
                    <Tabs
                        value={this.state.value}
                        onChange={this.handleChange}
                        indicatorColor="primary"
                        textColor="primary"
                        variant="fullWidth"
                    >
                        <Tab label="Statistics Report" />
                        <Tab label="By Question" />
                        <Tab label="By Student" />
                    </Tabs>
                </AppBar>
                <SwipeableViews
                    axis={theme.direction === 'rtl' ? 'x-reverse' : 'x'}
                    index={this.state.value}
                    onChangeIndex={this.handleChange}
                >
                    <TabContainer dir={theme.direction}>
                        <Center><StatsTable /></Center>
                        <p> </p>
                        <Center>
                            <BarChart width={700} height={200} data={data1}>
                                <CartesianGrid strokeDasharray="3 3" />
                                <XAxis dataKey="grade" />
                                <YAxis />
                                <Tooltip />
                                <Legend />
                                <Bar data={data1} dataKey="grade" dataKey="percent" fill="#000000">
                                    {
                                        data.map((entry, index) => <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />)
                                    }
                                </Bar>
                            </BarChart>
                        </Center>
                    </TabContainer>
                    <TabContainer dir={theme.direction}>
                        <Center>
                            <QuestionTable />
                            <BarChart width={400} height={200} data={data1}>
                                <CartesianGrid strokeDasharray="3 3" />
                                <XAxis dataKey="grade" />
                                <YAxis />
                                <Tooltip />
                                <Legend />
                                <Bar data={data1} dataKey="grade" dataKey="percent" fill="#000000">
                                    {
                                        data.map((entry, index) => <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />)
                                    }
                                </Bar>
                            </BarChart>
                        </Center>
                    </TabContainer>
                    <TabContainer dir={theme.direction}>
                        <Center><StudentTable /></Center>
                    </TabContainer>
                </SwipeableViews>
            </div>
        );
    }
}

CenteredTabs.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles, { withTheme: true })(CenteredTabs);
