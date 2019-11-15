package com.klopsi.user.view.model;

import com.klopsi.user.validation.Password;
import com.klopsi.user.validation.PasswordGroup;
import com.klopsi.user.validation.RepeatedPasswords;
import com.klopsi.user.validation.UniqueLogin;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@RepeatedPasswords(groups = PasswordGroup.class)
public class RegistrationForm implements Serializable {
	/**
	 * User login.
	 */
	@Size(min = 3, max = 12)
	@UniqueLogin
	private String login;

	/**
	 * User password.
	 */
	@Size(min = 8, max = 20)
	@Password
	private String password;

	/**
	 * Repeated password.
	 */
	@Size(min = 8, max = 20)
	@Password
	private String repeatPassword;

	@NotBlank(message = "First name is required")
	private String firstName;

	@NotBlank(message = "Last name is required")
	private String lastName;

	@PastOrPresent(message = "Birthday date cannot be set in the future")
	@NotNull(message = "Birthday date must be provided")
	private LocalDate birthDate;
}
