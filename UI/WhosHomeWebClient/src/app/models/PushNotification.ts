export enum NotificationType {
    SENSOR_STATUS_CHANGED = "SENSOR_STATUS_CHANGED",
    SENSOR_ERROR = "SENSOR_ERROR",
    ENGINE_STATUS_CHANGED = "ENGINE_STATUS_CHANGED",
    ACTIVITY_DETECTION = "ACTIVITY_DETECTION"
}

export default class PushNotification<T> {
    private _receiveTime: Date;
    private _notificationType: NotificationType;
    private _payload: T;
    private _readed: boolean;

    public constructor(type: NotificationType, payload: T, receiveTime?: Date) {
        this._notificationType = type;
        this._payload = payload;
        this._readed = false;
        this._receiveTime = receiveTime || new Date();
    }

    public receiveTime(): Date {
        return this._receiveTime;
    }

    public notificationType(): NotificationType {
        return this._notificationType;
    }

    public payload(): T {
        return this._payload;
    }

    public markAsReaded(): void {
        this._readed = true;
    }

    public static of<T>(type: NotificationType, payload: T): PushNotification<T> {
        return new PushNotification(type, payload);
    }
}