import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule } from '@angular/forms';
import {Router,ActivatedRoute} from'@angular/router';
import { Loan } from '../loan';
import { LoanServiceService } from '../loan-service.service';
import { Observable } from "rxjs";
@Component({
  selector: 'app-applyloan1',
  templateUrl: './applyloan1.component.html',
  styleUrls: ['./applyloan1.component.css']
})
export class Applyloan1Component implements OnInit {
  
  

  
  constructor(private router:Router,
    private route:ActivatedRoute ,private http:HttpClient,private employeeService:LoanServiceService) { 
      
     
    }
   users:any;
    data:any;
  ngOnInit(): void {  
  
   let rep=this.http.get("http://localhost:8086/applyloanget");
   rep.subscribe((data)=>this.users=data);
    
    this.route.queryParams.subscribe((params)=>{
      this.data=JSON.parse(atob(params.data));
    }
    )
     
  }
  

  
    


  

  
}

