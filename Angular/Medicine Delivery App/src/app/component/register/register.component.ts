import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {


  public log_email:string = "";
  public log_password:string = "";
  public reg_email:string = "";
  public reg_password:string = "";
  public reg_confirm_password:string = "";
  login():void{

  }
  register():void{
    
  }

  constructor() { }

  ngOnInit(): void {
  }

}
