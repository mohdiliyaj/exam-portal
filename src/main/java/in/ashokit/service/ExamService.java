package in.ashokit.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import in.ashokit.binding.ExamResponse;
import in.ashokit.entity.Question;
import in.ashokit.repo.QuestionRepo;

@Service
public class ExamService implements IExamService {
	
	private QuestionRepo questionRepo;
	
	public ExamService(QuestionRepo questionRepo) {
		this.questionRepo = questionRepo;
	}
	
	@Override
	public List<Question> getAllQuestions(){
		return questionRepo.findAll();
	}
	
	public List<ExamResponse> getListResponses(){
	    return new ArrayList<ExamResponse>();
	}
	
}
