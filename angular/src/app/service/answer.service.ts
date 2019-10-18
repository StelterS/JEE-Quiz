import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Answer} from '../model/answer';

@Injectable()
export class AnswerService {

  constructor(private http: HttpClient) {
  }

  findAllAnswers(): Observable<Answer[]> {
    return this.http.get<Answer[]>('api/answers');
  }

  findAnswer(id: number): Observable<Answer> {
    return this.http.get<Answer>(`api/answers/${id}`);
  }

  removeAnswer(exercise: Answer): Observable<any> {
    return this.http.delete(`api/answers/${exercise.id}`);
  }

  saveAnswer(exercise: Answer): Observable<any> {
    if (exercise.id) {
      return this.http.put(`api/answers/${exercise.id}`, exercise);
    } else {
      return this.http.post('api/answers/', exercise);
    }
  }
}
