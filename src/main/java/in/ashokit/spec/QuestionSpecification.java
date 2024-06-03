package in.ashokit.spec;

import org.springframework.data.jpa.domain.Specification;

import in.ashokit.entity.Questions;

public class QuestionSpecification {
	public static Specification<Questions> likeName(String name){
		return (root, query, CriteriaBuilder)->CriteriaBuilder.like(root.get("questionValue"), "%" +name+"%");
	}
	
}
