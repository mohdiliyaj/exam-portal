package in.ashokit.spec;

import org.springframework.data.jpa.domain.Specification;

import in.ashokit.entity.Category;

public class CategorySpecification {
	public static Specification<Category> likeName(String name){
		return (root, query, CriteriaBuilder)->CriteriaBuilder.like(root.get("categoryName"), "%" +name+"%");
	}
}
