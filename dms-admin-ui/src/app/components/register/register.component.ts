import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  firstName!: string;
  lastName!: string;
  username!: string;
  email!: string;
  password!: string;
  rePassword!: string;
  error!: string;

  constructor() { }

  ngOnInit(): void {
  }

  register(): void {
    return;
  }
}
