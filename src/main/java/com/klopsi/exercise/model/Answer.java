package com.klopsi.exercise.model;

import lombok.*;

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
	private String content;
	private Integer percent;
	private LocalDate submissionDate;
	private Exercise exercise;

	public Answer(Answer answer){
		this.id = answer.id;
		this.content = answer.content;
		this.exercise = answer.exercise;
		this.percent = answer.percent;
		this.submissionDate = answer.submissionDate;
	}
}
