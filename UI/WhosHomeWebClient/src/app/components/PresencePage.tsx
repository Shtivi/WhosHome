import * as React from "react";
import { withStyles, createStyles, WithStyles, Theme } from '@material-ui/core/styles';
import { connect } from 'react-redux'
import { Dispatch, Action } from 'redux'
import { PresenceState } from "../store/Store";
import { fetchAllPrecenseData } from "../actions/PrecenseActionCreators";

const styles = (theme: Theme) => createStyles({

});

interface PresencePageProps extends WithStyles<typeof styles> {
    dispatch: Dispatch<Action>,
    presenceState: PresenceState
}

class PresencePage extends React.Component<PresencePageProps> {
    public constructor(props: PresencePageProps) {
        super(props);
        this.props.dispatch(fetchAllPrecenseData())
    }

    render() {
        return (
            <div></div>
        );
    }
}

const mapStateToProps = (state: any) => ({
    presenceState: state.presence
})

const mapDispatchToProps = (dispatch: Dispatch<Action<any>>) => ({
    dispatch: dispatch
})

export default withStyles(styles)(connect(mapStateToProps, mapDispatchToProps)(PresencePage));