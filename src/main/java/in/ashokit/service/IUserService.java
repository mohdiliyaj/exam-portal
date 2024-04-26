package in.ashokit.service;

import in.ashokit.binding.Login;
import in.ashokit.binding.Register;
import in.ashokit.entity.User;

public interface IUserService {
	
	public User loginCheck(Login login);
	
	public User saveStudent(Register register);
	
	public User findUserByEmail(String email);

}
