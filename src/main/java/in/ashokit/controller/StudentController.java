package in.ashokit.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import in.ashokit.binding.ExamResponse;
import in.ashokit.binding.StudentDashboard;
import in.ashokit.entity.Category;
import in.ashokit.entity.Questions;
import in.ashokit.entity.Student;
import in.ashokit.entity.StudentResponse;
import in.ashokit.entity.User;
import in.ashokit.service.IAdminService;
import in.ashokit.service.IStudentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class StudentController {

	private IStudentService studentService;
	private IAdminService adminService;

	public StudentController(IStudentService studentService, IAdminService adminService) {
		this.studentService = studentService;
		this.adminService = adminService;
	}

	@GetMapping("/dashboard")
	public String loadDashboard(Model model, HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		if (session != null) {
			if (session.getAttribute("userType").equals("student")) {
				List<Category> allCategories = adminService.getAllCategories();
				User user = new User();
				user.setUserId((Integer) session.getAttribute("userId"));
				StudentDashboard studentDashboard = studentService.buildStudentDashboard(user);
				model.addAttribute("dashboard", studentDashboard);
				List<StudentResponse> userExamsByUserId = studentService.getUserExamsByUserId(user);
				model.addAttribute("categories", allCategories);
				model.addAttribute("exams", userExamsByUserId);
				return "dashboard";
			}
		}
		return "redirect:/logout";
	}

	@GetMapping("/category")
	public String loadExamBoard(@RequestParam("categoryId") Integer categoryId, HttpServletRequest req, Model model) {
		HttpSession session = req.getSession(false);
		if (session != null) {
			if (session.getAttribute("userType").equals("student")) {
				Category categoryById = adminService.getCategoryById(categoryId);
				List<Category> allCategories = adminService.getAllCategories();
				model.addAttribute("categories", allCategories);
				model.addAttribute("examId", categoryById.getCategoryId());
				model.addAttribute("category", categoryById);
				return "start-exam";
			}
		}
		return "redirect:/logout";
	}

	@GetMapping("/exam")
	public String startExam(@RequestParam("examId") Integer categoryId, HttpServletRequest req, Model model) {

		HttpSession session = req.getSession(false);
		if (session != null) {
			if (session.getAttribute("userType").equals("student")) {
				session.setAttribute("categoryId", categoryId);
				List<Questions> allQuestion = studentService.getAllQuestion(categoryId);
				model.addAttribute("timeLimit", allQuestion.size());
				model.addAttribute("questions", allQuestion);
				return "examination";
			}
		}
		return "redirect:/logout";
	}

	@PostMapping("/submitResponse")
	@ResponseBody
	public ResponseEntity<String> submitResponse(@RequestBody List<ExamResponse> selectedQuestions,
			HttpServletRequest req) {

		HttpSession session = req.getSession(false);
		Integer categoryId = (Integer) session.getAttribute("categoryId");
		studentService.validateAndSaveExam(selectedQuestions, categoryId, (Integer) session.getAttribute("userId"));
		session.removeAttribute("categoryId");
		return new ResponseEntity<>("student response successfully saved", HttpStatus.CREATED);
	}

	@GetMapping("/result")
	public String loadResultPage(Model model) {
		return "complete";
	}

	@GetMapping("/profile")
	public String loadProfile(HttpServletRequest req, Model model) {
		HttpSession session = req.getSession(false);
		if (session != null) {
			if (session.getAttribute("userType").equals("student")) {
				User user = new User();
				user.setUserId((Integer) session.getAttribute("userId"));
				Student studentByUserId = studentService.findStudentByUserId(user);
				model.addAttribute("student", studentByUserId);
				List<Category> allCategories = adminService.getAllCategories();
				model.addAttribute("categories", allCategories);
				return "profile";
			}
		}
		return "redirect:/logout";
	}

	@PostMapping("/update-profile")
	public String updateProfile(@ModelAttribute("student") Student student, HttpServletRequest req, Model model) {

		HttpSession session = req.getSession(false);
		if (session != null) {
			if (session.getAttribute("userType").equals("student")) {
				Student updateStudentDetails = studentService.updateStudentDetails(student);
				if (updateStudentDetails != null) {
					model.addAttribute("succMsg", "Profile details updated successfully");
					model.addAttribute("student", updateStudentDetails);
					List<Category> allCategories = adminService.getAllCategories();
					model.addAttribute("categories", allCategories);
					return "profile";
				}
				model.addAttribute("errMsg", "Error occured while updating the profile");
				model.addAttribute("student", student);
				List<Category> allCategories = adminService.getAllCategories();
				model.addAttribute("categories", allCategories);
				return "profile";
			}
		}
		return "redirect:/logout";
	}

}
