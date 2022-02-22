import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TempService {

  constructor(private http: HttpClient) { }

  private apiUrl = 'http://localhost:9001/directory'
  
  public getDirectories(): Observable<any> {
    return this.http.get(this.apiUrl);
  }
}
