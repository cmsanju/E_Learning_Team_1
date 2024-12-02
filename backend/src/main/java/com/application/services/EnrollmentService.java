package com.application.services;

import java.util.List;

import com.application.DTO.EnrollmentDetailsDTO;
import com.application.model.User;
import com.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.application.model.Enrollment;
import com.application.repository.EnrollmentRepository;
import com.application.model.MailData;

@Service
public class EnrollmentService {

	@Autowired
	private EnrollmentRepository enrollmentRepo;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EmailService emailService;

	public Enrollment saveEnrollment(Enrollment enrollment) {
		return enrollmentRepo.save(enrollment);
	}

	public Enrollment addNewEnrollment(Enrollment enrollment) {
		return enrollmentRepo.save(enrollment);
	}

	public List<Enrollment> getAllEnrollments() {
		return (List<Enrollment>) enrollmentRepo.findAll();
	}

	public void updateEnrolledcount(String coursename, int enrolledcount) {
		enrollmentRepo.updateEnrolledcount(enrolledcount, coursename);
	}

	public Enrollment fetchByCoursename(String coursename) {
		return enrollmentRepo.findByCoursename(coursename);
	}

	public Enrollment fetchByCourseid(String courseid) {
		return enrollmentRepo.findByCourseid(courseid);
	}

	public List<Enrollment> fetchByEnrolledusername(String enrolledusername) {
		return (List<Enrollment>) enrollmentRepo.findByEnrolledusername(enrolledusername);
	}

	public List<Enrollment> fetchByEnrolleduserid(String enrolleduserid) {
		return (List<Enrollment>) enrollmentRepo.findByEnrolleduserid(enrolleduserid);
	}

	public List<Enrollment> fetchByEnrolledusertype(String enrolledusertype) {
		return (List<Enrollment>) enrollmentRepo.findByEnrolledusertype(enrolledusertype);
	}

	public List<Enrollment> fetchByInstructorname(String instructorname) {
		return (List<Enrollment>) enrollmentRepo.findByInstructorname(instructorname);
	}

	public List<Enrollment> fetchByInstructorinstitution(String instructorinstitution) {
		return (List<Enrollment>) enrollmentRepo.findByInstructorinstitution(instructorinstitution);
	}

	public List<Enrollment> fetchByEnrolleddate(String enrolleddate) {
		return (List<Enrollment>) enrollmentRepo.findByEnrolleddate(enrolleddate);
	}

	public List<Enrollment> fetchByCoursetype(String coursetype) {
		return (List<Enrollment>) enrollmentRepo.findByCoursetype(coursetype);
	}

	public List<Enrollment> fetchByYoutubeurl(String youtubeurl) {
		return (List<Enrollment>) enrollmentRepo.findByYoutubeurl(youtubeurl);
	}

	public List<Enrollment> fetchByWebsiteurl(String websiteurl) {
		return (List<Enrollment>) enrollmentRepo.findByWebsiteurl(websiteurl);
	}

	public List<Enrollment> fetchBySkilllevel(String skilllevel) {
		return (List<Enrollment>) enrollmentRepo.findBySkilllevel(skilllevel);
	}

	public List<Enrollment> fetchByLanguage(String language) {
		return (List<Enrollment>) enrollmentRepo.findByLanguage(language);
	}

	// Method to send enrollment details via email
//	public void sendEnrollmentEmail(String enrollduserid, String userEmail) {
//		Enrollment enrollment = enrollmentRepo.findFirstByEnrolleduserid(enrollduserid);
//
//		if (enrollment == null) {
//			throw new RuntimeException("No enrollment found for userId: " + enrollduserid);
//		}
//
//		// Map Enrollment to DTO
//		EnrollmentDetailsDTO detailsDTO = new EnrollmentDetailsDTO(
//				enrollment.getCoursename(),
//				enrollment.getInstructorname(),
//				enrollment.getInstructorinstitution(),
//				enrollment.getEnrolleddate(),
//				enrollment.getCoursetype(),
//				enrollment.getSkilllevel(),
//				enrollment.getLanguage()
//		);
//
//		// Build email content
//		String emailContent = buildEmailContent(detailsDTO);
//
//		// Create MailData object
//		MailData mailData = new MailData();
//		mailData.setRecipient(userEmail);
//		mailData.setSubject("Enrollment Confirmation");
//		mailData.setMsgBody(emailContent);
//
//		// Send the email
//		emailService.sendSimpleMail(mailData);
//	}

