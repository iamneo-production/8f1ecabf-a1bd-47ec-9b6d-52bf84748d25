import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoanidpageComponent } from './loanidpage.component';

describe('LoanidpageComponent', () => {
  let component: LoanidpageComponent;
  let fixture: ComponentFixture<LoanidpageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LoanidpageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LoanidpageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
