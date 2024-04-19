package in.ashokit.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import in.ashokit.binding.ExamResponse;
import in.ashokit.entity.Question;
import in.ashokit.service.IExamService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ExamController {

	private final IExamService examService;

	public ExamController(IExamService examService) {
		this.examService = examService;
	}

	@GetMapping("/test")
	public String loadTest(HttpServletRequest req, Model model) {
		HttpSession session = req.getSession(false);
		if (session != null) {
			List<Question> allQuestions = examService.getAllQuestions();
			model.addAttribute("questions", allQuestions);
			
			List<ExamResponse> examResponseList = new ArrayList<>();
			for (Question question : allQuestions) {
	            examResponseList.add(new ExamResponse(question.getQuestionId(), null)); // Initialize with question ID
	        }
			
			model.addAttribute("examResponseList", examResponseList);
			return "exam";
		}
		return "redirect:/logout";
	}

	@PostMapping("/submitResponse")
	public String submitResponse(@ModelAttribute("examResponseList") ArrayList<ExamResponse> examResponseList) {
		for (ExamResponse response : examResponseList) {
			System.out.println("Question ID: " + response.getQuestionId());
			System.out.println("Answer ID: " + response.getAnswerId());
		}
		return "redirect:/result";
	}
}
