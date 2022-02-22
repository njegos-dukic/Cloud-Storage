import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TempComponent } from './components/temp/temp.component';
import { AuthGuard } from './guard/auth.guard';

const routes: Routes = [
  {
    path: 'hello',
    component: TempComponent,
    canActivate: [AuthGuard]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
