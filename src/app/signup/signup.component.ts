import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RegistrationService } from '../registration.service';
import { User } from '../user';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  exform1: FormGroup
  p=  "[6-9]\\d{9}";
  user: User =new User();
  constructor(private fb: FormBuilder,private loginuserservice1: RegistrationService) { 
    this.exform1= this.fb.group({
      ua: [null,Validators.required],
      email: [null,[Validators.required,Validators.email]],
      uname: [null,Validators.required],
      mobile: [null,[Validators.required,Validators.pattern(this.p)]],
      password: [null,[Validators.required,Validators.minLength(5)]],
      cpassword: [null,Validators.required]
    },
    {
      validators: this.MustMatch('password','cpassword')
    })
  }

  ngOnInit():void{

  }
  /*clicksub1()
  {
    console.log(this.exform1.value);
    this.exform1.reset();
  }*/
   get email()
   {
     return this.exform1.get('email');
   }
   get password()
   {
     return this.exform1.get('password');
   }
   get ua()
   {
     return this.exform1.get('ua');
   }
   get uname()
   {
     return this.exform1.get('uname');
   }
   get mobile()
   {
     return this.exform1.get('mobile');
   }
   get cpassword()
   {
     return this.exform1.get('cpassword');
   }
   MustMatch(pass: string, cpass: string)
   {
     return (formgroup:FormGroup) =>{
       const p= formgroup.controls[pass];
       const cp= formgroup.controls[cpass];
       if(cp.errors && !cp.errors?.['MustMatch']){
         return
       }
       if(p.value !== cp.value)
       {
         cp.setErrors({MustMatch:true});
       }
       else
       {
         cp.setErrors(null);
       }
     }
   }

   userRegister()
   {
     console.log(this.user);
     this.loginuserservice1.loginUserFromRemote1(this.user).subscribe(data=>{
       alert("User is registered")
     },error=>alert("User already exists"));
   }

}
