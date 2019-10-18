import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './component/app/app.component';
import {AppRoutingModule} from './app-routing.module';
import {ExerciseListComponent} from './component/exercise-list/exercise-list.component';
import {ExerciseEditComponent} from './component/exercise-edit/exercise-edit.component';
import {AnswerListComponent} from './component/answer-list/answer-list.component';
import {AnswerService} from './service/answer.service';
import {ExerciseService} from './service/exercise.service';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule} from '@angular/forms';
import {AnswerEditComponent} from './component/answer-edit/answer-edit.component';

@NgModule({
  declarations: [
    AppComponent,
    ExerciseListComponent,
    ExerciseEditComponent,
    AnswerEditComponent,
    AnswerListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [
    ExerciseService,
    AnswerService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
