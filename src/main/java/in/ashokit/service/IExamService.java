package in.ashokit.service;

import java.util.List;

import in.ashokit.binding.ExamResponse;
import in.ashokit.entity.Question;

public interface IExamService {
	
	public List<Question> getAllQuestions();
	
	public boolean saveStudentExamResponse(Integer studentId, List<ExamResponse> selectedQuestions);
}
