import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-usernav',
  templateUrl: './usernav.component.html',
  styleUrls: ['./usernav.component.css']
})
export class UsernavComponent implements OnInit {

  constructor(private router:Router) { }

  ngOnInit(): void {
    
  }
  logout()
   {
     this.router.navigate(['/']);
   }

}
