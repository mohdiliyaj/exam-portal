package in.ashokit.service;

import java.util.List;

import org.springframework.web.server.ServerWebExchange;

import in.ashokit.binding.ExamResponse;
import in.ashokit.binding.StudentDashboard;
import in.ashokit.entity.Questions;
import in.ashokit.entity.Student;
import in.ashokit.entity.StudentResponse;
import in.ashokit.entity.User;
import jakarta.servlet.http.HttpServletRequest;

public interface IStudentService {
	
	public List<Questions> getAllQuestion(Integer categoryId);
	
	public boolean validateAndSaveExam(List<ExamResponse> examResponse, Integer categoryId, Integer userId);
	
	public List<StudentResponse> getUserExamsByUserId(User user);
	
	public StudentDashboard buildStudentDashboard(User user);
	
	public Student findStudentByUserId(User user);
	
	public Student updateStudentDetails(Student student);
	
	public Questions getQuestionByCategoryAndIndex(Integer categoryId, Integer index);
	
	public StudentResponse getUserExamResponseAfterSubmittingExam(User user);

	public byte[] generateResponsePdf(Integer responseId);
	
	public User findUserById(Integer userId);
}
