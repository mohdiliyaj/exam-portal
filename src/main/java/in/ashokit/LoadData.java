package in.ashokit;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import in.ashokit.entity.Admin;
import in.ashokit.entity.Answer;
import in.ashokit.entity.Category;
import in.ashokit.entity.Options;
import in.ashokit.entity.Questions;
import in.ashokit.entity.Student;
import in.ashokit.entity.Subject;
import in.ashokit.entity.User;
import in.ashokit.repo.AdminRepo;
import in.ashokit.repo.AnswerRepo;
import in.ashokit.repo.CategoryRepo;
import in.ashokit.repo.OptionsRepo;
import in.ashokit.repo.QuestionsRepo;
import in.ashokit.repo.StudentRepo;
import in.ashokit.repo.SubjectRepo;
import in.ashokit.repo.UserRepo;

@Component
public class LoadData implements CommandLineRunner{
	
	private UserRepo userRepo;
	private AdminRepo adminRepo;
	private StudentRepo studentRepo;
	private SubjectRepo subjectRepo;
	private CategoryRepo categoryRepo;
	private QuestionsRepo questionRepo;
	private AnswerRepo answerRepo;
	private OptionsRepo optionsRepo;
	
	public LoadData(UserRepo userRepo, AdminRepo adminRepo,StudentRepo studentRepo, 
			SubjectRepo subjectRepo, CategoryRepo categoryRepo, QuestionsRepo questionRepo, 
			AnswerRepo answerRepo, OptionsRepo optionsRepo) {
		
		this.adminRepo = adminRepo;
		this.userRepo = userRepo;
		this.studentRepo = studentRepo;
		this.subjectRepo = subjectRepo;
		this.categoryRepo = categoryRepo;
		this.questionRepo = questionRepo;
		this.answerRepo = answerRepo;
		this.optionsRepo = optionsRepo;
	}
	
