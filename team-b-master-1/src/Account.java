import java.util.ArrayList;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.util.Scanner;
import java.lang.*;
import java.io.IOException;
import java.util.Properties;
import java.io.IOException;

public class Account extends Person {

    //Private Instance Variables
    private String userName;
    private String password;
    private String id;
    private ArrayList<Course> registeredCourses = new ArrayList<Course>();

    //Main Constructor
    public Account(String firstName,
                  String lastName,
                  int age,
                  String gender,
                  String socialSecurityNumber,
                  String userName,
                  String password,
                  String id)
    {
        super(firstName,lastName,age,gender,socialSecurityNumber); //Calls the Constructor in the super class.
        this.userName = userName;
        this.password = password;
        this.id = id;

    }

    /*
    *********************************
    Setter Methods
    *********************************
    */

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setRegisteredCourses(ArrayList<Course> registeredCourses)
    {
        this.registeredCourses = registeredCourses;
    }

    /*
    *********************************
    Getter Methods
    *********************************
    */

    public String getUserName()
    {
        return this.userName;
    }

    public String getPassword()
    {
        return this.password;
    }
    
    public String getID()
    {
        return this.id;
    }

    public ArrayList<Course> getRegisteredCourses()
    {
        return this.registeredCourses;
    }


    /*
    *********************************
    Class Methods
    *********************************
    */


   /*
    -Attempts to add course to this.registeredCourses
        -Return false if course is duplicate or Course is at max Enrollment Limit.
        -If true, add course to this.registeredCourses
        -Then call course.incrementEnrollment() to increase enrollment of course by 1.
        -Then call this.updateAccountRecord() to update AccountRecords.txt with the new Course.
        -Then call this.updateCoursesRecord() to update Courses.txt with the "numEnrolled" Update.
            -Return true.
    -ASSIGNEE: John
   */
    public boolean addCourse (Course course) {
	        if(this.registeredCourses.contains(course) || course.getNumEnrolled() == course.getEnrollmentLimit()) {
	            return false;
	        }
	        this.registeredCourses.add(course);
	        course.incrementEnrollment();
	        this.updateAccountRecord();
	        this.updateCoursesRecord(course);
	        return true;
    }

   /*
    -Attempts to remove course from this.registeredCourses
        -Return false if user is not registered to Course
        -If true, remove Course from this.registeredCourses
        -Then call course.decrementEnrollment() to decrease enrollment of course by 1.
        -Then call this.updateAccountRecord() to update AccountRecords.txt with the removal of the Course.
        -Then call this.updateCoursesRecord() to update Courses.txt with the "numEnrolled" update.
            -Return true.
    -ASSIGNEE: Noe
   */
    public boolean removeCourse(Course course)
    {
        if (!this.registeredCourses.contains(course)) // if Account is not registered to class, return false.
        {
            return false;
        }

        //Remove course from registeredCourses
        this.registeredCourses.remove(course);
        //Decrement Enrollment
        course.decrementEnrollment();
        //Update databases
        updateAccountRecord();
        updateCoursesRecord(course);

        return true;

    }

    /*
    *********************************
    Private Utility Methods
    *********************************
    */


