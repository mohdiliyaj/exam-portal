package in.ashokit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entity.Question;

public interface QuestionRepo extends JpaRepository<Question, Integer> {

}
