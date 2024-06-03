package in.ashokit.spec;

import org.springframework.data.jpa.domain.Specification;

import in.ashokit.entity.StudentResponse;
import in.ashokit.entity.User;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;

public class StudentResponseSpecification {
	public static Specification<StudentResponse> byUserEmail(String email) {
        return (root, query, criteriaBuilder) -> {
            Join<StudentResponse, User> userJoin = root.join("user");
            Predicate predicate = criteriaBuilder.like(userJoin.get("email"), "%" +email+"%");
            return predicate;
        };
    }
}
