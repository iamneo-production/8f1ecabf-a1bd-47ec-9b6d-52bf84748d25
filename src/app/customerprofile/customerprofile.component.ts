import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { LocalStorageService, SessionStorageService } from 'angular-web-storage';
import { BehaviorSubject, Observable } from 'rxjs';
import { Subject } from 'rxjs';
import { MessageService } from '../service/message.service';

@Component({
  selector: 'app-customerprofile',
  templateUrl: './customerprofile.component.html',
  styleUrls: ['./customerprofile.component.css']
})
export class CustomerprofileComponent implements OnInit {
  editoption:boolean=false;
   
  // private messageTracker = new BehaviorSubject<any>("light");



  url: string = "http://localhost:8090/";
  public data:any=[];
  userId: string = "";
  profileArray:any;
  numberExist:boolean=false;
  emailExist:boolean=false;
  originalNumber:any;
  originalEmail:any;
  addprofileArray:any;
  proArray:any;
  prolength:any;
  editproArray:any;
  loanDetailsArray:any;
  reviewArray:any;
  reviewLength:any;
  rating:any=0;
  rowArray:any=[];
  empty:any='';
  
  constructor(private router: Router, private httpObj: HttpClient, private route: ActivatedRoute, private local: LocalStorageService, private session: SessionStorageService,private messageService: MessageService) { }

  ngOnInit(): void {
    this.userId=this.getFromLocal(1);
    if(this.userId==null)
    {
      this.router.navigate([""]);
    }
    else
    {

      let addUser = this.httpObj.get(this.url + "user/getUserdata/"+this.userId);
    addUser.subscribe((response)=>{
      console.log(response);

      this.profileArray=response;
     
      this.originalEmail=this.profileArray[0].email;
      this.originalNumber=this.profileArray[0].number;
      console.log("email is "+this.originalEmail);
      console.log("number is "+this.originalNumber);



      let addUsers = this.httpObj.get(this.url + "user/getProfile/"+this.userId);
      addUsers.subscribe((response)=>{
        console.log(response);
  
       this.proArray=response;
       
       this.prolength=this.proArray.length;

       let addUserss = this.httpObj.get(this.url + "user/viewLoans/"+this.userId);
       addUserss.subscribe((response)=>{
       console.log(response);
       this.loanDetailsArray=response;


       let addUsersss = this.httpObj.get(this.url + "user/getReview/"+this.userId);
       addUsersss.subscribe((response)=>{
       console.log(response);
      this.reviewArray=response;
      this.reviewLength=this.reviewArray.length;

      

      });


      });
  
       
      });



      
    
         
  

    });

    }
  }

  editform()
  {
    this.editoption=true;
    $('#editbtn').fadeOut();
    // $('#editbtn').attr("disabled",'disabled');

  }

  counter(i: number) {
    return new Array(i);
}

countStar(param : any)
{
  this.rating=param;
  console.log("rating is "+this.rating);
} 


editReview(param : any)
{
    this.rowArray=[];
    for(var i=0;i<this.reviewArray.length;i++)
    {
      if(this.reviewArray[i].reviewId==param)
      {
        this.rowArray.push(this.reviewArray[i]);
      }
    }

    this.rating=this.rowArray[0].rating;
    $('#star'+this.rating).click();

    console.log("rating is "+this.rating);
    

    
}

submitReview(param: any, id: any)
{
   console.log("id is "+id);
   console.log("feedback "+param);

   var submitArray={
     "reviewId": id,
     "rating": this.rating,
     "feedback": param
   }

   let addUser = this.httpObj.put(this.url + "user/editReview",submitArray);
    addUser.subscribe((response)=>{
    console.log(response);
     this.ngOnInit();
    $('#exitModal').click();
     
    }); 
   
   
}

deleteReview(param : any)
{
  console.log("delete id is "+param);

  let addUser = this.httpObj.delete(this.url + "user/deleteReview/"+param);
    addUser.subscribe((response)=>{
    console.log(response);
     
    $('#closeStaticModal').click();

    this.ngOnInit();
     
    }); 
  
}

changePassword()
{
  $('#openPass').click();
}

showeye(param : any)
  {
    if(param==0)
    {
    $('#password').attr('type','text');
    $('#openeye0').hide();
    $('#closeeye0').show();
    setTimeout(() => {
      $('#password').attr('type','password');
    $('#openeye0').show();
    $('#closeeye0').hide();
    }, 1000);
  }
  else if(param==1)
  {
    $('#confirmPassword').attr('type','text');
    $('#openeye1').hide();
    $('#closeeye1').show();
    setTimeout(() => {
      $('#confirmPassword').attr('type','password');
    $('#openeye1').show();
    $('#closeeye1').hide();
    }, 1000);
  }
  else if(param==2)
  {
   
  }
  else if(param==3)
  {
   
  }
  else if(param==4)
  {
    
  }

   
    
  }

