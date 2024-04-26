package in.ashokit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entity.Student;
import in.ashokit.entity.User;


public interface StudentRepo extends JpaRepository<Student, Integer>{
	
	public Student findByUser(User user);

}
