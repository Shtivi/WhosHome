import * as React from "react";
import '../../css/circular-menu.css';
import NotificationsOff from '@material-ui/icons/NotificationsOff';
import Menu from '@material-ui/icons/Menu';
import SvgIcon from '@material-ui/core/SvgIcon';
import IconButton from '@material-ui/core/IconButton';
import Button from '@material-ui/core/Button';
import { withStyles, createStyles, WithStyles } from '@material-ui/core/styles';
import grey from '@material-ui/core/colors/grey';
import cyan from '@material-ui/core/colors/cyan';


const styles = createStyles({
    mainButton: {
        bottom: '-3em',
        position: 'fixed',
        right: '37%',
        zIndex: 11,
        width: '7em',
        height: '7em',
        paddingBottom: '2em',
        backgroundColor: grey[900],
        color: cyan[500]
    },
    mainButtonActive: {
        backgroundColor: grey[100],
        color: grey[900]
    }
});

interface CiruclarMenuProps extends WithStyles<typeof styles> {
}

interface CiruclarMenuState {
    displayed: boolean,
    hover: boolean
}

class circularMenu extends React.Component<CiruclarMenuProps, CiruclarMenuState> {
    private navDisplayed: boolean;

    public constructor(props: CiruclarMenuProps) {
        super(props);
        this.state = {displayed: false, hover:false}
    }

    private toggle = (e: any) => {
        e.stopPropagation();
        if (!this.state.displayed) this.show();
        else this.hide();
    }

    private show = () => this.setState({displayed: true});
    private hide = () => this.setState({displayed:false});
    private hoverOn = () => this.setState({hover:true});
    private hoverOff = () => this.setState({hover:false});

    private wrapperClick = (e: any) => {
        e.stopPropagation();
    }

    render() {
        let {displayed, hover} = this.state;
        let {classes} = this.props;
        return (
            <div className='csstransforms'>
                <Button onClick={this.toggle} variant="fab" 
                        className={classes.mainButton + ' ' + (displayed ? classes.mainButtonActive : '') +
                                   (hover ? classes.mainButton : '')}>
                    <Menu></Menu>
                </Button>
                <div className={"cn-wrapper" + (displayed ? ' opened-nav' : '')} onClick={this.wrapperClick}>
                    <ul>
                        <li><a href="#"><span>
                            <IconButton><SvgIcon>
                                <NotificationsOff></NotificationsOff>
                            </SvgIcon></IconButton>
                        </span></a></li>
                        <li><a href="#"><span>                            <IconButton><SvgIcon>
                                <NotificationsOff></NotificationsOff>
                            </SvgIcon></IconButton></span></a></li>
                        <li><a href="#"><span>                            <IconButton><SvgIcon>
                                <NotificationsOff></NotificationsOff>
                            </SvgIcon></IconButton></span></a></li>
                    </ul>
                </div>
                <div id="cn-overlay" className={"cn-overlay" + (displayed ? ' on-overlay' : '')} onClick={this.hide}></div>
            </div>
        )
    }
}

export default withStyles(styles)(circularMenu);