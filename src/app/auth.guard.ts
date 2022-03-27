import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { RegistrationService } from './registration.service';
import { User } from './user';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  
  constructor(private service: RegistrationService,private route:Router) { 
  }
  canActivate()
  {
     if(this.service.IsLoggedUser())
     {
       localStorage.clear();
       return true;
     }
     else
     {
       localStorage.clear();
       this.route.navigate(["signup"])
       return false;
     }
  }
    
      
  }



