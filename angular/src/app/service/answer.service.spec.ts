import { TestBed } from '@angular/core/testing';

import {AnswerService} from './answer.service';

describe('ExerciseService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: AnswerService = TestBed.get(AnswerService);
    expect(service).toBeTruthy();
  });
});
