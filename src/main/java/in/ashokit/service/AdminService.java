package in.ashokit.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import in.ashokit.binding.AdminDashboard;
import in.ashokit.binding.Question;
import in.ashokit.entity.Answer;
import in.ashokit.entity.Category;
import in.ashokit.entity.Options;
import in.ashokit.entity.Questions;
import in.ashokit.entity.StudentResponse;
import in.ashokit.entity.Subject;
import in.ashokit.entity.User;
import in.ashokit.repo.AnswerRepo;
import in.ashokit.repo.CategoryRepo;
import in.ashokit.repo.OptionsRepo;
import in.ashokit.repo.QuestionsRepo;
import in.ashokit.repo.StudentResponseRepo;
import in.ashokit.repo.SubjectRepo;
import jakarta.transaction.Transactional;

@Service
public class AdminService implements IAdminService {

	private SubjectRepo subjectRepo;
	private CategoryRepo categoryRepo;
	private QuestionsRepo questionRepo;
	private AnswerRepo answerRepo;
	private OptionsRepo optionsRepo;
	private StudentResponseRepo studentResponseRepo;

	public AdminService(SubjectRepo subjectRepo, CategoryRepo categoryRepo, QuestionsRepo questionRepo, AnswerRepo answerRepo, OptionsRepo optionsRepo, StudentResponseRepo studentResponseRepo) {
		this.subjectRepo = subjectRepo;
		this.categoryRepo = categoryRepo;
		this.questionRepo = questionRepo;
		this.answerRepo = answerRepo;
		this.optionsRepo = optionsRepo;
		this.studentResponseRepo = studentResponseRepo;
	}

	@Override
	public Subject getSubjectByName(String name) {
		return subjectRepo.findBySubjectNameIgnoringCase(name);
	}

	@Override
	public Subject saveSubject(Subject subject, Integer userId) {
		User user = new User();
		user.setUserId(userId);
		subject.setUser(user);
		return subjectRepo.save(subject);
	}

	@Override
	public List<Subject> getAllSubjects() {
		return subjectRepo.findAll();
	}

	@Override
	public Subject getBySubjectId(Integer subjectId) {
		return subjectRepo.findById(subjectId).get();
	}

	@Override
	public Category getCategoryByName(String name) {
		return categoryRepo.findByCategoryNameIgnoringCase(name);
	}

	@Override
	public Category saveCategory(Category category, Integer subjectId, Integer userId) {
		Subject subject = new Subject();
		subject.setSubjectId(subjectId);
		User user = new User();
		user.setUserId(userId);
		category.setSubject(subject);
		category.setUser(user);
		return categoryRepo.save(category);
	}

	@Override
	public List<Category> getAllCategories() {
		return categoryRepo.findAll();
	}

