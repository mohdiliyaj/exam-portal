package in.ashokit.service;

import java.util.List;

import in.ashokit.binding.AdminDashboard;
import in.ashokit.binding.Question;
import in.ashokit.entity.Category;
import in.ashokit.entity.Questions;
import in.ashokit.entity.StudentResponse;
import in.ashokit.entity.Subject;
import in.ashokit.entity.User;

public interface IAdminService {

	public Subject getSubjectByName(String name);

	public Subject saveSubject(Subject subject, Integer userId);

	public List<Subject> getAllSubjects();
	
	public List<Subject> getAllFilteredSubjects(Subject s);

	public Subject getBySubjectId(Integer subjectId);

	public Category getCategoryByName(String name);

	public Category saveCategory(Category category, Integer subjectId, Integer userId);

	public List<Category> getAllCategories();
	
	public List<Category> getAllFilteredCategories(Category c);
 
	public Category getCategoryById(Integer categoryId);

	public Question saveQuestion(Question question, Integer userId, Integer categoryId) throws Exception;

	public Questions getQuestionByName(String name);

	public List<Question> getAllQuestions();
	
	public List<Question> getAllFilteredQuestions(Questions q);

	public Question getByQuestionId(Integer questionId);
	
	public AdminDashboard buildAdminDashboard();
	
	public List<StudentResponse> viewAllStudentResponse();
	
	public List<StudentResponse> viewAllFilteredStudentResponses(User user);

}
