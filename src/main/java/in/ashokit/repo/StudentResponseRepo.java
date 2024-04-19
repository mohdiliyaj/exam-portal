package in.ashokit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entity.StudentResponse;

public interface StudentResponseRepo extends JpaRepository<StudentResponse, Integer> {

}
