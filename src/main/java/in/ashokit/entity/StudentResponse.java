package in.ashokit.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "student_response")
public class StudentResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer responseId;
    private Integer scoredMarks;
    private LocalDateTime submittedTime;
    private Boolean examStatus;
    private Double percentage;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @Column(name = "responses", columnDefinition = "json")
    private String responses;    
    
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

	public Integer getResponseId() {
		return responseId;
	}

	public void setResponseId(Integer responseId) {
		this.responseId = responseId;
	}

	public Integer getScoredMarks() {
		return scoredMarks;
	}

	public void setScoredMarks(Integer scoredMarks) {
		this.scoredMarks = scoredMarks;
	}

	public LocalDateTime getSubmittedTime() {
		return submittedTime;
	}

	public void setSubmittedTime(LocalDateTime submittedTime) {
		this.submittedTime = submittedTime;
	}

	public Boolean getExamStatus() {
		return examStatus;
	}

	public void setExamStatus(Boolean examStatus) {
		this.examStatus = examStatus;
	}

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getResponses() {
		return responses;
	}

	public void setResponses(String responses) {
		this.responses = responses;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
}

