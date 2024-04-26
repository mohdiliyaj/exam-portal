package in.ashokit.binding;

public class Question {
	
	private Integer questionId;
	private String questionValue;
	private String optionOne;
	private String optionTwo;
	private String optionThree;
	private String optionFour;
	private Integer correctAnswer;
	private Integer categoryId;
	private String categoryName;
		
	public Integer getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}
	public String getQuestionValue() {
		return questionValue;
	}
	public void setQuestionValue(String questionValue) {
		this.questionValue = questionValue;
	}
	public String getOptionOne() {
		return optionOne;
	}
	public void setOptionOne(String optionOne) {
		this.optionOne = optionOne;
	}
	public String getOptionTwo() {
		return optionTwo;
	}
	public void setOptionTwo(String optionTwo) {
		this.optionTwo = optionTwo;
	}
	public String getOptionThree() {
		return optionThree;
	}
	public void setOptionThree(String optionThree) {
		this.optionThree = optionThree;
	}
	public String getOptionFour() {
		return optionFour;
	}
	public void setOptionFour(String optionFour) {
		this.optionFour = optionFour;
	}
	public Integer getCorrectAnswer() {
		return correctAnswer;
	}
	public void setCorrectAnswer(Integer correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}
