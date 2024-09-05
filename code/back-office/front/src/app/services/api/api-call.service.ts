import { HttpClient } from '@angular/common/http';
import {
  BehaviorSubject,
  catchError,
  firstValueFrom,
  map,
  tap,
  throwError,
} from 'rxjs';
import { env } from '../../../environment/env';
import { ApiResponse } from '../../dto/api.response';

export class ApiCallService {
  protected baseUrl = env.baseUrl;
  protected httpClient: HttpClient;

  private loadingSubject = new BehaviorSubject<boolean>(false);
  public loading$ = this.loadingSubject.asObservable();
  private errorSubject = new BehaviorSubject<string | null>(null);
  public error$ = this.errorSubject.asObservable();
  private messageSubject = new BehaviorSubject<string | null>(null);
  public message$ = this.messageSubject.asObservable();

  protected constructor(httpClient: HttpClient) {
    this.httpClient = httpClient;
  }

  protected async getCall<T>(url: string): Promise<ApiResponse<T>> {
    this.loadingSubject.next(true);
    const res = this.httpClient.get(`${this.baseUrl}/${url}`).pipe(
      tap((response: any) => {
        this.loadingSubject.next(false);
        this.messageSubject.next(response.message || null);
        setTimeout(() => {
          this.messageSubject.next(null);
        }, 5000);
      }),
      catchError((error) => {
        this.loadingSubject.next(false);
        this.errorSubject.next(error.error.message);
        setTimeout(() => {
          this.errorSubject.next(null);
        }, 5000);
        return throwError(() => error);
      })
    );
    return await firstValueFrom(res);
  }

  protected async postCall<T>(url: string, data: any): Promise<ApiResponse<T>> {
    this.loadingSubject.next(true);
    const res = this.httpClient.post(`${this.baseUrl}/${url}`, data).pipe(
      tap((response: any) => {
        this.loadingSubject.next(false);
        this.messageSubject.next(response.message || null);
        setTimeout(() => {
          this.messageSubject.next(null);
        }, 5000);
      }),
      catchError((error) => {
        this.loadingSubject.next(false);
        this.errorSubject.next(error.error.message);
        setTimeout(() => {
          this.errorSubject.next(null);
        }, 5000);
        return throwError(() => error);
      }),
      map((response: any) => response)
    );
    return await firstValueFrom(res);
  }

  protected async putCall<T>(url: string, data: any): Promise<ApiResponse<T>> {
    this.loadingSubject.next(true);
    const res = this.httpClient.put(`${this.baseUrl}/${url}`, data).pipe(
      tap((response: any) => {
        this.loadingSubject.next(false);
        this.messageSubject.next(response.message || null);
        setTimeout(() => {
          this.messageSubject.next(null);
        }, 5000);
      }),
      catchError((error) => {
        this.loadingSubject.next(false);
        this.errorSubject.next(error.error.message);
        setTimeout(() => {
          this.errorSubject.next(null);
        }, 5000);
        return throwError(() => error);
      })
    );
    return await firstValueFrom(res);
  }

  protected async deleteCall<T>(url: string): Promise<ApiResponse<T>> {
    this.loadingSubject.next(true);
    const res = this.httpClient.delete(`${this.baseUrl}/${url}`).pipe(
      tap((response: any) => {
        this.loadingSubject.next(false);
        this.messageSubject.next(response.message || null);
        setTimeout(() => {
          this.messageSubject.next(null);
        }, 5000);
      }),
      catchError((error) => {
        this.loadingSubject.next(false);
        this.errorSubject.next(error.error.message);
        setTimeout(() => {
          this.errorSubject.next(null);
        }, 5000);
        return throwError(() => error);
      })
    );
    return await firstValueFrom(res);
  }
}