    /*
    -This private utility method will update the AccountRecords.txt file with the addition or removal of a Course for this Account.
    -ASSIGNEE: John
    */
    private void updateAccountRecord() {
	        //re-print this course record in Courses.txt. The numEnrolled value will be updated.
	        //Open the current Courses.txt.
	        File originalFile = null;
	        Scanner origFileScanner = null;
	        BufferedWriter out = null;
	        String delimiter = null;
	        String line = null;
	        int delimiterLength = 0;

	        try {
	            originalFile = new File("AccountRecords.txt");
	            origFileScanner = new Scanner(originalFile, "UTF-8");
	            delimiter = ",";
	            delimiterLength = delimiter.length();
	            origFileScanner.useDelimiter(delimiter);
	        } catch (IOException e) {
	            System.out.println("File Not Found.");
	        }

	        File tempAccountFile = new File("TempAccountRecords.txt");
	        try {
	            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempAccountFile), "UTF-8"));
	        } catch (IOException e) {
	            System.out.println("File Not Found.");
	        }

	        while(origFileScanner.hasNext()) {
	            line = origFileScanner.nextLine();
                String target = ","+this.id+",";
                System.out.println("Target: "+ target);
	            if(line.contains((target))) {
                    System.out.println("*DEBUG* this.ID: " + this.id);
                    System.out.println(line);
	                //Get index of delimiters(",")
	                ArrayList<Integer> indexArrayList = new ArrayList<Integer>();

	                for (int i = 0; i < 8; i++)  //Count to studentID
	                {
	                    if (i == 0) //first iteration of loop
	                    {
	                        indexArrayList.add(line.indexOf(delimiter));

	                    } else {

	                        indexArrayList.add(line.indexOf(delimiter, indexArrayList.get(indexArrayList.size() - 1) + delimiterLength));
	                    }
	                }
	                //Construct a part of the String before adding courseIDs to the String
	                String origString = line.substring(0, indexArrayList.get(indexArrayList.size() - 1))+ delimiter;


	                for (int i = 0; i < registeredCourses.size(); i++) {
	                    origString += this.registeredCourses.get(i).getCourseID() + ",";
	                }

	                try {
	                    out.write(origString + "\r\n");
	                    out.flush();
	                }

	                catch (IOException e) {
	                    System.out.println("Exception");
	                }


	            } else {
	                try {
	                    out.write(line + "\r\n");
	                    out.flush();
	                }
	                catch (IOException e) {
	                    System.out.println("Exception");
	                }
	            }
	        }

	        try {
	            out.close();
	            origFileScanner.close();
	        }
	        catch (IOException e) {
	            System.out.println("Exception");
	        }

	        //Delete originalFile
	        originalFile.delete();

	        //Rename tempFile to originalFile
	        tempAccountFile.renameTo(originalFile);

    }

    /*
    -This private utility method will update the Courses.txt file with the "numEnrolled" update due to an addition or removal of a registered Course.
    -ASSIGNEE: Noe
    */
    private void updateCoursesRecord(Course course)
    {
        //re-print this course record in Courses.txt. The numEnrolled value will be updated.
        File originalFile = null;
        Scanner origFileScanner = null;
        BufferedWriter out = null;
        String delimiter = null;
        String line = null;
        int delimiterLength = 0;








        //Open the current Courses.txt.
        originalFile = new File("Courses.txt");
        try
        {
            origFileScanner = new Scanner(originalFile,"UTF-8");
            delimiter = "\",\"";
            delimiterLength = delimiter.length();
            origFileScanner.useDelimiter(delimiter);
        }
        catch (IOException e)
        {
            System.out.println("File Not Found.");
        }

        //Create a new temp file that will be later renamed to Courses.txt.
        File tempFile = new File("tempfile.txt");
        try
        {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile), "UTF-8"));
        }
        catch (IOException e)
        {
            System.out.println("File Not Found."); //this doesn't make sense????
        }

        line = null;
        while (origFileScanner.hasNext())
        {
            line = origFileScanner.nextLine();

            if (line.contains(course.getCourseID())) //course.getCourseID()
            {
                //Get index of delimiters.
                ArrayList<Integer> indexArrayList = new ArrayList<Integer>();

                for (int ii=0; ii < 6; ii++)//Count over to numEnrolled Value
                {
                    if (ii == 0) //first iteration of loop
                    {
                        indexArrayList.add(line.indexOf(delimiter));
                    }
                    else
                    {
                        indexArrayList.add(line.indexOf(delimiter,indexArrayList.get(indexArrayList.size()-1)+delimiterLength));
                    }
                }

                //Construct First Part of New String
                String firstPart = line.substring(0,indexArrayList.get(indexArrayList.size()-2));

                //Construct Last Part of New String
                String lastPart = line.substring(indexArrayList.get(indexArrayList.size()-1),line.length());

                //Construct string for value that is being updated.
                String updatedValue = "\",\"" + course.getNumEnrolled(); //course.getNumEnrolled()

                //Construct final string
                String result = firstPart + updatedValue + lastPart;

                //Print result to tempFile
                try
                {
                    out.write(result + "\r\n");
                    out.flush();
                }
                catch (IOException e)
                {
                    System.out.println("Exception");
                }


            }
            else //Print unmodified data to tempFile
            {
                try
                {
                    out.write(line + "\r\n");
                    out.flush();
                }
                catch (IOException e)
                {
                    System.out.println("Exception");
                }
            }
        }

        try
        {
            out.close();
            origFileScanner.close();
        }
        catch (IOException e)
        {
            System.out.println("Exception");
        }

        //Delete originalFile
        originalFile.delete();

        //Rename tempFile to originalFile
        tempFile.renameTo(originalFile);
        
    }




}