import java.io.*; //io.File; io.FileNotFoundException; io.IOException;
import java.lang.*;
import java.util.*; //util.Scanner;

public class readCourseState {
	public boolean courseSelection = false; //as of now, set to 'true' only for testing
	Scanner sc=new Scanner(System.in);
	createRecordFile studentFile = new createRecordFile();

	public void readCourseData() {
		File trialFile;
		Scanner fileScanner;

		try {
	    	trialFile = new File("Course.txt");
	        fileScanner = new Scanner(trialFile);
	        fileScanner.useDelimiter(",");

	        while(fileScanner.hasNext()) {
	        	String a = fileScanner.next();
	            String b = fileScanner.next();
	            String c = fileScanner.next();
	            String d = fileScanner.next();
	            String e = fileScanner.next();
	            String f = fileScanner.next();
	            String g = fileScanner.next();

	            System.out.printf("%-23s %-10s %-11s %-10s %-9s %-16s %-43s%n", a, b, c, d, e, f, g);
	            //System.out.println();
	        }

	        fileScanner.close();
	     }  catch (FileNotFoundException e) {
	        System.out.println("File could not be found");
         }

         System.out.println("\n\nSELECT THE COURSE  [please, enter Course ID [12345] for testing]");
         int courseID = sc.nextInt();
		 if (courseID == 12345) {courseSelection = true;} //just enter 12345 for testing
	     if (courseSelection) {registerProcess();}
     }

     public void registerProcess() {
	 	System.out.println("\nEnter your student ID (ie: 333)");
		   int studentID = sc.nextInt();
		System.out.println("Enter your First Name");
		   String firsName = sc.next();
		System.out.println("Enter your Last Name");
		   String lastName = sc.next();
		System.out.println("Student ID: " + studentID + " First Name: " + firsName +
														" Last Name: " + lastName);
   		sc.close();

		studentFile.createFile();
		//studentFile.updateRecords(studentID, firsName, lastName, courseID);
		studentFile.updateRecords();
		studentFile.closeFile();
	 }

}