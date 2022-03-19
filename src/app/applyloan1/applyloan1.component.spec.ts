import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Applyloan1Component } from './applyloan1.component';

describe('Applyloan1Component', () => {
  let component: Applyloan1Component;
  let fixture: ComponentFixture<Applyloan1Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Applyloan1Component ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(Applyloan1Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
