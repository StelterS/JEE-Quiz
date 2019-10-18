import {Component, OnInit} from '@angular/core';
import {Exercise} from '../../model/exercise';
import {Answer} from '../../model/answer';
import {AnswerService} from '../../service/answer.service';
import {ExerciseService} from '../../service/exercise.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-answer-edit',
  templateUrl: './answer-edit.component.html',
  styleUrls: ['./answer-edit.component.css']
})
export class AnswerEditComponent implements OnInit {

  answer: Answer;

  availableExercises: Exercise[];

  constructor(private answerService: AnswerService, private exerciseService: ExerciseService,
              private route: ActivatedRoute, private router: Router) {
  }

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id == null) {
      this.answer = new Answer();
    } else {
      this.answerService.findAnswer(Number(id)).subscribe(answer => this.answer = answer);
    }

    this.exerciseService.findAllExercises().subscribe(exercises => this.availableExercises = exercises);
  }

  save() {
    this.answerService.saveAnswer(this.answer).subscribe(() => this.router.navigateByUrl('/answers'));
  }

  compareExercises(exercise1: Exercise, exercise2: Exercise): boolean {
    return exercise1 && exercise2 ? exercise1.id === exercise2.id : exercise1 === exercise2;
  }
}
