package com.application.DTO;

public class EnrollmentDetailsDTO {
    private String coursename;
    private String instructorname;
    private String instructorinstitution;
    private String enrolleddate;
    private String coursetype;
    private String skilllevel;
    private String language;


    public EnrollmentDetailsDTO() {}

    public EnrollmentDetailsDTO(String coursename, String instructorname, String instructorinstitution,
                                String enrolleddate, String coursetype, String skilllevel, String language) {
        this.coursename = coursename;
        this.instructorname = instructorname;
        this.instructorinstitution = instructorinstitution;
        this.enrolleddate = enrolleddate;
        this.coursetype = coursetype;
        this.skilllevel = skilllevel;
        this.language = language;
    }

    // Getters and Setters
    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public String getInstructorname() {
        return instructorname;
    }

    public void setInstructorname(String instructorname) {
        this.instructorname = instructorname;
    }

    public String getInstructorinstitution() {
        return instructorinstitution;
    }

    public void setInstructorinstitution(String instructorinstitution) {
        this.instructorinstitution = instructorinstitution;
    }

    public String getEnrolleddate() {
        return enrolleddate;
    }

    public void setEnrolleddate(String enrolleddate) {
        this.enrolleddate = enrolleddate;
    }

    public String getCoursetype() {
        return coursetype;
    }

    public void setCoursetype(String coursetype) {
        this.coursetype = coursetype;
    }

    public String getSkilllevel() {
        return skilllevel;
    }

    public void setSkilllevel(String skilllevel) {
        this.skilllevel = skilllevel;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

}
