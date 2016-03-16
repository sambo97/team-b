import java.util.Vector;

public class Course implements Comparable<Course> {

    //Private Instance Variables
    private String courseName;
    private String courseID;
    private String startDate;
    private String endDate;
    private int enrollmentLimit;
    private int numEnrolled;
    private String description;


    //Constructor
    public Course(String courseName,
                  String courseID,
                  String startDate,
                  String endDate,
                  int enrollmentLimit,
                  int numEnrolled,
                  String description)
    {
        this.courseName = courseName;
        this.courseID = courseID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.enrollmentLimit = enrollmentLimit;
        this.numEnrolled = numEnrolled;
        this.description = description;

    }

    /*
    *********************************
    Setter Methods
    *********************************
    */


    public void setCourseName(String courseName)
    {
        this.courseName = courseName;
    }

    public void setCourseID(String courseID)
    {
        this.courseID = courseID;
    }

    public void setStartDate(String startDate)
    {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate)
    {
        this.endDate = endDate;
    }

    public void setEnrollmentLimit(int enrollmentLimit)
    {
        this.enrollmentLimit = enrollmentLimit;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    /*
    *********************************
    Getter Methods
    *********************************
    */


    public String getCourseName()
    {
        return this.courseName;
    }

    public String getCourseID()
    {
        return this.courseID;
    }

    public String getStartDate()
    {
        return this.startDate;
    }

    public String getEndDate()
    {
        return this.endDate;
    }

    public int getEnrollmentLimit()
    {
        return this.enrollmentLimit;
    }

    public int getNumEnrolled()
    {
        return this.numEnrolled;
    }

    public String getDescription()
    {
        return this.description;
    }


    /*
    *********************************
    Class Methods
    *********************************
    */

    //Increases the current enrollment number by one
    public void incrementEnrollment()
    {
        this.numEnrolled += 1;
    }

    //Decreases the current enrollment number by one
    public void decrementEnrollment()
    {
        this.numEnrolled -= 1;
    }
    /*
    CompareTo override for sorting by Course Name
    */
    public int compareTo(Course course)
    {
        return (this.courseName.compareTo(course.getCourseName()));
    }

    /*
    -This method returns Course data as a String.
    -ASSIGNEE: Alberto
    -Note: Alberto, I assume you would use this method to organize each course so that it prints on a single line. Modify as needed.
    */
    public String toString()
    {
        String result = getCourseName().toUpperCase() + "\n"
                        + "Course ID: " + getCourseID() + "  "
                        + "Start Date: " + getStartDate() + "  "
                        + "End Date: " + getEndDate() + "\n"
                        + "Enrollment Limit: " + Integer.toString(getEnrollmentLimit()) + "\n"
                        + "Number Enrolled: " + Integer.toString(getNumEnrolled()) + "\n"
                        + "Description: " + getDescription() + "\n";

        return result;
    }



}