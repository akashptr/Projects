import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AboutusComponent } from './component/aboutus/aboutus.component';
import { CartComponent } from './component/cart/cart.component';
import { ContactComponent } from './component/contact/contact.component';
import { ProductsComponent } from './component/products/products.component';
import { RegisterComponent } from './component/register/register.component';

const routes: Routes = [
  {path:'',component:ProductsComponent},
 { path:'contact',component:ContactComponent},
 {path:'aboutus',component:AboutusComponent},
 {path:'products',component:ProductsComponent},
 {path:'cart',component:CartComponent},
 {path:'register',component:RegisterComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