	// public void sendEnrollmentEmail(String userid) {
	// 	User user = userRepository.findByUserid(userid);
	// 	if (user == null) {
	// 		throw new RuntimeException("No user found for userid: " + userid);
	// 	}

	// 	String userEmail = user.getEmail();

	// 	Enrollment enrollment = enrollmentRepo.findFirstByEnrolleduserid(userid);
	// 	if (enrollment == null) {
	// 		throw new RuntimeException("No enrollment found for userid: " + userid);
	// 	}

	// 	// Map Enrollment to DTO
	// 	EnrollmentDetailsDTO detailsDTO = new EnrollmentDetailsDTO(
	// 			enrollment.getCoursename(),
	// 			enrollment.getInstructorname(),
	// 			enrollment.getInstructorinstitution(),
	// 			enrollment.getEnrolleddate(),
	// 			enrollment.getCoursetype(),
	// 			enrollment.getSkilllevel(),
	// 			enrollment.getLanguage()
	// 	);


	// 	String emailContent = buildEmailContent(detailsDTO);


	// 	MailData mailData = new MailData();
	// 	mailData.setRecipient(userEmail);
	// 	mailData.setSubject("Enrollment Confirmation");
	// 	mailData.setMsgBody(emailContent);

	// 	emailService.sendSimpleMail(mailData);
	// }

	public void sendEnrollmentEmailByEmail(String email) {
		try {
			System.out.println("=== Starting Email Service ===");
			System.out.println("Received email: " + email);

			// Get user
			User user = userRepository.findByEmail(email);
			if (user == null) {
				System.out.println("No user found for email: " + email);
				throw new RuntimeException("No user found with email: " + email);
			}
			System.out.println("Found user: " + user.getUserid());

			// Get enrollment
			String userId = user.getUserid();
			Enrollment enrollment = enrollmentRepo.findFirstByEnrolleduserid(userId);
			if (enrollment == null) {
				System.out.println("No enrollment found for userId: " + userId);
				throw new RuntimeException("No enrollment found for user ID: " + userId);
			}
			System.out.println("Found enrollment for course: " + enrollment.getCoursename());

			// Create DTO
			EnrollmentDetailsDTO detailsDTO = new EnrollmentDetailsDTO(
					enrollment.getCoursename(),
					enrollment.getInstructorname(),
					enrollment.getInstructorinstitution(),
					enrollment.getEnrolleddate(),
					enrollment.getCoursetype(),
					enrollment.getSkilllevel(),
					enrollment.getLanguage()
			);
			System.out.println("Created DTO for course: " + detailsDTO.getCoursename());

			// Send email
			String emailContent = buildEmailContent(detailsDTO);
			MailData mailData = new MailData();
			mailData.setRecipient(email);
			mailData.setSubject("Enrollment Confirmation");
			mailData.setMsgBody(emailContent);
			System.out.println("Attempting to send email to: " + email);

			emailService.sendSimpleMail(mailData);
			System.out.println("=== Email Sent Successfully ===");
			
		} catch (Exception e) {
			System.err.println("=== Email Service Error ===");
			System.err.println("Error message: " + e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("Failed to process enrollment email: " + e.getMessage());
		}
	}




	private String buildEmailContent(EnrollmentDetailsDTO details) {
		return "Dear User,\n\n" +
				"Thank you for enrolling in the course!\n\n" +
				"Course Name: " + details.getCoursename() + "\n" +
				"Instructor: " + details.getInstructorname() + "\n" +
				"Institution: " + details.getInstructorinstitution() + "\n" +
				"Enrollment Date: " + details.getEnrolleddate() + "\n" +
				"Course Type: " + details.getCoursetype() + "\n" +
				"Skill Level: " + details.getSkilllevel() + "\n" +
				"Language: " + details.getLanguage() + "\n\n" +
				"Happy Learning!\n\nBest Regards,\nTeam";
	}
}
