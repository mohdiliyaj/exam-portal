package in.ashokit.service;

import java.util.List;

import in.ashokit.binding.AdminDashboard;
import in.ashokit.binding.Question;
import in.ashokit.entity.Category;
import in.ashokit.entity.Questions;
import in.ashokit.entity.StudentResponse;
import in.ashokit.entity.Subject;

public interface IAdminService {

	public Subject getSubjectByName(String name);

	public Subject saveSubject(Subject subject, Integer userId);

	public List<Subject> getAllSubjects();

	public Subject getBySubjectId(Integer subjectId);

	public Category getCategoryByName(String name);

	public Category saveCategory(Category category, Integer subjectId, Integer userId);

	public List<Category> getAllCategories();

	public Category getCategoryById(Integer categoryId);

	public Question saveQuestion(Question question, Integer userId, Integer categoryId) throws Exception;

	public Questions getQuestionByName(String name);

	public List<Question> getAllQuestions();

	public Question getByQuestionId(Integer questionId);
	
	public AdminDashboard buildAdminDashboard();
	
	public List<StudentResponse> viewAllStudentResponse();

}
