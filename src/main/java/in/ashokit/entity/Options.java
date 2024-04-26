package in.ashokit.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "options_master")
public class Options {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer optionId;
	private String optionValue;
	private Integer optionNumber;
	
	@ManyToOne
	@JoinColumn(name = "question_id")
	private Questions question;

	public Integer getOptionId() {
		return optionId;
	}

	public void setOptionId(Integer optionId) {
		this.optionId = optionId;
	}

	public String getOptionValue() {
		return optionValue;
	}

	public void setOptionValue(String optionValue) {
		this.optionValue = optionValue;
	}

	public Questions getQuestion() {
		return question;
	}

	public void setQuestion(Questions question) {
		this.question = question;
	}

	public Integer getOptionNumber() {
		return optionNumber;
	}

	public void setOptionNumber(Integer optionNumber) {
		this.optionNumber = optionNumber;
	}
}