	@Override
	public Category getCategoryById(Integer categoryId) {
		return categoryRepo.findById(categoryId).get();
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public Question saveQuestion(Question question, Integer userId, Integer categoryId) throws Exception{
		if (question.getQuestionId() == null) {
			User user = new User();
			user.setUserId(userId);
			Category category = new Category();
			category.setCategoryId(categoryId);
			Questions questions = new Questions();
			questions.setQuestionValue(question.getQuestionValue());
			questions.setUser(user);
			questions.setCategory(category);
			Questions save = questionRepo.save(questions);
			if (save.getQuestionId() == null) {
				throw new Exception("error occured while saving the record");
			}

			Answer answer = new Answer();
			answer.setCorrectAnswer(question.getCorrectAnswer());
			answer.setQuestion(save);
			Answer save2 = answerRepo.save(answer);
			if (save2.getAnswerId() == null) {
				throw new Exception("error occured while saving the record");
			}

			Map<Integer, String> map = new HashMap<>();
			map.put(1, question.getOptionOne());
			map.put(2, question.getOptionTwo());
			map.put(3, question.getOptionThree());
			map.put(4, question.getOptionFour());

			Set<Integer> keySet = map.keySet();
			keySet.forEach(e -> {
				try {
					Options option = new Options();
					option.setOptionNumber(e);
					option.setOptionValue(map.get(e));
					option.setQuestion(save);
					Options save3 = optionsRepo.save(option);
					if (save3.getOptionId() == null) {
						throw new Exception("error occured while saving the record");
					}
				} catch (Exception x) {
					x.printStackTrace();
				}
			});
			question.setQuestionId(save.getQuestionId());
			return question;
		} else {

			Questions questions = questionRepo.findById(question.getQuestionId()).get();
			if (question.getQuestionValue() != questions.getQuestionValue()) {
				questions.setQuestionValue(question.getQuestionValue());
				questions = questionRepo.save(questions);
			}

			Answer answer = questions.getAnswer();
			if (question.getCorrectAnswer() != answer.getCorrectAnswer()) {
				answer.setCorrectAnswer(question.getCorrectAnswer());
				answer = answerRepo.save(answer);
			}

			List<Options> options = questions.getOptions();
			if (options.get(0).getOptionValue() != question.getOptionOne()) {
				Options options2 = options.get(0);
				options2.setOptionValue(question.getOptionOne());
				optionsRepo.save(options2);
			}
			if (options.get(1).getOptionValue() != question.getOptionTwo()) {
				Options options2 = options.get(1);
				options2.setOptionValue(question.getOptionTwo());
				optionsRepo.save(options2);
			}
			if (options.get(2).getOptionValue() != question.getOptionThree()) {
				Options options2 = options.get(2);
				options2.setOptionValue(question.getOptionThree());
				optionsRepo.save(options2);
			}
			if (options.get(3).getOptionValue() != question.getOptionFour()) {
				Options options2 = options.get(3);
				options2.setOptionValue(question.getOptionFour());
				optionsRepo.save(options2);
			}

			question.setQuestionId(questions.getQuestionId());
			Category category = categoryRepo.findById(categoryId).get();
			question.setCategoryName(category.getCategoryName());
			return question;
		}
	}

	@Override
	public Questions getQuestionByName(String question) {
		return questionRepo.findByQuestionValue(question);
	}

	@Override
	public List<Question> getAllQuestions() {
		List<Questions> all = questionRepo.findAll();
		List<Question> questions = new ArrayList<>();
		all.forEach(e -> {
			Question question = new Question();
			question.setQuestionId(e.getQuestionId());
			question.setQuestionValue(e.getQuestionValue());
			question.setCategoryId(e.getCategory().getCategoryId());
			question.setCategoryName(e.getCategory().getCategoryName());
			question.setCorrectAnswer(e.getAnswer().getCorrectAnswer());
			question.setOptionOne(e.getOptions().get(0).getOptionValue());
			question.setOptionTwo(e.getOptions().get(1).getOptionValue());
			question.setOptionThree(e.getOptions().get(2).getOptionValue());
			question.setOptionFour(e.getOptions().get(3).getOptionValue());
			questions.add(question);
		});
		return questions;
	}

	@Override
	public Question getByQuestionId(Integer questionId) {

		Optional<Questions> byId = questionRepo.findById(questionId);
		if (byId.isPresent()) {
			Questions questions = byId.get();
			Question question = new Question();
			question.setCategoryId(questions.getCategory().getCategoryId());
			question.setCategoryName(questions.getCategory().getCategoryName());
			question.setCorrectAnswer(questions.getAnswer().getCorrectAnswer());
			question.setQuestionId(questions.getQuestionId());
			question.setQuestionValue(questions.getQuestionValue());
			question.setOptionOne(questions.getOptions().get(0).getOptionValue());
			question.setOptionTwo(questions.getOptions().get(1).getOptionValue());
			question.setOptionThree(questions.getOptions().get(2).getOptionValue());
			question.setOptionFour(questions.getOptions().get(3).getOptionValue());
			return question;
		}
		return null;
	}
	
	@Override
	public AdminDashboard buildAdminDashboard() {
		
		AdminDashboard dashboard = new AdminDashboard();
		dashboard.setNoOfSubjects(subjectRepo.findAll().size());
		dashboard.setNoOfCategories(categoryRepo.findAll().size());
		dashboard.setTotalAttempts(studentResponseRepo.findAll().size());
		dashboard.setPassedAttempts(studentResponseRepo.findByExamStatus(true).size());		
		return dashboard;
	}
	
	@Override
	public List<StudentResponse> viewAllStudentResponse() {
		return studentResponseRepo.findAll();
	}
	
}
