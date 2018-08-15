import * as React from 'react';
import { render } from 'react-dom'
import { Hello } from './components/Hello';
import { createStore } from '../../node_modules/redux';
import rootReducer from './reducers/rootReducer';
import { Provider } from 'react-redux';
declare let module: any

const store = createStore(rootReducer);

render(
    <Provider store={store}>
        <Hello />
    </Provider>,
    document.getElementById('root')
)

export default store