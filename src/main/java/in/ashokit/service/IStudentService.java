package in.ashokit.service;

import in.ashokit.binding.LoginBinding;
import in.ashokit.entity.Student;

public interface IStudentService {
	
	public Student loginCheck(LoginBinding login);
	
	public Student getStudent(Integer studentId);
	
	public Student updateStudentDetails(Student student);

}
