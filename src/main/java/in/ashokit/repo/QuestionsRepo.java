package in.ashokit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entity.Category;
import in.ashokit.entity.Questions;


public interface QuestionsRepo extends JpaRepository<Questions, Integer> {
	
	public Questions findByQuestionValue(String question);
	
	public List<Questions> findByCategory(Category category);
	
}
