package com.klopsi.answer.model;

import com.klopsi.exercise.model.Exercise;
import com.klopsi.resource.model.Link;
import com.klopsi.user.model.User;
import lombok.*;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.lang.annotation.Target;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Answer implements Serializable {

	private int id;

	@NotBlank
	private String content;

	@Min(0)
	@Max(100)
	@NotNull
	private Integer percent;

	private LocalDate submissionDate;

	@NotNull
	private Exercise exercise;

	@JsonbTransient
	public Exercise getExercise() {
		return this.exercise;
	}

	@NotNull
	private User user;

	@JsonbTransient
	public User getUser() {
		return this.user;
	}

	public Answer(Answer answer){
		this.id = answer.id;
		this.content = answer.content;
		this.exercise = answer.exercise;
		this.percent = answer.percent;
		this.submissionDate = answer.submissionDate;
		this.user = answer.user;
	}

	public Answer(int id, String content, Integer percent, LocalDate submissionDate, Exercise exercise, User user){
		this.id = id;
		this.content = content;
		this.exercise = exercise;
		this.percent = percent;
		this.submissionDate = submissionDate;
		this.user = user;
	}

	/**
	 * HATEOAS links.
	 */
	@JsonbProperty("_links")
	private Map<String, Link> links = new HashMap<>();
}
