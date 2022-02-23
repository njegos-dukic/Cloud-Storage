import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DMSFile } from '../interfaces/dms-file';
import { DMSFolder } from '../interfaces/dms-folder';

@Injectable({
  providedIn: 'root'
})
export class TempService {

  constructor(private http: HttpClient) { }
  
  public getFileStructure(): Observable<DMSFolder> {
    let apiUrl = 'http://localhost:9001/directory';
    return this.http.get<DMSFolder>(apiUrl);
  }

  public delete(currentPath: string, iNodeName: string): Observable<any> {
    let apiUrl = 'http://localhost:9001/directory';
    let body = currentPath + '/' + iNodeName;
    return this.http.post(apiUrl, body);
  }

  public logOut(): any {
    let apiUrl = "http://localhost:9001/logout";
    location.href = apiUrl;
  }
}
