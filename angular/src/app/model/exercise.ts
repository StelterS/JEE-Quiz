import {Difficulty} from './defficulty.enum';
import {Answer} from './answer';

export class Exercise {
  id: number;

  title: string;

  content: string;

  difficulty: Difficulty;

  maxPoints: number;

  answers: Answer[];
}
