import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Log } from '../interfaces/log';

@Injectable({
  providedIn: 'root'
})
export class LogService {

  constructor(private http: HttpClient) { }

  public getLogs(): Observable<Log[]> {
    let apiUrl = 'http://localhost:9000/logs'
    return this.http.get<Log[]>(apiUrl);
  }

  public logOut(): any {
    let apiUrl = "http://localhost:9000/logout";
    location.href = apiUrl;
  }
}