  submitPassword(param: any)
  {
    var email=this.profileArray[0].email;

    var loginArray={
      "email":email,
      "password":param
    }

    console.log(loginArray);

    let addUser = this.httpObj.post(this.url + "user/updatepassword",loginArray);
    addUser.subscribe((response)=>{
    console.log(response);
     
     $('#updateSuccess').fadeIn();
     setTimeout(() => {
      $('#updateSuccess').fadeOut();
      $('#resetForm').click();
      $('#closePassword').click();
      
     }, 1000);
    }); 
    


    
  }

  onClose()
  {
    this.ngOnInit();
  }


  selectChange(param: any)
  {
    console.log(param);
    this.sendMessage(param);
    
  }

  sendMessage(param: any): void {
    // send message to subscribers via observable subject
    this.messageService.sendMessage(param);
}

clearMessages(): void {
    // clear messages
    this.messageService.clearMessages();
}

  

  getFromLocal(key: any): any {
    console.log('recieved= key:' + key);
    this.data[key]= this.local.get(key);
    console.log(this.data);
    return this.data[key];
   }

   openForm()
   {
     $('#addprofileinfo').slideToggle();
   }

   showAll()
   {
     $('#smallBar').slideToggle();
   }

   openReview()
   {
     $('#reviewLaunch').click();
   }


   checkNumber(param: any)
   {
     console.log("number is "+param);
     if(param!=null && param!=undefined && param!="" && param!=this.originalNumber){
      let addUser = this.httpObj.get(this.url + "checkNumber/" +param);
      addUser.subscribe((response)=>{
      console.log(response);
      if(response==true)
      {
        this.numberExist=true;
      }
      else
      {
        this.numberExist=false;
      }
    });
    }
     
   }
  
   checkEmail(param: any)
   {
     console.log("email is "+param);
  
     if(param!=null && param!=undefined && param!="" && param!=this.originalEmail){
      let addUser = this.httpObj.get(this.url + "checkEmail/" +param);
      addUser.subscribe((response)=>{
      console.log(response);
      if(response==true)
      {
        this.emailExist=true;
        
      }
      else
      {
        this.emailExist=false;
       
      }
    }); 
    }
     
   }

   addProfile(param: any)
   {
     

     this.addprofileArray={
       "userId":this.userId,
       "name":param.value.name,
       "number":param.value.mobileNo,
       "email":param.value.email,
       "address":param.value.address
     }
     console.log(this.addprofileArray);
     
     let addUser = this.httpObj.post(this.url + "user/addProfile", this.addprofileArray);
    addUser.subscribe((response)=>{
    console.log(response);
    
      this.ngOnInit();

    }); 

     
   }

   updateProfile(param : any)
   {

       this.editproArray={
    
        "name":param.value.name,
        "number":param.value.mobileNo,
        "email":param.value.email,
        "address":param.value.address
       }

       console.log(this.editproArray);

      

       let addUser = this.httpObj.put(this.url + "user/editProfile/"+this.userId, this.editproArray);
       addUser.subscribe((response)=>{
       console.log(response);
       
        this.editoption=false;
        $('#editbtn').fadeIn();
         this.ngOnInit();
   
       }); 
       

   }

   deletePro()
   {
     $('#deleteLaunch').click();
   }

   deleteprofile()
   {
     console.log("userId is "+this.userId);

     let addUser = this.httpObj.delete(this.url + "user/deleteProfile/"+this.userId);
     addUser.subscribe((response)=>{
     console.log(response);
     
     $('#deletethisloanfast').click();

       this.ngOnInit();
 
     }); 

     
   }
  


}
