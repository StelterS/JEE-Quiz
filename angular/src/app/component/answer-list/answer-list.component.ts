import {Component, OnInit} from '@angular/core';
import {ExerciseService} from '../../service/exercise.service';
import {Observable} from 'rxjs';
import {AnswerService} from '../../service/answer.service';
import {Answer} from '../../model/answer';

@Component({
  selector: 'app-answer-list',
  templateUrl: './answer-list.component.html',
  styleUrls: ['./answer-list.component.css']
})
export class AnswerListComponent implements OnInit {

  answers: Observable<Answer[]>;

  constructor(private service: AnswerService) {
  }

  ngOnInit() {
    this.answers = this.service.findAllAnswers();
  }

  remove(answer: Answer) {
    this.service.removeAnswer(answer).subscribe(() => this.ngOnInit());
  }
}
