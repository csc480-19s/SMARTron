import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import SwipeableViews from 'react-swipeable-views';
import AppBar from '@material-ui/core/AppBar';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import Typography from '@material-ui/core/Typography';
import StudentTable from './StudentTable';
import Center from 'react-center';
import QuestionTable from "./QuestionTable";
import StatsTable from './StatsTable';

function TabContainer({ children, dir }) {
    return (
        <Typography component="div" dir={dir} style={{ padding: 8 * 3 }}>
            {children}
        </Typography>
    );
}

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
                    </TabContainer>

                    <TabContainer dir={theme.direction}>
                        <Center><QuestionTable /></Center>
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
