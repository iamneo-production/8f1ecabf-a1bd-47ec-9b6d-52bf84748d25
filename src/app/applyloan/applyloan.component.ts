import { Component, OnInit } from '@angular/core';
import {FormGroup} from '@angular/forms'
import { FormControl } from '@angular/forms';
import { FormsModule } from '@angular/forms';
import { Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Loan } from '../loan';
import { LoanServiceService } from '../loan-service.service';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
@Component({
  selector: 'app-applyloan',
  templateUrl: './applyloan.component.html',
  styleUrls: ['./applyloan.component.css']
})
export class ApplyloanComponent implements OnInit {

 public  loginForm  =new FormGroup({
   name:new FormControl('',[Validators.required,Validators.pattern('[a-zA-Z]+$')]),

   address: new FormControl('',[Validators.required]),
   mobile: new FormControl('',[Validators.required,Validators.pattern('[0-9]+$'),Validators.minLength(10),Validators.maxLength(10)]),
   email:new FormControl('',[Validators.required,Validators.email]),
   Aadhaar: new FormControl('',[Validators.required,Validators.pattern('[0-9]+$'),Validators.minLength(12),Validators.maxLength(12)]),
   pan: new FormControl('',[Validators.required]),
   salary: new FormControl('',[Validators.required,Validators.pattern('[0-9]+$')]),
   
   amount: new FormControl('',[Validators.required,Validators.pattern('[0-9]+$')]), 
   repaymentmonths: new FormControl('',[Validators.required,Validators.pattern('[0-9]+$')]),
   
 })
 loan1: Loan =new Loan();
 newLoan():void{
   this.loan1=new Loan();

 }
 loginuser(){
  console.log(this.loginForm.value);
 }
 get repaymentmonths(){
  return this.loginForm.get('repaymentmonths');
}
 get loan(){
  return this.loginForm.get('loan');
}
 get pan(){
  return this.loginForm.get('pan');
}
get salary(){
  return this.loginForm.get('salary');
}
 get user(){
  return this.loginForm.get('user');
}
 get name(){
   return this.loginForm.get('name');
 }
get address(){
  return this.loginForm.get('address');
}
get mobile(){
  return this.loginForm.get('mobile');
}
get email(){
  return this.loginForm.get('email');
}
get Aadhaar(){
  return this.loginForm.get('Aadhaar');
}
get amount(){
  return this.loginForm.get('amount');
}
 constructor( private router: Router,private route:ActivatedRoute,private http:HttpClient,private loanservice:LoanServiceService) { 
   
  }
  


  ngOnInit(): void {
    
  }
   
  



  data1:any;
   submitForm(){
    this.loanservice.createloandetails(this.loan1).subscribe(data=>{
      console.log(data);
       this.data1=data;
       this.loan1=new Loan();
       this.goto();
    },
    error=>console.error(error));
    
   
    
    
  }
  goto(){
    this.router.navigate(['VIRTUSA/applyloan1'],{
      queryParams:{data:btoa(JSON.stringify(this.data1))}
    });
  }
 

}