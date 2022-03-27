import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { User } from './user';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {
  

  
  constructor(private _http :HttpClient,private router:Router) { }

  public loginUserFromRemote(user: User):Observable<any>
  {
    console.log(user);
    return this._http.post<any>("http://localhost:8080/login",user);

  }

  public getType(user:User):Observable<any>
  {
    return this._http.post<any>("http://localhost:8080/userType",user);
  
  }
   IsLoggedUser()
   {
     return localStorage.getItem('token')=="user";
   }
   IsLoggedAdmin()
   {
     return localStorage.getItem('token')=="admin";
   }
   

  public loginUserFromRemote1(user: User):Observable<any>
  {
    console.log(user);
    if(user.userType=="user")
    return this._http.post<any>("http://localhost:8080/user/signup",user);
    else
    return this._http.post<any>("http://localhost:8080/admin/signup",user); 
  }

  
}
