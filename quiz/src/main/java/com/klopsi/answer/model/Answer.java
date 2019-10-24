package com.klopsi.answer.model;

import com.klopsi.exercise.model.Exercise;
import com.klopsi.user.model.User;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

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

	@NotNull
	private User user;

	public Answer(Answer answer){
		this.id = answer.id;
		this.content = answer.content;
		this.exercise = answer.exercise;
		this.percent = answer.percent;
		this.submissionDate = answer.submissionDate;
		this.user = answer.user;
	}
}
