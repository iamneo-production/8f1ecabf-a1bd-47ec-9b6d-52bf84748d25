import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoanServiceService {

  constructor(private http: HttpClient) { }
  createloandetails(loan1: Object): Observable<Object> {
    return this.http.post('http://localhost:8086/applyloaninsert', loan1);

}
getEmployeesList(): Observable<any> {
return this.http.get("http://localhost:8086/applyloanget");
}

}
