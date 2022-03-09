import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { LocalStorageService, SessionStorageService } from 'angular-web-storage';
import { UploadFileService } from '../upload-file.service';

@Component({
  selector: 'app-appliedloan',
  templateUrl: './appliedloan.component.html',
  styleUrls: ['./appliedloan.component.css']
})
export class AppliedloanComponent implements OnInit {
  loanId:any;
  url: string = "http://localhost:8090/";
  public data:any=[];
  userId: string = "";
  rating:any=0;
  userArray:any;

  name = new FormControl('', Validators.required);  

  constructor(private router: Router, private httpObj: HttpClient, private route: ActivatedRoute, private local: LocalStorageService, private session: SessionStorageService, private uploadService: UploadFileService) { }

  ngOnInit(): void {

    this.userId=this.getFromLocal(1);
    if(this.userId==null)
    {
      this.router.navigate([""]);
    }
    else
    {
      this.loanId=this.getFromLocal(2);
      if(this.loanId==null)
      {
        this.router.navigate([""]);
      }
      else{

        let addUser = this.httpObj.get(this.url + "user/getUserdata/"+this.userId);
    addUser.subscribe((response)=>{
      console.log(response);
      this.userArray=response;

        setTimeout(() => {
          $('#onLaunch').click();
        }, 1000);

      });

      }
    }
  }


  getFromLocal(key: any): any {
    console.log('recieved= key:' + key);
    this.data[key]= this.local.get(key);
    console.log(this.data);
    return this.data[key];
   }

   backtoLoan()
   {
    localStorage.removeItem('2');
    this.router.navigate(["customerapplyloan"]);
   }

   countStar(param : any)
   {
     console.log("No of stars is "+param);
     this.rating=param;
     
   }

   onSubmit()
   {
     console.log(this.name.value);
     console.log("rating is "+this.rating);

     var reviewArray={
       "userId": this.userId,
       "email" : this.userArray[0].email,
       "loanId": this.loanId,
       "rating": this.rating,
       "feedback" : this.name.value
     }

     console.log(reviewArray);

    

     let addUsers = this.httpObj.post(this.url + "user/addReview", reviewArray);
     addUsers.subscribe((response)=>{
     console.log(response);
 
     $('#deletethisloanfast').click();
      
     }); 
     
     
     
   }



}
