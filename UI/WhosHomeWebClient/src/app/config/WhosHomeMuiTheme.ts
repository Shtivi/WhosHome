import { createMuiTheme } from '@material-ui/core/styles';
import cyan from '@material-ui/core/colors/cyan';
import blue from '@material-ui/core/colors/blue';

const whosHomeMuiTheme = createMuiTheme({
    palette: {
        primary: {
            // main: ​​'#00d2a8'
            main: '#a9bfe1'
        },
        secondary: {
            main: '#7491d0'
        }
    },
    typography: {
        fontFamily: 'Hiragino'
    }
});

export default whosHomeMuiTheme;