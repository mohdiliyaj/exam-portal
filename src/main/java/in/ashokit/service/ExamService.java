package in.ashokit.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import in.ashokit.binding.ExamResponse;
import in.ashokit.entity.Question;
import in.ashokit.entity.StudentResponse;
import in.ashokit.repo.QuestionRepo;
import in.ashokit.repo.StudentResponseRepo;

@Service
public class ExamService implements IExamService {
	
	private QuestionRepo questionRepo;
	private StudentResponseRepo studentResponseRepo;
	
	public ExamService(QuestionRepo questionRepo, StudentResponseRepo studentResponseRepo) {
		this.questionRepo = questionRepo;
		this.studentResponseRepo = studentResponseRepo;
	}
	
	@Override
	public List<Question> getAllQuestions(){
		return questionRepo.findAll();
	}
	
	@Override
	public boolean saveStudentExamResponse(Integer studentId, List<ExamResponse> selectedQuestions) {
		String resJson = "";
		ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            resJson = objectMapper.writeValueAsString(selectedQuestions);
        } catch (Exception e) {
            e.printStackTrace();
        }
		StudentResponse res = new StudentResponse();
		res.setStudentId(studentId);
		res.setResponses(resJson);
		
		StudentResponse save = studentResponseRepo.save(res);
		if(save != null) {
			return true;
		}
		return false;
	}
	
}
