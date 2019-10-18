import {Component, OnInit} from '@angular/core';
import {ExerciseService} from '../../service/exercise.service';
import {Observable} from 'rxjs';
import {Exercise} from '../../model/exercise';

@Component({
  selector: 'app-exercise-list',
  templateUrl: './exercise-list.component.html',
  styleUrls: ['./exercise-list.component.css']
})
export class ExerciseListComponent implements OnInit {

  exercises: Observable<Exercise[]>;

  constructor(private service: ExerciseService) {
  }

  ngOnInit() {
    this.exercises = this.service.findAllExercises();
  }

  remove(exercise: Exercise) {
    this.service.removeExercise(exercise).subscribe(() => this.ngOnInit());
  }
}
