package in.ashokit.service;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import in.ashokit.binding.Login;
import in.ashokit.binding.Register;
import in.ashokit.entity.Student;
import in.ashokit.entity.User;
import in.ashokit.repo.StudentRepo;
import in.ashokit.repo.UserRepo;
import jakarta.transaction.Transactional;

@Service
public class UserService implements IUserService {

	private UserRepo userRepo;
	private StudentRepo studentRepo;

	public UserService(UserRepo userRepo, StudentRepo studentRepo) {
		this.userRepo = userRepo;
		this.studentRepo = studentRepo;
	}

	@Override
	public User loginCheck(Login login) {
		User user = userRepo.findByEmailAndPassword(login.getEmail(), login.getPassword());
		if (user != null) {
			return user;
		}
		return null;
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public User saveStudent(Register register) {
		User user = new User();
		try {
			BeanUtils.copyProperties(user, register);
		} catch (Exception e) {
			e.printStackTrace();
		}
		user.setUserRole("student");
		User save = userRepo.save(user);
		Student student = new Student();
		try {
			BeanUtils.copyProperties(student, register);
		} catch (Exception e) {
			e.printStackTrace();
		}
		student.setUser(save);
		studentRepo.save(student);
		return save;
	}
	
	@Override
	public User findUserByEmail(String email) {
		return userRepo.findByEmail(email);
	}
}
