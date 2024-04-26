package in.ashokit.binding;

public class StudentDashboard {
	
	private Integer noOfExams;
	private Long passedExams;
	private Long failedExams;
	private String overAllPercentage;
	
	public Integer getNoOfExams() {
		return noOfExams;
	}
	public void setNoOfExams(Integer noOfExams) {
		this.noOfExams = noOfExams;
	}
	public Long getPassedExams() {
		return passedExams;
	}
	public void setPassedExams(Long passedExams) {
		this.passedExams = passedExams;
	}
	public Long getFailedExams() {
		return failedExams;
	}
	public void setFailedExams(Long failedExams) {
		this.failedExams = failedExams;
	}
	public String getOverAllPercentage() {
		return overAllPercentage;
	}
	public void setOverAllPercentage(String overAllPercentage) {
		this.overAllPercentage = overAllPercentage;
	}
}
