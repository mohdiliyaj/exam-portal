package in.ashokit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entity.Student;

public interface StudentRepo extends JpaRepository<Student, Integer>{
	
	public Student findByEmailAndPassword(String email, String password);

}
