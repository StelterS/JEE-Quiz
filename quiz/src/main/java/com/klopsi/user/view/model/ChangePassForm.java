package com.klopsi.user.view.model;

import com.klopsi.user.validation.Password;
import com.klopsi.user.validation.PasswordGroup;
import com.klopsi.user.validation.RepeatedPasswords;
import lombok.*;

import javax.validation.constraints.Size;
import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@RepeatedPasswords(groups = PasswordGroup.class)
public class ChangePassForm implements Serializable {
	/**
	 * User password.
	 */
	@Size(min = 8, max = 20)
	@Password
	private String password;

	/**
	 * Repeated password.
	 */
	@Size(min = 8, max=20)
	@Password
	private String repeatPassword;

}
