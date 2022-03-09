import { DatePipe } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { LocalStorageService, SessionStorageService } from 'angular-web-storage';

@Component({
  selector: 'app-repaymentschedule',
  templateUrl: './repaymentschedule.component.html',
  styleUrls: ['./repaymentschedule.component.css']
})
export class RepaymentscheduleComponent implements OnInit {

  url: string = "http://localhost:8090/";
  public data:any=[];
  userId: string = "";
  loanId:any;
  IdArray:any;
  role:any;
  repayArray:any;
  rowArray:any=[];
  dueDate:String="";
  paidDate:any;
  loanArray:any;
  recoveryAmount:any=0;
  totalInstallment:any;
  repayLength:any;
  todayDate:String="";
  totalAmountPaid:any;
  // dateArray:any=[];
  // paidDateArray:any=[];

  

  constructor(private router: Router, private httpObj: HttpClient, private route: ActivatedRoute, private local: LocalStorageService, private session: SessionStorageService) { }

  ngOnInit(): void {
    this.userId=this.getFromLocal(1);
    if(this.userId==null)
    {
      this.router.navigate([""]);
    }
    else
    {
      this.loanId=this.getFromLocal(3);
      if(this.loanId==null)
      {
        this.router.navigate([""]);
      }
      else
     {
      let addUser = this.httpObj.get(this.url + "user/getUserdata/"+this.userId);
      addUser.subscribe((response)=>{
        console.log(response);
        this.IdArray=response;
       this.role=this.IdArray[0].userRole;
       

       let addUsers = this.httpObj.get(this.url + "getSchedule/"+this.loanId);
      addUsers.subscribe((response)=>{
        console.log(response);
        
        this.repayArray=response;

         this.todayDate = new Date().toISOString().slice(0, 10);
        console.log(this.todayDate);

        this.repayLength=this.repayArray.length;

        if(this.repayLength==0)
        {
          this.recoveryAmount=0;
        }

        this.recoveryAmount=0;
        this.totalAmountPaid=0;

        this.totalInstallment=this.repayArray.length;

        for(var i=0;i<this.repayArray.length;i++)
        {

          this.totalAmountPaid+=+(this.repayArray[i].amountPaid);
          if(this.repayArray[i].amountPaid!='0.0')
          {
          this.recoveryAmount+=+(this.repayArray[i].total);
          }
          
        }
        this.recoveryAmount=Math.round(this.recoveryAmount);

        let addUserss = this.httpObj.get(this.url + "getThisLoan/"+this.loanId);
        addUserss.subscribe((response)=>{
          console.log(response);
          
         this.loanArray=response;
         
        });

       
      });



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

   editSchedule(param: any)
   {
     console.log("Installment no is "+param);

     this.rowArray=[];
    

     for(var i=0;i<this.repayArray.length;i++)
     {
       if(this.repayArray[i].installmentNo==param)
       {
         this.rowArray.push(this.repayArray[i]);
       }
     }

   console.log(this.rowArray);

   $('#editRepay').click();

    //  DueDate
    //  var date_string=this.rowArray[0].dueDate;
   
    //  setTimeout(() => {
    //   this.rowArray[0].dueDate=this.reverseString(date_string);
    //  }, 240);
      

    // console.log("Correct date is "+this.reverseString(date_string));
    

    //  PaidDate
    //  var date_strings=this.rowArray[0].paidDate;
    //  if(date_strings!=null)
    //  {
     
    //   setTimeout(() => {
    //     this.rowArray[0].paidDate=this.reverseString(date_strings);
    //    }, 240);
     
    //  }
     
       
     
   }

   showDate(param: any)
   {
     console.log("date is "+param);
     
   }

   reverseString(param: any) : string
   {
    let parts_of_dates = param.split("-");
    var paidDate=parts_of_dates[2]+'-'+parts_of_dates[1]+'-'+parts_of_dates[0];
    return paidDate;
   }

   closeThis()
   {
     this.ngOnInit();
   }

   printTable()
   {

    $('#printprint').hide();

    window.print();

    setTimeout(() => {
      $('#printprint').show();
    }, 200);


   }

   deleteSchedule()
   {
     $('#deleteLaunch').click();
   }

   deleteRepayment()
   {
    let addUserss = this.httpObj.delete(this.url + "admin/deleteRepaymentSchedule/"+this.loanId);
    addUserss.subscribe((response)=>{
      console.log(response);
      
      $('#deletethisloanfast').click();

      this.router.navigate(["adminappliedloan"]);
     
     
    });
   }

   editThisSchedule(param: any)
   {
     console.log(param.value);

     var editArray={
      "loanId": this.loanId,
       "installmentNo": this.rowArray[0].installmentNo,
        "emi": this.rowArray[0].emi,
       "fine": this.rowArray[0].fine,
       "total": this.rowArray[0].total,
       "amountPaid":this.rowArray[0].total,
       "dueDate": this.rowArray[0].dueDate,
       "paidDate":this.reverseString(param.value.paidDate)
     }

     console.log(editArray);

     let addUserss = this.httpObj.post(this.url + "admin/editRepaymentSchedule/"+this.loanId,editArray);
     addUserss.subscribe((response)=>{
       console.log(response);
       this.ngOnInit();
       $('#closethis').click();
      
      
     });
     
     
   }



}
