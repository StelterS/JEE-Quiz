import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Exercise} from '../model/exercise';

@Injectable()
export class ExerciseService {

  constructor(private http: HttpClient) {
  }

  findAllExercises(): Observable<Exercise[]> {
    return this.http.get<Exercise[]>('api/exercises');
  }

  findExercise(id: number): Observable<Exercise> {
    return this.http.get<Exercise>(`api/exercises/${id}`);
  }

  removeExercise(exercise: Exercise): Observable<any> {
    return this.http.delete(`api/exercises/${exercise.id}`);
  }

  saveExercise(exercise: Exercise): Observable<any> {
    if (exercise.id) {
      return this.http.put(`api/exercises/${exercise.id}`, exercise);
    } else {
      return this.http.post('api/exercises/', exercise);
    }
  }
}
