import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import statsJSON from '../../JSON/Statistics';

const styles = theme => ({
    root: {
        width: '50%',
        marginTop: theme.spacing.unit * 3,
        overflowX: 'auto',
    },
    table: {
        minWidth: 100,
    },
});

let id = 0;
function createData(grade, percent) {
    id += 1;
    return { grade, percent };
}

const data = [];

statsJSON.gradeDistribution.forEach((stat) => {
    data.push(createData(stat.grade, stat.percent));
});

function StatsTable(props) {

    const { classes } = props;

    return (
        <Paper className={classes.root}>
            <Table className={classes.table}>
                <TableHead>
                    <TableRow>
                        <TableCell align="center">Grade</TableCell>
                        <TableCell align="center">Percent Score</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {data.map(row => (
                        <TableRow key={row.id}>
                            <TableCell component="th" scope="row">
                                {row.grade}
                            </TableCell>
                            <TableCell align="center">{row.percent}</TableCell>
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