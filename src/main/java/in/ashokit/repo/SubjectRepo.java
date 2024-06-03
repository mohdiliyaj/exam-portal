package in.ashokit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import in.ashokit.entity.Subject;

public interface SubjectRepo extends JpaRepository<Subject, Integer> {
	
	public Subject findBySubjectNameIgnoringCase(String name);
	
	public List<Subject> findAll(Specification<Subject> spec);
	
	
	
}
