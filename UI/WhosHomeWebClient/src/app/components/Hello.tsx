import * as React from 'react';
import Button from '@material-ui/core/Button';


export class Hello extends React.Component<{}, {}> {
   render() {
    return(
        <Button variant="contained" color="primary">
            Hello World
        </Button>
    );
   }
}