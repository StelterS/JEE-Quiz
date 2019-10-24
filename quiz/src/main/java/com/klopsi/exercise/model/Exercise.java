package com.klopsi.exercise.model;

import com.klopsi.answer.model.Answer;
import com.klopsi.resource.model.Link;
import lombok.*;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Exercise implements Serializable {

	private int id;

	@NotBlank
	private String title;

	@NotBlank
	private String content;

	private Difficulty difficulty;

	@Min(0)
	@NotNull
	private Integer maxPoints;

	@JsonbTransient
	private List<Answer> answers = new ArrayList<>();

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Exercise exercise = (Exercise) o;
		return id == exercise.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public Exercise(int id, String title, String content, Difficulty difficulty, Integer maxPoints, List<Answer> answers) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.difficulty = difficulty;
		this.maxPoints = maxPoints;
		this.answers = answers;
	}

	public Exercise(Exercise exercise){
		this.id = exercise.id;
		this.title = exercise.title;
		this.content = exercise.content;
		this.difficulty = exercise.difficulty;
		this.maxPoints = exercise.maxPoints;
		this.answers = exercise.answers;
	}

	@JsonbProperty("_links")
	private Map<String, Link> links = new HashMap<>();

}
