package in.ashokit.service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.exceptions.IOException;
import com.itextpdf.io.source.ByteArrayOutputStream;

import in.ashokit.binding.ExamResponse;
import in.ashokit.binding.StudentDashboard;
import in.ashokit.binding.StudentExamResponse;
import in.ashokit.entity.Category;
import in.ashokit.entity.Questions;
import in.ashokit.entity.Student;
import in.ashokit.entity.StudentResponse;
import in.ashokit.entity.User;
import in.ashokit.repo.CategoryRepo;
import in.ashokit.repo.QuestionsRepo;
import in.ashokit.repo.StudentRepo;
import in.ashokit.repo.StudentResponseRepo;
import in.ashokit.repo.UserRepo;
import jakarta.transaction.Transactional;

@Service
public class StudentService implements IStudentService {

	private QuestionsRepo questionRepo;
	private StudentResponseRepo studentResponseRepo;
	private StudentRepo studentRepo;
	private CategoryRepo categoryRepo;
	private UserRepo userRepo;
	private final TemplateEngine templateEngine;
	
	public StudentService(QuestionsRepo questionRepo, StudentResponseRepo studentResponseRepo,
			StudentRepo studentRepo,CategoryRepo categoryRepo, TemplateEngine templateEngine, 
			UserRepo userRepo) {
		this.questionRepo = questionRepo;
		this.studentResponseRepo = studentResponseRepo;
		this.studentRepo = studentRepo;
		this.categoryRepo = categoryRepo;
		this.templateEngine = templateEngine;
		this.userRepo = userRepo;
	}

	@Override
	public List<Questions> getAllQuestion(Integer categoryId) {
		Category category = new Category();
		category.setCategoryId(categoryId);
		return questionRepo.findByCategory(category);
	}
	
