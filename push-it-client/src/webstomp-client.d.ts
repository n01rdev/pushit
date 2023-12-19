declare module 'webstomp-client' {
    export interface Client {
        connect(headers: any, connectCallback: () => void, errorCallback: (error: any) => void): void;
        disconnect(disconnectCallback: () => void): void;
        subscribe(destination: string, callback: (message: any) => void): void;
    }

    export function over(socket: WebSocket): Client;
}