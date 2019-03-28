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
        width: '25%',
        marginTop: theme.spacing.unit * 3,
        overflowX: 'auto',
    },
    table: {
        minWidth: 100,
    },
});

let id = 0;
function createData(response, percent) {
    id += 1;
    return { response, percent};
}

const rows = [
    createData('A', '10'),
    createData('B', '20'),
    createData('C', '5'),
    createData('D', '2'),
    createData('E', '1'),
];

function QuestionTable(props) {
    const { classes } = props;

    return (
        <Paper className={classes.root}>
            <Table className={classes.table}>
                <TableHead>
                    <TableRow>
                        <TableCell>Question 1:</TableCell>
                    </TableRow>
                    <TableRow>
                        <TableCell >Response</TableCell>
                        <TableCell align="center">Percent</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {rows.map(row => (
                        <TableRow key={row.id}>
                            <TableCell component="th" scope="row">
                                {row.response}
                            </TableCell>
                            <TableCell align="center">{row.percent}</TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </Paper>
    );
}

QuestionTable.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(QuestionTable);