	public void run(String... args) throws Exception {
		
		User user1 = new User();
		user1.setName("iliyaj");
		user1.setEmail("iliyaj@gmail.com");
		user1.setPassword("iliyaj@123");
		user1.setUserRole("admin");
		
		User user2 = new User();
		user2.setName("rajesh");
		user2.setEmail("rajesh@gmail.com");
		user2.setPassword("rajesh@123");
		user2.setUserRole("student");
		
		List<User> saveAll = userRepo.saveAll(Arrays.asList(user1,user2));
		
		Admin admin = new Admin();
		admin.setEmail("iliyaj@gmail.com");
		admin.setName("iliyaj");
		admin.setPassword("iliyaj@123");
		admin.setPhoneNumber(9876543210L);
		admin.setUser(saveAll.get(0));
		
		adminRepo.save(admin);
		
		Student student = new Student();
		student.setName("rajesh");
		student.setEmail("rajesh@gmail.com");
		student.setAddress("123 main street");
		student.setGender("male");
		student.setDateOfBirth(LocalDate.of(2000, 4, 12));
		student.setPassword("rajesh@123");
		student.setPhoneNumber(9087654321L);
		student.setUser(saveAll.get(1));
		
		studentRepo.save(student);
		
		Subject subject = new Subject();
		subject.setSubjectName("Java");
		subject.setSubjectDesc("This subject is related to the Java");
		subject.setUser(saveAll.get(0));
		
		Subject savedSubject = subjectRepo.save(subject);
		
		Category category = new Category();
		category.setCategoryName("Java Strings");
		category.setCategoryDesc("This exam is related to the java strings");
		category.setUser(saveAll.get(0));
		category.setSubject(savedSubject);
		
		Category savedCategory = categoryRepo.save(category);
		
		Questions question1 = new Questions();
		question1.setCategory(savedCategory);
		question1.setQuestionValue("What does the length() method in Java's String class return?");
		question1.setUser(saveAll.get(0));

		Questions question2 = new Questions();
		question2.setCategory(savedCategory);
		question2.setQuestionValue("Which method is used to concatenate two or more strings in Java?");
		question2.setUser(saveAll.get(0));

		Questions question3 = new Questions();
		question3.setCategory(savedCategory);
		question3.setQuestionValue("What does the charAt() method in Java's String class return?");
		question3.setUser(saveAll.get(0));

		Questions question4 = new Questions();
		question4.setCategory(savedCategory);
		question4.setQuestionValue("Which of the following statements is true about Java strings?");
		question4.setUser(saveAll.get(0));

		Questions question5 = new Questions();
		question5.setCategory(savedCategory);
		question5.setQuestionValue("What does the toLowerCase() method do in Java's String class?");
		question5.setUser(saveAll.get(0));

		List<Questions> saveAll2 = questionRepo.saveAll(Arrays.asList(question1, question2, question3, question4, question5));
		
		Options option1Q1 = new Options();
		option1Q1.setOptionNumber(1);
		option1Q1.setOptionValue("The number of characters in the string");
		option1Q1.setQuestion(saveAll2.get(0));

		Options option2Q1 = new Options();
		option2Q1.setOptionNumber(2);
		option2Q1.setOptionValue("The size of the string in bytes");
		option2Q1.setQuestion(saveAll2.get(0));

		Options option3Q1 = new Options();
		option3Q1.setOptionNumber(3);
		option3Q1.setOptionValue("The number of words in the string");
		option3Q1.setQuestion(saveAll2.get(0));

		Options option4Q1 = new Options();
		option4Q1.setOptionNumber(4);
		option4Q1.setOptionValue("The ASCII value of the first character");
		option4Q1.setQuestion(saveAll2.get(0));

		// Question 2 Options
		Options option1Q2 = new Options();
		option1Q2.setOptionNumber(1);
		option1Q2.setOptionValue("join()");
		option1Q2.setQuestion(saveAll2.get(1));

		Options option2Q2 = new Options();
		option2Q2.setOptionNumber(2);
		option2Q2.setOptionValue("concat()");
		option2Q2.setQuestion(saveAll2.get(1));

		Options option3Q2 = new Options();
		option3Q2.setOptionNumber(3);
		option3Q2.setOptionValue("append()");
		option3Q2.setQuestion(saveAll2.get(1));

		Options option4Q2 = new Options();
		option4Q2.setOptionNumber(4);
		option4Q2.setOptionValue("merge()");
		option4Q2.setQuestion(saveAll2.get(1));

		// Question 3 Options
		Options option1Q3 = new Options();
		option1Q3.setOptionNumber(1);
		option1Q3.setOptionValue("The entire string");
		option1Q3.setQuestion(saveAll2.get(2));

		Options option2Q3 = new Options();
		option2Q3.setOptionNumber(2);
		option2Q3.setOptionValue("The character at the specified index");
		option2Q3.setQuestion(saveAll2.get(2));

		Options option3Q3 = new Options();
		option3Q3.setOptionNumber(3);
		option3Q3.setOptionValue("The Unicode value of the character");
		option3Q3.setQuestion(saveAll2.get(2));

		Options option4Q3 = new Options();
		option4Q3.setOptionNumber(4);
		option4Q3.setOptionValue("The ASCII value of the character");
		option4Q3.setQuestion(saveAll2.get(2));

		// Question 4 Options
		Options option1Q4 = new Options();
		option1Q4.setOptionNumber(1);
		option1Q4.setOptionValue("Strings are mutable objects in Java");
		option1Q4.setQuestion(saveAll2.get(3));

		Options option2Q4 = new Options();
		option2Q4.setOptionNumber(2);
		option2Q4.setOptionValue("Strings in Java can be directly compared using `==`");
		option2Q4.setQuestion(saveAll2.get(3));

		Options option3Q4 = new Options();
		option3Q4.setOptionNumber(3);
		option3Q4.setOptionValue("Strings can be modified using the `substring()` method");
		option3Q4.setQuestion(saveAll2.get(3));

		Options option4Q4 = new Options();
		option4Q4.setOptionNumber(4);
		option4Q4.setOptionValue("Strings in Java are implemented as arrays of characters");
		option4Q4.setQuestion(saveAll2.get(3));

		// Question 5 Options
		Options option1Q5 = new Options();
		option1Q5.setOptionNumber(1);
		option1Q5.setOptionValue("Converts the string to uppercase");
		option1Q5.setQuestion(saveAll2.get(4));

		Options option2Q5 = new Options();
		option2Q5.setOptionNumber(2);
		option2Q5.setOptionValue("Converts the string to lowercase");
		option2Q5.setQuestion(saveAll2.get(4));

		Options option3Q5 = new Options();
		option3Q5.setOptionNumber(3);
		option3Q5.setOptionValue("Checks if the string contains only lowercase characters");
		option3Q5.setQuestion(saveAll2.get(4));

		Options option4Q5 = new Options();
		option4Q5.setOptionNumber(4);
		option4Q5.setOptionValue("Converts the first character of the string to lowercase");
		option4Q5.setQuestion(saveAll2.get(4));
		
		List<Options> asList = Arrays.asList(option1Q1, option2Q1,option3Q1, option4Q1, option1Q2, option2Q2, option3Q2, option4Q2, option1Q3,option2Q3, option3Q3, option4Q3, option1Q4,option2Q4, option3Q4, option4Q4, option1Q5,option2Q5, option3Q5, option4Q5);
		optionsRepo.saveAll(asList);
		
		Answer answer1 = new Answer();
		answer1.setCorrectAnswer(1);
		answer1.setQuestion(saveAll2.get(0));
		
		Answer answer2 = new Answer();
		answer2.setCorrectAnswer(2);
		answer2.setQuestion(saveAll2.get(1));
		
		Answer answer3 = new Answer();
		answer3.setCorrectAnswer(2);
		answer3.setQuestion(saveAll2.get(2));
		
		Answer answer4 = new Answer();
		answer4.setCorrectAnswer(3);
		answer4.setQuestion(saveAll2.get(3));
		
		Answer answer5 = new Answer();
		answer5.setCorrectAnswer(2);
		answer5.setQuestion(saveAll2.get(4));
		
		List<Answer> asList2 = Arrays.asList(answer1, answer2, answer3, answer4, answer5);
		answerRepo.saveAll(asList2);
	}
}
