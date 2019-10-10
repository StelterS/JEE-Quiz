package com.klopsi.exercise.model;

import com.klopsi.exercise.model.Answer;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Exercise implements Serializable {
	private int id;
	private String title;
	private String content;
	private Difficulty difficulty;
	private Integer maxPoints;
	private List<Answer> answers;

	public Exercise(Exercise exercise){
		this.id = exercise.id;
		this.title = exercise.title;
		this.content = exercise.content;
		this.difficulty = exercise.difficulty;
		this.maxPoints = exercise.maxPoints;
		this.answers = exercise.answers;
	}

}
