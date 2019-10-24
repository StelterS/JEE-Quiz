import {Component, OnInit} from '@angular/core';
import {Exercise} from '../../model/exercise';
import {Difficulty} from '../../model/defficulty.enum';
import {ExerciseService} from '../../service/exercise.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-exercise-edit',
  templateUrl: './exercise-edit.component.html',
  styleUrls: ['./exercise-edit.component.css']
})
export class ExerciseEditComponent implements OnInit {

  exercise: Exercise;

  availableDifficulties = Difficulty;

  difficultyKeys: string[];

  constructor(private service: ExerciseService, private route: ActivatedRoute, private router: Router) {
  }

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id == null) {
      this.exercise = new Exercise();
    } else {
      this.service.findExercise(Number(id)).subscribe(exercise => this.exercise = exercise);
    }

    this.difficultyKeys = Object.keys(this.availableDifficulties).filter(k => !isNaN(Number(k)));
  }

  save() {
    this.service.saveExercise(this.exercise).subscribe(() => this.router.navigateByUrl('/exercises'));
  }
}
