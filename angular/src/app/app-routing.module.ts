import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ExerciseListComponent} from './component/exercise-list/exercise-list.component';
import {AnswerListComponent} from './component/answer-list/answer-list.component';
import {ExerciseEditComponent} from './component/exercise-edit/exercise-edit.component';
import {AnswerEditComponent} from './component/answer-edit/answer-edit.component';

const routes: Routes = [
  {path: 'exercises', component: ExerciseListComponent},
  {path: 'answers', component: AnswerListComponent},
  {path: 'exercises/create', component: ExerciseEditComponent},
  {path: 'exercises/:id/edit', component: ExerciseEditComponent},
  {path: 'answers/create', component: AnswerEditComponent},
  {path: 'answers/:id/edit', component: AnswerEditComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
