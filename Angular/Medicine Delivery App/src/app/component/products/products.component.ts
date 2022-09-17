import { Component, OnInit } from '@angular/core';
import MedicineData from 'src/app/_files/medicine.json';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }
  public medicine:{id:number,Type:string,name:string,Image:string,Dose:string,Dose_type:string,Brand:string, packing_Type:string,Price:string }[]=MedicineData;

}
