import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule} from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { MatListModule, MatTabsModule } from '@angular/material';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { GiphyComponent } from './components/giphy.component';
import { GiphyService } from './giphyService';

const routes: Routes = [
  {path: '', component: AppComponent},
  {path: '**', redirectTo: '/', pathMatch: 'full'}
];


@NgModule({
  declarations: [
    AppComponent,
    GiphyComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    MatListModule,
    MatTabsModule,
    RouterModule.forRoot(routes)
  ],
  providers: [GiphyService],
  bootstrap: [AppComponent]
})
export class AppModule { }
