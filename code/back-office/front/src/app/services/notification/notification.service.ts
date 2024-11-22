import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Client } from '@stomp/stompjs';
import SocketJS from 'sockjs-client';

@Injectable({
  providedIn: 'root',
})
export class NotificationService {
  private stompClient: Client | null = null;

  private messageSubject = new BehaviorSubject<any>(null);
  public message$ = this.messageSubject.asObservable();

  private connectionSubject = new BehaviorSubject<boolean>(false);
  public connection$ = this.connectionSubject.asObservable();

  connect(topic: string) {
    const socket = new SocketJS('http://localhost:8081/ws');
    this.stompClient = new Client({
      webSocketFactory: () => socket,
      debug: (str) => {
        console.log(str);
      },
    });

    this.stompClient.onConnect = (frame) => {
      console.log('Connected: ' + frame);
      this.connectionSubject.next(true);
      this.stompClient?.subscribe(topic, (message) => {
        this.messageSubject.next(message);
      });
    };

    this.stompClient.onStompError = (frame) => {
      console.error('Broker reported error: ' + frame.headers['message']);
      console.error('Additional details: ' + frame.body);
    };
    this.stompClient?.activate();
  }

  disconnect() {
    if (this.stompClient) {
      this.stompClient.deactivate();
    }
  }
}
