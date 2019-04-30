import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';

const styles = theme => ({
    root: {
        width: '80%',
        marginTop: theme.spacing.unit * 3,
        overflowX: 'auto',
    },
    table: {
        minWidth: 500,
    },
});

let id = 0;
function createData(mean, median, max, min, range, deviation, variance, kr20, kr21, cronbach) {
    id += 1;
    return { mean, median, max, min, range, deviation, variance, kr20, kr21, cronbach };
}

const rows = [];

// statsJSON.gradeDistribution.forEach((stat) => {
//     data.push(createData(stat.grade, stat.percent));
// });

fetch('http://pi.cs.oswego.edu:13126/statistics')
    .then((res) => res.json())
    .then((responseJson) => {
        rows.push(createData(responseJson.mean, responseJson.median, responseJson.max, responseJson.min, responseJson.range, 
            responseJson.deviation, responseJson.variance, responseJson.kr20, responseJson.kr21, responseJson.cronbach));
    })
    .catch((error) => {
        console.error(error);
    });

function StatsTable(props) {

    const { classes } = props;

    return (
        <Paper className={classes.root}>
            <Table className={classes.table}>
                <TableHead>
                    <TableRow>
                        <TableCell align="center">Mean</TableCell>
                        <TableCell align="center">Median</TableCell>
                        <TableCell align="center">Max</TableCell>
                        <TableCell align="center">Min</TableCell>
                        <TableCell align="center">Range</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {rows.map(row => (
                        <TableRow key={row.id}>
                            <TableCell component="th" scope="row">
                                {row.mean}
                            </TableCell>
                            <TableCell align="center">{row.median}</TableCell>
                            <TableCell align="center">{row.max}</TableCell>
                            <TableCell align="center">{row.min}</TableCell>
                            <TableCell align="center">{row.range}</TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
            <Table className={classes.table}>
                <TableHead>
                    <TableRow>
                        <TableCell align="center">Standard Deviation</TableCell>
                        <TableCell align="center">Variance</TableCell>
                        <TableCell align="center">Kuder-Richardson-20</TableCell>
                        <TableCell align="center">Kuder-Richardson-21</TableCell>
                        <TableCell align="center">Cronbach</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {rows.map(row => (
                        <TableRow key={row.id}>
                            <TableCell component="th" scope="row">
                                {row.deviation}
                            </TableCell>
                            <TableCell align="center">{row.variance}</TableCell>
                            <TableCell align="center">{row.kr20}</TableCell>
                            <TableCell align="center">{row.kr21}</TableCell>
                            <TableCell align="center">{row.cronbach}</TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </Paper>
    );
}

StatsTable.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(StatsTable);