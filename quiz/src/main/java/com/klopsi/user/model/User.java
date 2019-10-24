package com.klopsi.user.model;

import com.klopsi.answer.model.Answer;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class User implements Serializable {
	private int id;

	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;

	private LocalDate birthDate;

	@NotNull
	private List<Answer> answers;

	public User(User user) {
		this.id = user.id;
		this.firstName = user.firstName;
		this.lastName = user.lastName;
		this.birthDate = user.birthDate;
		this.answers = user.answers;
	}
}
