package com.klopsi.answer.events;

import com.klopsi.answer.model.Answer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnswerUpdate {
	private Answer answer;
}