	@Override
	public Questions getQuestionByCategoryAndIndex(Integer categoryId, Integer index) {
		Category orElse = categoryRepo.findById(categoryId).orElse(null);
		List<Questions> byCategory = questionRepo.findByCategory(orElse);
		if(index >= 0 && index < byCategory.size()) {
			return byCategory.get(index);
		}else {
			return null;
		}
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public boolean validateAndSaveExam(List<ExamResponse> examResponse, Integer ctaegoryId, Integer userId) {

		Integer marks = 0;
		String studentResponseInJson = "";

		Map<Integer, Integer> response = new HashMap<>();
		examResponse.forEach(e -> {
			response.put(e.getQuestionId(), e.getAnswerId());
		});

		List<Questions> allQuestion = getAllQuestion(ctaegoryId);
		Map<Integer, Integer> questions = new HashMap<>();
		allQuestion.forEach(e -> {
			questions.put(e.getQuestionId(), e.getAnswer().getCorrectAnswer());
		});

		Set<Integer> keySet = questions.keySet();
		for (Integer i : keySet) {
			if (questions.get(i).equals(response.get(i))) {
				marks++;
			}
		}

		try {
			studentResponseInJson = convertStudentResponseInJson(examResponse);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		Category category = new Category();
		category.setCategoryId(ctaegoryId);

		User user = new User();
		user.setUserId(userId);

		StudentResponse studentResponse = new StudentResponse();
		studentResponse.setCategory(category);
		studentResponse.setUser(user);
		studentResponse.setResponses(studentResponseInJson);
		studentResponse.setScoredMarks(marks);
		studentResponse.setSubmittedTime(LocalDateTime.now());
		Double percentage = ((double) marks / (double) keySet.size()) * 100;
		studentResponse.setPercentage(percentage);
		Boolean examStatus = percentage > 70 ? true : false;
		studentResponse.setExamStatus(examStatus);

		StudentResponse save = studentResponseRepo.save(studentResponse);
		if (save != null) {
			return true;
		}
		return false;

	}

	private String convertStudentResponseInJson(List<ExamResponse> examResponse) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		return objectMapper.writeValueAsString(examResponse);
	}

	@Override
	public List<StudentResponse> getUserExamsByUserId(User user) {
		List<StudentResponse> byUserId = studentResponseRepo.findByUser(user);
		Collections.reverse(byUserId);
		if (byUserId.size() < 8) {
			return byUserId;
		}
		return new ArrayList<>(byUserId.subList(0, 8));
	}
	
	@Override
	public StudentResponse getUserExamResponseAfterSubmittingExam(User user) {
		List<StudentResponse> byUserId = studentResponseRepo.findByUser(user);
		Collections.reverse(byUserId);
		return byUserId.get(0);
	}

	@Override
	public StudentDashboard buildStudentDashboard(User user) {
		List<StudentResponse> byUserId = studentResponseRepo.findByUser(user);
		StudentDashboard dashboard = new StudentDashboard();
		dashboard.setNoOfExams(byUserId.size());
		long passedExams = byUserId.stream().filter(e -> e.getExamStatus().equals(true)).count();
		dashboard.setPassedExams(passedExams);
		long failedExams = byUserId.stream().filter(e -> e.getExamStatus().equals(false)).count();
		dashboard.setFailedExams(failedExams);
		double overAllPercentage = byUserId.stream().mapToDouble(StudentResponse::getPercentage).average().orElse(0.0);
		DecimalFormat decimalFormat = new DecimalFormat("#.##");
		String formattedPercentage = decimalFormat.format(overAllPercentage);
		dashboard.setOverAllPercentage(formattedPercentage);
		return dashboard;
	}

	@Override
	public Student findStudentByUserId(User user) {
		return studentRepo.findByUser(user);
	}

	@Override
	public Student updateStudentDetails(Student updateStudent) {
		Optional<Student> byId = studentRepo.findById(updateStudent.getStudentId());
		if (byId.isPresent()) {
			Student student = byId.get();
			if (!student.getName().equals(updateStudent.getName())) {
				student.setName(updateStudent.getName());
			}
			if (!student.getPhoneNumber().equals(updateStudent.getPhoneNumber())) {
				student.setPhoneNumber(updateStudent.getPhoneNumber());
			}
			if (!student.getAddress().equals(updateStudent.getAddress())) {
				student.setAddress(updateStudent.getAddress());
			}
			if (!student.getPassword().equals(updateStudent.getPassword()) && !updateStudent.getPassword().equals("")) {
				student.setPassword(updateStudent.getPassword());
			}
			if (!student.getGender().equals(updateStudent.getGender())) {
				student.setGender(updateStudent.getGender());
			}
			Student save = studentRepo.save(student);
			return save;
		}
		return null;
	}
	
	@Override
	public byte[] generateResponsePdf(Integer responseId) {
		Optional<StudentResponse> byId = studentResponseRepo.findById(responseId);
		if(byId.isEmpty()) {
			return null;
		}
		StudentResponse studentResponse = byId.get();
		Optional<User> byId2 = userRepo.findById(studentResponse.getUser().getUserId());
		Optional<Category> byId3 = categoryRepo.findById(studentResponse.getCategory().getCategoryId());
		if(byId2.isEmpty() || byId3.isEmpty()) {
			return null;
		}
		
		List<Questions> allQuestion = getAllQuestion(byId3.get().getCategoryId());
		
		Context context = new Context();
		context.setVariable("studentResponse", studentResponse);
		context.setVariable("user", byId2.get());
		context.setVariable("category", byId3.get());
		context.setVariable("questions", allQuestion);
		
		String responses = studentResponse.getResponses();
		ObjectMapper mapper = new ObjectMapper();
		List<StudentExamResponse> list = null;
		try {
			list = mapper.readValue(responses, new TypeReference<List<StudentExamResponse>>(){});
		}catch(Exception e) {
			e.printStackTrace();
		}
		context.setVariable("studentExamResponse", list);
		String process = templateEngine.process("student-result", context);
		
		ByteArrayOutputStream target = new ByteArrayOutputStream();
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setBaseUri("http://localhost:8080");
        try {
            HtmlConverter.convertToPdf(process, target, converterProperties);
            return target.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
	}
	
	@Override
	public User findUserById(Integer userId) {
		Optional<User> byId = userRepo.findById(userId);
		if(byId.isPresent()) {
			return byId.get();
		}
		return null;
	}
}
