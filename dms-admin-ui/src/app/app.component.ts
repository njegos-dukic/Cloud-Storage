import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Log } from './interfaces/log';
import { LogService } from './services/log.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  public logs: Log[] = [];
  public error: string = '';
  public noLogs: string = '';

  public date: Date = new Date();

  constructor(private logService: LogService) { }

  ngOnInit(): void {
    this.logService.getLogs().subscribe(res => { 
      this.error = ''; 
      this.noLogs = '';
      this.logs = res;
      if (this.logs.length == 0)
        this.noLogs = 'No logs.'
      this.logs.forEach(l => l.dateTime = new Date(l.dateTime));
      console.log(this.logs);
    }, 
    err => { 
      this.error = "Please log in as administrator!" 
    });
  }

  public logOut() {
    this.logService.logOut();
  }
}
