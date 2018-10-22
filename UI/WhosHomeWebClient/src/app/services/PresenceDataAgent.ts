import PersonPresenceData from "../models/PersonPresenceData";
import {Promise} from 'es6-promise'
import axios from "axios";
import config from "../config/config";

export interface IPresenceDataAgent {
    fetchAllPeoplePresenceData(): Promise<PersonPresenceData[]>
}

export class PresenceDataAgent implements IPresenceDataAgent {
    public constructor(private presenceApiConfig: any) {}

    public fetchAllPeoplePresenceData(): Promise<PersonPresenceData[]> {
        return new Promise<PersonPresenceData[]>((resolve, reject) => {
            axios.get(this.presenceApiConfig.endpoint + this.presenceApiConfig.api.fetchAllPeoplePresenceData)
                .then(res => resolve(<PersonPresenceData[]>res.data))
                .catch(reject);
        })
    }
}

export default new PresenceDataAgent(config.presenceData)