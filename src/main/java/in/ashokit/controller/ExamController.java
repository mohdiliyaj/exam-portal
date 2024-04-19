package in.ashokit.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import in.ashokit.binding.ExamResponse;
import in.ashokit.entity.Question;
import in.ashokit.entity.Student;
import in.ashokit.service.IExamService;
import in.ashokit.service.IStudentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ExamController {

	private IExamService examService;
	private IStudentService studentService; 

	public ExamController(IExamService examService, IStudentService studentService) {
		this.examService = examService;
		this.studentService = studentService;
	}

	@GetMapping("/test")
	public String loadTest(HttpServletRequest req, Model model) {
		HttpSession session = req.getSession(false);
		if (session != null) {
			Integer studentId = (Integer)session.getAttribute("studentId");
			Student student = studentService.getStudent(studentId);
			if(!student.getIsCompleted()) {
				student.setIsCompleted(true);
				studentService.updateStudentDetails(student);
				List<Question> allQuestions = examService.getAllQuestions();
				model.addAttribute("questions", allQuestions);
				return "exam";
			}else {
				model.addAttribute("errMsg", "You are already completed the exam..!");
				return "dashboard";
			}
		}
		return "redirect:/logout";
	}

	@PostMapping("/submitResponse")
	@ResponseBody
	public ResponseEntity<String> submitResponse(@RequestBody List<ExamResponse> selectedQuestions, HttpServletRequest req) {
		
		HttpSession session = req.getSession(false);
		Integer studentId = (Integer) session.getAttribute("studentId");
		
		boolean saveStudentExamResponse = examService.saveStudentExamResponse(studentId, selectedQuestions);
		if(saveStudentExamResponse) {
			return new ResponseEntity<>("student response successfully saved", HttpStatus.CREATED);
		}else {
			return new ResponseEntity<>("failed to store the student response", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("/result")
	public String loadResultPage(Model model) {
		return "complete";
	}
}
