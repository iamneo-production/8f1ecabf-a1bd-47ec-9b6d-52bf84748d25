import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminnavComponent } from './adminnav/adminnav.component';
import { AuthGuard } from './auth.guard';
import { AuthadminGuard } from './authadmin.guard';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import { UsernavComponent } from './usernav/usernav.component';

const routes: Routes = [
  {path:'',component:LoginComponent},
  {path:'signup',component:SignupComponent},
  {path:'user',component:UsernavComponent,canActivate:[AuthGuard]},
  {path:'admin',component:AdminnavComponent,canActivate:[AuthadminGuard]}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
