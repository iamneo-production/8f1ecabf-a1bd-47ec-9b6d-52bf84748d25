import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { LocalStorageService, SessionStorageService } from 'angular-web-storage';
import { MessageService } from '../service/message.service';

@Component({
  selector: 'app-adminrequest',
  templateUrl: './adminrequest.component.html',
  styleUrls: ['./adminrequest.component.css']
})
export class AdminrequestComponent implements OnInit {


  url: string = "http://localhost:8090/";
  public data:any=[];
  userId: string = "";
  requestArray:any;
  requestLength:any;
  userArray:any;
  userLength:any;
  reviewArray:any;
  reviewLength:any;
  deleteId:any;

  constructor(private router: Router, private httpObj: HttpClient, private route: ActivatedRoute, private local: LocalStorageService, private session: SessionStorageService,private messageService: MessageService) { }

  ngOnInit(): void {

    this.userId=this.getFromLocal(1);
    if(this.userId==null)
    {
      this.router.navigate([""]);
    }
    else
    {
      let addUsers = this.httpObj.get(this.url + "admin/getAllRequest");
      addUsers.subscribe((response)=>{
      console.log(response);
       this.requestArray=response;
      this.requestLength=this.requestArray.length;

      $('#onerequest').click();

      let addUserss = this.httpObj.get(this.url + "getAllUser");
      addUserss.subscribe((response)=>{
      console.log(response);
      
      this.userArray=response;
      this.userLength=this.userArray.length;



      let addUsersss = this.httpObj.get(this.url + "getAllReview");
      addUsersss.subscribe((response)=>{
      console.log(response);
      
       this.reviewArray=response;
       this.reviewLength=this.reviewArray.length;

     
       
      }); 

       
      }); 

       
      }); 
    }

  }

  getFromLocal(key: any): any {
    console.log('recieved= key:' + key);
    this.data[key]= this.local.get(key);
    console.log(this.data);
    return this.data[key];
   }

   counter(i: number) {
    return new Array(i);
}

deleteReview(param : any)
{
  console.log("delete id is "+param);

  let addUser = this.httpObj.delete(this.url + "user/deleteReview/"+param);
    addUser.subscribe((response)=>{
    console.log(response);
     
    let addUsersss = this.httpObj.get(this.url + "getAllReview");
      addUsersss.subscribe((response)=>{
      console.log(response);
      
       this.reviewArray=response;
       this.reviewLength=this.reviewArray.length;

     
       
      }); 
     
    }); 
  
}

deleteUser(param : any)
{
  console.log("delete id is "+param);

  this.deleteId=param;

  $('#deleteLaunch').click();
  
}

deleteThisUser()
{
  

   console.log("delete Id is "+this.deleteId);
   

  let addUsers = this.httpObj.delete(this.url + "admin/deleteThisUser/"+this.deleteId);
      addUsers.subscribe((response)=>{
      console.log(response);
      
      let addUserss = this.httpObj.get(this.url + "getAllUser");
      addUserss.subscribe((response)=>{
      console.log(response);
      
      this.userArray=response;
      this.userLength=this.userArray.length;
      $('#deletethisloanfast').click();
    }); 
      
    }); 

}

   showRequest()
   {
      $('#requestShow').fadeIn();
      $('#userShow').hide();
      $('#feedbackShow').hide();

      $('#onerequest').css("transform","scale(1.4)");
      $('#twouser').css("transform","scale(1.0)");
      $('#threefeedback').css("transform","scale(1.0)");
   }

   showUser()
   {
    $('#userShow').fadeIn();
    $('#requestShow').hide();
    $('#feedbackShow').hide();

    $('#twouser').css("transform","scale(1.4)");
    $('#onerequest').css("transform","scale(1.0)");
    $('#threefeedback').css("transform","scale(1.0)");
   }

   showFeedback()
   {
    $('#feedbackShow').fadeIn();
    $('#userShow').hide();
    $('#requestShow').hide();

    $('#threefeedback').css("transform","scale(1.4)");
    $('#onerequest').css("transform","scale(1.0)");
    $('#twouser').css("transform","scale(1.0)");
   }

   addAdmin(param: any)
   {
      console.log("add id is "+param);

      let addUsers = this.httpObj.get(this.url + "admin/addRequest/"+param);
      addUsers.subscribe((response)=>{
      console.log(response);
      
      this.ngOnInit();

      }); 


      
   }
   deleteAdmin(param: any)
   {
       console.log("delete id is "+param);

       let addUsers = this.httpObj.get(this.url + "admin/deleteRequest/"+param);
       addUsers.subscribe((response)=>{
       console.log(response);
       
       this.ngOnInit();
       
       }); 
       
   }

}
