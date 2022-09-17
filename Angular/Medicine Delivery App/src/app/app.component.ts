import { Component } from '@angular/core';
import MedicineData from './_files/medicine.json';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'medicine-delivary';
  public medicine:{id:number,Type:string,name:string,Image:string,Dose:string,Dose_type:string,Brand:string, packing_Type:string,Price:string }[]=MedicineData;
}
