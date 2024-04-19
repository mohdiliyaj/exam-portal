package in.ashokit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import in.ashokit.binding.LoginBinding;
import in.ashokit.entity.Student;
import in.ashokit.service.IStudentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class StudentController {

	private IStudentService studentService;

	public StudentController(IStudentService studentService) {
		this.studentService = studentService;
	}

	@GetMapping("/")
	public String loadLogin(Model model) {
		model.addAttribute("student", new LoginBinding());
		return "index";
	}

	@PostMapping("/login")
	public String handlingLogin(@ModelAttribute LoginBinding loginCredentials, HttpServletRequest req, Model model) {
		Student loginCheck = studentService.loginCheck(loginCredentials);
		if (loginCheck != null) {
			HttpSession session = req.getSession(true);
			session.setAttribute("studentId", loginCheck.getStudentId());
			return "redirect:/dashboard";
		} else {
			model.addAttribute("student", new LoginBinding());
			model.addAttribute("errMsg", "Invalid Credentials");
			return "index";
		}
	}

	@GetMapping("/dashboard")
	public String buildDashboard(HttpServletRequest req, Model model) {
		HttpSession session = req.getSession(false);
		if (session != null) {
			return "dashboard";
		}
		return "redirect:/logout";
	}

	@GetMapping("/profile")
	public String loadProfile(HttpServletRequest req, Model model) {
		HttpSession session = req.getSession(false);
		if (session != null) {
			Integer studentId = (Integer) session.getAttribute("studentId");
			Student student = studentService.getStudent(studentId);
			model.addAttribute("student", student);
			return "profile";
		}
		return "redirect:/logout";
	}

	@PostMapping("/profile")
	public String updateProfile(@ModelAttribute Student student, Model model) {
		Student updatedStudentDetails = studentService.updateStudentDetails(student);
		if (updatedStudentDetails != null) {
			model.addAttribute("succMsg", "Detail updated successfully");
		} else {
			model.addAttribute("errMsg", "Error occured while updating the details");
		}
		model.addAttribute("student", updatedStudentDetails);
		return "profile";
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest req, Model model) {
		HttpSession session = req.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		return "redirect:/";
	}

}
