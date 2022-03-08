import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { RegistrationService } from '../registration.service';
import { User } from '../user';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  user:User= new User();
  exform!: FormGroup;
  constructor(private loginuserservice: RegistrationService) { 
  }

  ngOnInit(): void {
    this.exform= new FormGroup({
      'email': new FormControl(null,[Validators.required,Validators.email]),
      'password': new FormControl(null,[Validators.required,Validators.minLength(5)])
    });

  }
  /*clicksub()
  {
    console.log(this.exform.value);
    this.exform.reset();
  }*/
   get email()
   {
     return this.exform.get('email');
   }
   get password()
   {
     return this.exform.get('password');
   }

   loginUser()
   {
     console.log(this.user);
     this.loginuserservice.loginUserFromRemote(this.user).subscribe(
       data=>{alert("Login success")},
       error=>alert("bad credentials"))
     
   }
}
