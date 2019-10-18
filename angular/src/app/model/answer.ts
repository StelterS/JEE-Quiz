import {Exercise} from './exercise';

export class Answer {
  id: number;

  content: string;

  percent: number;

  submissionDate: Date;

  exercise: Exercise;
}
