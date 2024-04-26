package in.ashokit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entity.Subject;

public interface SubjectRepo extends JpaRepository<Subject, Integer> {
	
	public Subject findBySubjectNameIgnoringCase(String name);
	
}
