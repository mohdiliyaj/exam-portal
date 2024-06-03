package in.ashokit.repo;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entity.Category;
import in.ashokit.entity.Subject;

public interface CategoryRepo extends JpaRepository<Category, Integer>{
	
	public Category findByCategoryName(String name);
	
	public Category findByCategoryNameIgnoringCase(String name);
	
	public List<Category> findAll(Specification<Category> spec);

}
