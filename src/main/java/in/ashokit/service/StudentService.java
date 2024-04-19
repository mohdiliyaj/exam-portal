package in.ashokit.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import in.ashokit.binding.LoginBinding;
import in.ashokit.entity.Student;
import in.ashokit.repo.StudentRepo;

@Service
public class StudentService implements IStudentService {
	
	private StudentRepo studentRepo;
	
	public StudentService(StudentRepo studentRepo) {
		this.studentRepo = studentRepo;
	}
	
	@Override
	public Student loginCheck(LoginBinding login) {
		Student student = studentRepo.findByEmailAndPassword(login.getEmail(), login.getPassword());
		if(student != null) {
			return student;
		}
		return null;
	}
	
	@Override
	public Student getStudent(Integer studentId) {
		Optional<Student> byId = studentRepo.findById(studentId);
		if(byId.isPresent()) {
			Student student = byId.get();
			return student;
		}
		return null;
	}
	
	@Override
	public Student updateStudentDetails(Student updatedStudent) {
		Student student = studentRepo.findById(updatedStudent.getStudentId()).get();		
		if(student.getName() != updatedStudent.getName()) {
			student.setName(updatedStudent.getName());
		}
		if(student.getPhoneNumber() != updatedStudent.getPhoneNumber()) {
			student.setPhoneNumber(updatedStudent.getPhoneNumber());
		}
		if(student.getAddress() != updatedStudent.getAddress()) {
			student.setAddress(updatedStudent.getAddress());
		}
		if(student.getPassword() != updatedStudent.getPassword() && !updatedStudent.getPassword().equals("")) {
			student.setPassword(updatedStudent.getPassword());
		}
		if(student.getGender() != updatedStudent.getGender()) {
			student.setGender(updatedStudent.getGender());
		}
		Student updatedStudentDetails = studentRepo.save(student);
		return updatedStudentDetails;
	}
	
}
