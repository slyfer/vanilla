import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { LoginRequest } from '../domain/login-request';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  private loginForm: FormGroup;
  private logged: boolean;

  constructor(private httpClient: HttpClient, private formBuilder: FormBuilder) {
  }

  ngOnInit() {
    this.logged = false;

    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  submit() {
    if (this.loginForm.valid) {
      const loginRequest = {...new LoginRequest(), ...this.loginForm.value};

      this.httpClient.post('/api/auth/signin', loginRequest, {observe: 'response'}).subscribe(
        value => this.logged = true
      );
    }
  }
}


