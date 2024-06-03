package in.ashokit.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.ashokit.binding.AdminDashboard;
import in.ashokit.binding.Question;
import in.ashokit.entity.Category;
import in.ashokit.entity.Questions;
import in.ashokit.entity.StudentResponse;
import in.ashokit.entity.Subject;
import in.ashokit.entity.User;
import in.ashokit.service.IAdminService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

	private IAdminService adminService;

	public AdminController(IAdminService adminService) {
		this.adminService = adminService;
	}

	@GetMapping("/admin-dashboard")
	public String loadDashboard(HttpServletRequest req, Model model) {
		HttpSession session = req.getSession(false);
		if (session != null && session.getAttribute("userType").equals("admin")) {
			AdminDashboard adminDashboard = adminService.buildAdminDashboard();
			model.addAttribute("dashboard", adminDashboard);
			return "admin-dashboard";
		}
		return "redirect:/logout";
	}

	@GetMapping("/add-subject")
	public String loadAddSubject(HttpServletRequest req, Model model) {
		HttpSession session = req.getSession(false);
		if (session != null && session.getAttribute("userType").equals("admin")) {
			model.addAttribute("subject", new Subject());
			return "add-subject";
		}
		return "redirect:/logout";
	}

	@PostMapping("/add-subject")
	public String addSubject(@ModelAttribute Subject subject, HttpServletRequest req, Model model) {
		HttpSession session = req.getSession(false);
		if (session != null) {
			if (session.getAttribute("userType").equals("admin")) {
				if (subject.getSubjectId() != null) {
					Subject saveSubject = adminService.saveSubject(subject, (Integer) session.getAttribute("userId"));
					if (saveSubject != null) {
						model.addAttribute("succMsg",
								saveSubject.getSubjectName() + " subject details updated successfully");
						return "add-subject";
					}
					model.addAttribute("errMsg", "Error occured while updated subject details");
					return "add-subject";

				} else {
					Subject subjectByName = adminService.getSubjectByName(subject.getSubjectName());
					if (subjectByName == null) {
						Subject saveSubject = adminService.saveSubject(subject,
								(Integer) session.getAttribute("userId"));
						if (saveSubject.getSubjectId() != null) {
							model.addAttribute("succMsg", saveSubject.getSubjectName() + " Subject Added Successfully");
							model.addAttribute("subject", new Subject());
							return "add-subject";
						}
						model.addAttribute("errMsg", "failed to add the subject");
						return "add-subject";
					}
					model.addAttribute("errMsg", "Subject already exits");
					return "add-subject";
				}
			}
		}
		return "redirect:/logout";
	}

	@GetMapping("/view-subjects")
	public String showAllSubjects(HttpServletRequest req, Model model) {
		HttpSession session = req.getSession(false);
		if (session != null) {
			if (session.getAttribute("userType").equals("admin")) {
				List<Subject> allSubjects = adminService.getAllSubjects();
				Subject subject = new Subject();
				model.addAttribute("allSubjects", allSubjects);
				model.addAttribute("subject", subject);
				return "view-subjects";
			}
		}
		return "redirect:/logout";
	}
	
	@PostMapping("/filtered-subjects")
	public String showFilteredSubjects(@ModelAttribute("subject") Subject s, HttpServletRequest req, Model model) {
		HttpSession session = req.getSession(false);
		if(session != null) {
			if (session.getAttribute("userType").equals("admin")) {
				List<Subject> allFilteredSubjects = adminService.getAllFilteredSubjects(s);
				model.addAttribute("allSubjects", allFilteredSubjects);
				model.addAttribute("subject", s);
				return "filtered-subjects";
			}
		}
		return "redirect:/logout";
	}

	@GetMapping("/edit-subject")
	public String editSubject(@RequestParam("subjectId") Integer subjectId, HttpServletRequest req, Model model) {
		HttpSession session = req.getSession(false);
		if (session != null) {
			if (session.getAttribute("userType").equals("admin")) {
				Subject bySubjectId = adminService.getBySubjectId(subjectId);
				model.addAttribute("subject", bySubjectId);
				return "add-subject";
			}
		}
		return "redirect:/logout";
	}

	@GetMapping("/add-category")
	public String loadAddCategory(HttpServletRequest req, Model model) {
		HttpSession session = req.getSession(false);
		if (session != null) {
			if (session.getAttribute("userType").equals("admin")) {
				List<Subject> allSubjects = adminService.getAllSubjects();
				model.addAttribute("allSubjects", allSubjects);
				model.addAttribute("category", new Category());
				return "add-category";
			}
		}
		return "redirect:/logout";
	}

	@PostMapping("/add-category")
	public String addCategory(@ModelAttribute("category") Category category,
			@RequestParam("selectedSubjectId") Integer subjectId, HttpServletRequest req, Model model) {

		HttpSession session = req.getSession(false);
		if (session != null) {
			if (session.getAttribute("userType").equals("admin")) {
				if (category.getCategoryId() != null) {

					Category saveCategory = adminService.saveCategory(category, subjectId,
							(Integer) session.getAttribute("userId"));
					if (saveCategory != null) {
						model.addAttribute("succMsg",
								saveCategory.getCategoryName() + " Category details updated successfully");
						model.addAttribute("category", saveCategory);
						return "add-category";
					}
					model.addAttribute("errMsg", "Error occured while updating the category");
					model.addAttribute("category", category);
					return "add-category";

				} else {
					Category categoryByName = adminService.getCategoryByName(category.getCategoryName());
					List<Subject> allSubjects = adminService.getAllSubjects();
					if (categoryByName == null) {
						Category saveCategory = adminService.saveCategory(category, subjectId,
								(Integer) session.getAttribute("userId"));
						if (saveCategory != null) {
							model.addAttribute("succMsg",
									saveCategory.getCategoryName() + " Category Saved successfully");
							model.addAttribute("allSubjects", allSubjects);
							model.addAttribute("category", new Category());
							return "add-category";
						}
						model.addAttribute("errMsg", "Error occured while saving the category");
						model.addAttribute("allSubjects", allSubjects);
						model.addAttribute("category", new Category());
						return "add-category";

					}
					model.addAttribute("errMsg", categoryByName.getCategoryName() + " Category already exits");
					model.addAttribute("allSubjects", allSubjects);
					return "add-category";
				}
			}
		}
		return "redirect:/logout";

	}

	@GetMapping("/view-categories")
	public String showAllCategories(HttpServletRequest req, Model model) {
		HttpSession session = req.getSession(false);
		if (session != null) {
			if (session.getAttribute("userType").equals("admin")) {
				List<Category> allCategories = adminService.getAllCategories();
				Category category = new Category();
				model.addAttribute("allCategories", allCategories);
				model.addAttribute("category", category);
				return "view-categories";
			}
		}
		return "redirect:/logout";
	}
	
	@PostMapping("/filtered-categories")
	public String showAllFilteredCategories(@ModelAttribute("category") Category c ,HttpServletRequest req, Model model) {
		HttpSession session = req.getSession(false);
		if(session != null) {
			if(session.getAttribute("userType").equals("admin")) {
				List<Category> allFilteredCategories = adminService.getAllFilteredCategories(c);
				model.addAttribute("allCategories", allFilteredCategories);
				model.addAttribute("category", c);
				return "filtered-category";
			}
		}
		return "redirect:/logout";
	}

	@GetMapping("/edit-category")
	public String editCategory(@RequestParam("categoryId") Integer categoryId, HttpServletRequest req, Model model) {
		HttpSession session = req.getSession(false);
		if (session != null) {
			if (session.getAttribute("userType").equals("admin")) {
				Category categoryById = adminService.getCategoryById(categoryId);
				model.addAttribute("category", categoryById);
				return "add-category";
			}
		}
		return "redirect:/logout";
	}

	@GetMapping("/add-question")
	public String loadAddQuestion(HttpServletRequest req, Model model) {
		HttpSession session = req.getSession(false);
		if (session != null) {
			if (session.getAttribute("userType").equals("admin")) {
				List<Category> allCategories = adminService.getAllCategories();
				model.addAttribute("allCategories", allCategories);
				model.addAttribute("question", new Question());
				return "add-question";
			}
		}
		return "redirect:/logout";
	}

	@PostMapping("/add-question")
	public String saveQuestion(@ModelAttribute("question") Question question, HttpServletRequest req, Model model) {

		HttpSession session = req.getSession(false);
		if (session != null) {
			if (session.getAttribute("userType").equals("admin")) {
				if (question.getQuestionId() != null) {
					Question saveQuestion = null;
					try {
						saveQuestion = adminService.saveQuestion(question, (Integer) session.getAttribute("userId"),
								question.getCategoryId());
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (saveQuestion != null) {
						model.addAttribute("succMsg", "Question updated successfully");
						model.addAttribute("question", saveQuestion);
						return "add-question";
					}
					model.addAttribute("errMsg", "Error occured while updating the question");
					model.addAttribute("question", question);
					return "add-question";

				} else {
					Questions questionByName = adminService.getQuestionByName(question.getQuestionValue());
					List<Category> allCategories = adminService.getAllCategories();
					if (questionByName == null) {
						Question saveQuestion = null;
						try {
							saveQuestion = adminService.saveQuestion(question, (Integer) session.getAttribute("userId"),
									question.getCategoryId());
						} catch (Exception e) {
							e.printStackTrace();
						}
						if (saveQuestion != null) {
							model.addAttribute("succMsg", "Question saved successfully");
							model.addAttribute("allCategories", allCategories);
							model.addAttribute("question", new Question());
							return "add-question";
						}
						model.addAttribute("errMsg", "Error occured while saving the question");
						model.addAttribute("allCategories", allCategories);
						model.addAttribute("question", new Question());
						return "add-question";
					}
					model.addAttribute("errMsg", "Question is already exists");
					model.addAttribute("allCategories", allCategories);
					model.addAttribute("question", new Question());
					return "add-question";
				}
			}
		}
		return "redirect:/logout";
	}

	@GetMapping("/view-questions")
	public String viewQuestions(HttpServletRequest req, Model model) {
		HttpSession session = req.getSession(false);
		if (session != null) {
			if (session.getAttribute("userType").equals("admin")) {
				List<Question> allQuestions = adminService.getAllQuestions();
				Questions question = new Questions();
				model.addAttribute("allQuestions", allQuestions);
				model.addAttribute("question", question);
				return "view-questions";
			}
		}
		return "redirect:/logout";
	}
	
	@PostMapping("/filtered-questions")
	public String viewFilteredQuestions(@ModelAttribute("question")Questions q,HttpServletRequest req, Model model) {
		HttpSession session = req.getSession(false);
		if(session != null) {
			if(session.getAttribute("userType").equals("admin")) {
				List<Question> allFilteredQuestions = adminService.getAllFilteredQuestions(q);
				model.addAttribute("allQuestions", allFilteredQuestions);
				model.addAttribute("question", q);
				return "filtered-questions";
			}
		}
		return "redirect:/logout";
	}

	@GetMapping("/edit-question")
	public String editQuestion(@RequestParam("questionId") Integer questionId, HttpServletRequest req, Model model) {
		HttpSession session = req.getSession(false);
		if (session != null) {
			if (session.getAttribute("userType").equals("admin")) {
				Question byQuestionId = adminService.getByQuestionId(questionId);
				if (byQuestionId != null) {
					model.addAttribute("question", byQuestionId);
					return "add-question";
				}
				model.addAttribute("errMsg", "Question not exixts");
				return "add-question";
			}
		}
		return "redirect:/logout";
	}

	@GetMapping("/student-responses")
	public String viewStudentResponses(HttpServletRequest req, Model model) {
		HttpSession session = req.getSession(false);
		if (session != null) {
			if (session.getAttribute("userType").equals("admin")) {
				List<StudentResponse> viewAllStudentResponse = adminService.viewAllStudentResponse();
				model.addAttribute("allResponses", viewAllStudentResponse);
				User user = new User();
				model.addAttribute("user", user);
				return "student-responses";
			}
		}
		return "redirect:/logout";
	}
	
	@PostMapping("/filtered-responses")
	public String viewFilteredStudentResponses(@ModelAttribute("user") User user,HttpServletRequest req, Model model) {
		HttpSession session = req.getSession(false);
		if(session != null) {
			if(session.getAttribute("userType").equals("admin")) {
				List<StudentResponse> viewAllFilteredStudentResponses = adminService.viewAllFilteredStudentResponses(user);
				model.addAttribute("allResponses", viewAllFilteredStudentResponses);
				model.addAttribute("user", user);
				return "filtered-responses";
			}
		}
		return "redirect:/logout";
	}

}
