import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from './user';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  
  constructor(private _http :HttpClient) { }

  public loginUserFromRemote(user: User):Observable<any>
  {
    console.log(user);
    return this._http.post<any>("http://localhost:8080/login",user);

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
