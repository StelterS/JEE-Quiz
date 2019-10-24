package com.klopsi.user;

import com.klopsi.answer.AnswerService;
import com.klopsi.answer.model.Answer;
import com.klopsi.user.model.User;
import lombok.NoArgsConstructor;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@NoArgsConstructor
public class UserService {
	private final List<User> users = new ArrayList<>();
	private AnswerService answerService;

	@Inject
	public UserService(AnswerService answerService){
		this.answerService = answerService;
	}

	@PostConstruct
	public void init(){
		users.add(new User(1, "Albert", "Einstein", LocalDate.of(1879, Month.MARCH, 14), List.of()));
		users.add(new User(2, "Maria", "Skłodowska-Curie", LocalDate.of(1867, Month.NOVEMBER, 7), List.of()));
		users.add(new User(3, "Isaac", "Newton", LocalDate.of(1643, Month.JANUARY, 4), List.of()));
		users.add(new User(4, "Stephen", "Hawking", LocalDate.of(1942, Month.JANUARY, 8), List.of()));
		users.add(new User(5, "Alan", "Turing", LocalDate.of(1912, Month.JUNE, 23), List.of()));
	}

	public synchronized List<User> findAllUsers() {
		return users.stream().map(User::new).collect(Collectors.toList());
	}

	public synchronized User findUser(int id) {
		return users.stream().filter(user -> user.getId() == id).findFirst().map(User::new).orElse(null);
	}

	public void removeUser(User user) {
		// remove all corresponding answers
		answerService.deleteUserAnswers(user);
		// remove user itself
		users.removeIf(u -> u.equals(user));
	}

	public synchronized void saveUser(User user){
		// initialize answers to empty list on addition
		if(user.getAnswers() == null){
			user.setAnswers(List.of());
		}

		if(user.getId() != 0) {
			// update exercise
			users.removeIf(u -> u.getId() == user.getId());
			users.add(new User(user));
			answerService.addUserToAnswer(user);
		}
		else {
			user.setId(users.stream().mapToInt(User::getId).max().orElse(0) + 1);
			users.add(new User(user));
		}
	}

	public void addAnswerToUser(Answer answer) {
		for(User user : users) {
			if (user.getId() == answer.getUser().getId()) {
				List<Answer> newAnswers = user.getAnswers().stream().map(Answer::new).collect(Collectors.toList());
				newAnswers.add(answer);
				user.setAnswers(newAnswers);
			}
		}
	}

	public void deleteAnsFromCorrespondingUser(Answer answer) {
		for (User user : users) {
			if (user.getId() == answer.getUser().getId()) {
				List<Answer> newAnswers = user.getAnswers().stream().map(Answer::new).collect(Collectors.toList());
				newAnswers.removeIf(a -> a.getId() == answer.getId());
				user.setAnswers(newAnswers);
			}
		}
	}


}
