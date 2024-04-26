package in.ashokit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entity.Admin;

public interface AdminRepo extends JpaRepository<Admin, Integer> {
	
	
}
