package in.ashokit.repo;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entity.StudentResponse;
import in.ashokit.entity.User;


public interface StudentResponseRepo extends JpaRepository<StudentResponse, Integer> {
	
	public List<StudentResponse> findByUser(User userId);
	
	public List<StudentResponse> findByExamStatus(Boolean examStatus);
	
	public List<StudentResponse> findAll(Specification<StudentResponse> spec);
	
}
