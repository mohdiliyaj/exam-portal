package in.ashokit.spec;

import org.springframework.data.jpa.domain.Specification;

import in.ashokit.entity.Subject;

public class SubjectSpecification {
	public static Specification<Subject> likeName(String name){
		return (root, query, CriteriaBuilder)->CriteriaBuilder.like(root.get("subjectName"), "%" +name+"%");
	}
}
