import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { RegistrationService } from './registration.service';

@Injectable({
  providedIn: 'root'
})
export class AuthadminGuard implements CanActivate {
  constructor(private service: RegistrationService,private route:Router) { 
  }
  canActivate()
  {
     if(this.service.IsLoggedAdmin())
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
