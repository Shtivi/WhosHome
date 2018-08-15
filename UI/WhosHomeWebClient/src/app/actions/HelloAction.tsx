import { BaseAction } from "./BaseAction";

export class HelloAction extends BaseAction {
    public type: string = 'HELLO_ACTION';
    
    public constructor(public name: string) {
        super();
    }
}