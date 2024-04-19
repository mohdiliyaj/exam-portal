package in.ashokit.binding;

public class ExamResponse {
	
	private Integer questionId;
	private Integer answerId;

	public ExamResponse(Integer questionId, Integer answerId) {
		this.questionId = questionId;
		this.answerId = answerId;
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public Integer getAnswerId() {
		return answerId;
	}

	public void setAnswerId(Integer answerId) {
		this.answerId = answerId;
	}
}
