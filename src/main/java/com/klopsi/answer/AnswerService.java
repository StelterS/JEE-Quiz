package com.klopsi.answer;

import com.klopsi.answer.model.Answer;
import com.klopsi.exercise.ExerciseService;
import com.klopsi.exercise.model.Exercise;
import lombok.NoArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@NoArgsConstructor
public class AnswerService {
	private final List<Answer> answers = new ArrayList<>();
	private ExerciseService exerciseService;

	@Inject
	public AnswerService(ExerciseService exerciseService) {
		this.exerciseService = exerciseService;
	}

	public synchronized List<Answer> findAllAnswers() {
		return answers.stream().map(Answer::new).collect(Collectors.toList());
	}

	public synchronized Answer findAnswer(int id) {
		return answers.stream().filter(answer -> answer.getId() == id).findFirst().map(Answer::new).orElse(null);
	}

	public void removeAnswer(Answer answer) {
		// remove from corresponding exercise
		exerciseService.deleteAnsFromCorrespondingExercises(answer);
		// remove answer from service aka "database"
		answers.removeIf(a -> a.equals(answer));
	}

	public synchronized void saveAnswer(Answer answer){
		if(answer.getId() != 0) {
			// remove from corresponding exercise
			exerciseService.deleteAnsFromCorrespondingExercises(answer);
			// change element
			answers.removeIf(a -> a.getId() == answer.getId());
			answers.add(new Answer(answer));
		}
		else {
			answer.setId(answers.stream().mapToInt(Answer::getId).max().orElse(0) + 1);
			answers.add(new Answer(answer));
		}
		// add answer to corresponding exercise
		exerciseService.addAnswerToExercise(answer);
	}

	// does not delete answer from corresponding exercise (one way deletion)
	public void deleteAnswerFromExercise(Exercise exercise) {
		answers.removeIf(answer -> exercise.getAnswers().stream().anyMatch(element -> element.getId() == answer.getId()));
	}

	public void addExerciseToAnswer(Exercise exercise) {
		for (Answer answer : answers) {
			if (answer.getExercise().getId() == exercise.getId()) {
				answer.setExercise(exercise);
			}
		}
	}

	public void deleteAnswerFromExerciseList(Answer answer) {
		exerciseService.deleteAnsFromCorrespondingExercises(answer);
	}

}
