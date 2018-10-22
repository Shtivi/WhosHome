import { DeferredAction, DeferredActionBuilder } from "./DeferredAction";
import PersonPresenceData from "../models/PersonPresenceData";
import { ActionTypes } from "./ActionTypes";
import PresenceDataAgent from "../services/PresenceDataAgent";

export const fetchAllPrecenseData = (): DeferredAction<PersonPresenceData[]> => {
    return DeferredActionBuilder
        .of<PersonPresenceData[]>(ActionTypes.FETCH_PRESENCE_DATA)
        .withPromise(PresenceDataAgent.fetchAllPeoplePresenceData())
        .build();
}