package in.ashokit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import in.ashokit.binding.Login;
import in.ashokit.binding.Register;
import in.ashokit.entity.User;
import in.ashokit.service.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

	private IUserService userService;

	public UserController(IUserService userService) {
		this.userService = userService;
	}

	@GetMapping("/")
	public String loadLogin(Model model, HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		if(session != null) {
			session.invalidate();
		}
		model.addAttribute("student", new Login());
		return "index";
	}

	@PostMapping("/login")
	public String handlingLogin(@ModelAttribute Login loginCredentials, HttpServletRequest req, Model model) {		
		User loginCheck = userService.loginCheck(loginCredentials);
		if (loginCheck != null) {
			HttpSession session = req.getSession(true);
			session.setAttribute("userId", loginCheck.getUserId());
			session.setAttribute("userType", loginCheck.getUserRole());
			if (loginCheck.getUserRole().equals("admin")) {
				return "redirect:/admin-dashboard";
			} else {
				return "redirect:/dashboard";
			}
		} else {
			model.addAttribute("student", new Login());
			model.addAttribute("errMsg", "Invalid Credentials");
			return "index";
		}
	}

	@GetMapping("/register")
	public String loadRegistration(Model model) {
		model.addAttribute("register", new Register());
		return "register";
	}

	@PostMapping("/register")
	public String handleRegistration(@ModelAttribute Register register, Model model) {
		
		User userByEmail = userService.findUserByEmail(register.getEmail());
		if(userByEmail == null) {
			User saveStudent = userService.saveStudent(register);
			if (saveStudent.getUserId() != null) {
				model.addAttribute("succMsg", "Registration succesfully completed");
			} else {
				model.addAttribute("errMsg", "Error occured while registration");
			}
		}else {
			model.addAttribute("errMsg", "User already exists");
		}
		return "register";
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
