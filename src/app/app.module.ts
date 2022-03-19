import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule ,rountingComponents} from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatSliderModule } from '@angular/material/slider'
;
import {MatToolbarModule, 
  } from '@angular/material/toolbar';
  import {MatButtonModule} from '@angular/material/button'
import { NavbarComponent } from './navbar/navbar.component';


import {ReactiveFormsModule} from '@angular/forms';
import { LoanidpageComponent } from './loanidpage/loanidpage.component';
import { Applyloan1Component } from './applyloan1/applyloan1.component';
import { HttpClientModule } from '@angular/common/http';
@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    rountingComponents,
    LoanidpageComponent,
    Applyloan1Component
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatSliderModule,
    MatToolbarModule,
    MatButtonModule,
    ReactiveFormsModule,    HttpClientModule,
    FormsModule

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
