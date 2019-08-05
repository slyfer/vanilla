import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SideNavLayoutComponent } from './side-nav-layout/side-nav-layout.component';


const routes: Routes = [
  {path: '', component: SideNavLayoutComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
