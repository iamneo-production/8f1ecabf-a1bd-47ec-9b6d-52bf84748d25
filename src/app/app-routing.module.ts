import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ApplyloanComponent } from './applyloan/applyloan.component';
import { Applyloan1Component } from './applyloan1/applyloan1.component';
import { LoanidpageComponent } from './loanidpage/loanidpage.component';


const routes: Routes = [
  {path:'Apply Loan',component:ApplyloanComponent},
  {path:'Loan Status',component:ApplyloanComponent},
  {path:'Profile',component:ApplyloanComponent},
  {path:'VIRTUSA/loanidpage',component:LoanidpageComponent},
  {path:'VIRTUSA/applyloan1',component:Applyloan1Component},
  

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],

  exports: [RouterModule]
})
export class AppRoutingModule { }
export const rountingComponents=[ApplyloanComponent,LoanidpageComponent]