import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
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
  responsedata: any;

  constructor(private loginuserservice: RegistrationService,private router:Router) { 
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
     this.loginuserservice.getType(this.user).subscribe({
      next:(res:any)=>{
        console.log(res);
        if(res===null)
        {
          alert("register yourself");
        }
        else if(res.userType=="user")
        {
        this.loginuserservice.loginUserFromRemote(this.user).subscribe(
          data=>{alert("Login success"),
          localStorage.setItem('token',res.userType),
          this.router.navigate(["user"])}
        );
    
        
        }
        else if(res.userType=="admin")
        {
          this.loginuserservice.loginUserFromRemote(this.user).subscribe(
            data=>{alert("Login success"),
            localStorage.setItem('token',res.userType),
            this.router.navigate(["admin"])}
            
        );
        
          }
        }
     });
          
          
   }
  }


