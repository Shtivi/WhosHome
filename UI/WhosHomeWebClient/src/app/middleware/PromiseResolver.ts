import { Action, Store, Dispatch, AnyAction } from "redux";
import { ActionTypes, AsyncStatus } from "../actions/ActionTypes";
import { Promise } from "es6-promise";
import { DeferredAction, DeferredActionBuilder } from "../actions/DeferredAction";

const PromiseResolver = (store: Store) => (next: Dispatch<AnyAction>) => (action: DeferredAction<any>) => {
    const {dispatch} = store;
    if (action.promise && action.asyncStatus == AsyncStatus.PENDING) {
        action.promise
            .then(data => dispatch(createSucessAction(action, data)))
            .catch(error => dispatch(createFailureAction(action, error)));;
    }
    
    next(action);
}

const createSucessAction = <T> (originalAction: DeferredAction<T>, data: T): DeferredAction<T> => {
    return DeferredActionBuilder
        .from<T>(originalAction)
        .withAsyncStatus(AsyncStatus.COMPLETED)
        .withPayload(data)
        .build();
}

const createFailureAction = <T> (originalAction: DeferredAction<T>, error: Error): DeferredAction<T> => {
    return DeferredActionBuilder
        .from<T>(originalAction)
        .withAsyncStatus(AsyncStatus.ERROR)
        .withError(error)
        .build();
}

export default PromiseResolver;