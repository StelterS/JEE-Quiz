package com.klopsi.exercise.model;

import com.klopsi.answer.model.Answer;
import com.klopsi.resource.model.Link;
import lombok.*;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@EqualsAndHashCode(exclude = {"links", "answers"})
@ToString(exclude = {"links", "answers"})
@Entity
@Table(name = "exercise")
@NamedQuery(name = Exercise.Queries.FIND_ALL, query = "select e from Exercise e")
@NamedQuery(name = Exercise.Queries.COUNT, query = "select count(e) from Exercise e")
@NamedQuery(name = Exercise.Queries.FIND_BY_DIFFICULTY, query = "select e from Exercise e where e.difficulty in :difficulties")
@NamedQuery(name = Exercise.Queries.FIND_BY_ID, query = "select e from Exercise e where e.id in :id")
@NamedEntityGraph(name = Exercise.Graphs.WITH_ANSWER,
		attributeNodes = {@NamedAttributeNode("answers")})
public class Exercise implements Serializable {

	public static class Graphs {
		public static final String WITH_ANSWER = "Exercise(Answer)";
	}

	public static class Queries {
		public static final String FIND_ALL = "Exercise.findAll";
		public static final String COUNT = "Exercise.count";
		public static final String FIND_BY_DIFFICULTY = "Author.findByDifficulty";
		public static final String FIND_BY_ID = "Author.findById";
	}

	@Id
	@GeneratedValue
	@Getter
	@Setter//for JSONB deserialization
	private Integer id;

	@Getter
	@Setter
	@NotBlank(message = "Title must be specified")
	private String title;

	@Getter
	@Setter
	@NotBlank(message = "Question to be answered must be specified")
	private String content;

	@Getter
	@Setter
	@NotNull
	@Enumerated(EnumType.STRING)
	private Difficulty difficulty;

	@Getter
	@Setter
	@Min(value = 0, message = "Value cannot be negative")
	@NotNull(message = "Maximum amount of points to get must be specified")
	private Integer maxPoints;

	@JsonbTransient
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exercise", cascade = CascadeType.REMOVE)	// answers are deleted with exercise
	@Getter
	@Setter
	private List<Answer> answers = new ArrayList<>();

	/**
	 * HATEOAS links.
	 */
	@JsonbProperty("_links")
	@Transient
	@Getter
	@Setter
	private Map<String, Link> links = new HashMap<>();

	public Exercise(String title, String content, Difficulty difficulty, Integer maxPoints) {
		this.title = title;
		this.content = content;
		this.difficulty = difficulty;
		this.maxPoints = maxPoints;
	}

}
