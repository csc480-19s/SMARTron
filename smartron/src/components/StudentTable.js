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
function createData(student, grade, total_points, percent) {
    id += 1;
    return { student, grade, total_points, percent };
}

const rows = [
    createData('Sushmita Banerjee', 'A', 98, 98),
    createData('Nicholas Esposito', 'A', 97, 97),
    createData('Narayan Neopane', 'A', 96, 96),
    createData('Kristen Ray', 'A', 95, 95),
];

function StudentTable(props) {
    const { classes } = props;

    return (
        <Paper className={classes.root}>
            <Table className={classes.table}>
                <TableHead>
                    <TableRow>
                        <TableCell >Student</TableCell>
                        <TableCell align="center">Grade</TableCell>
                        <TableCell align="center">Total Points</TableCell>
                        <TableCell align="center">Percent %</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {rows.map(row => (
                        <TableRow key={row.id}>
                            <TableCell component="th" scope="row">
                                {row.student}
                            </TableCell>
                            <TableCell align="center">{row.grade}</TableCell>
                            <TableCell align="center">{row.total_points}</TableCell>
                            <TableCell align="center">{row.percent}</TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </Paper>
    );
}

StudentTable.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(StudentTable);
