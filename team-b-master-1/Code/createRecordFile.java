//package textfiles;
import java.io.*; //io.FileWriter; io.PrintWriter; io.IOException;
import java.lang.*;
import java.util.*;

public class createRecordFile {
	public boolean Networking_Fundamentals = true; //set to 'true' only for testing
	public boolean Wireless_Communications = true;
	public boolean Intro_to_Programming = false;
	public boolean Intro_to_Programming_2 = false;
	public int counterNF = 10;
	public int counterWC = 10; //set to 10 only for testing below 'if' condition
	public int counterItoP = 10;
	public int counterItoP2 = 10;

	File recordFile;
	PrintWriter fillStudentRecord;

	//public createRecordFile() { }
	public void createFile() {
		try{
			recordFile = new File("studentRegistrationRecord.txt");
			fillStudentRecord = new PrintWriter(recordFile);
		}
		catch(Exception e) {
			System.out.println("you hava an error");
		}
	}

	//public void updateRecords(int studentID, String fn, String ln, int courseID) {
	public void updateRecords() {
		if ((Networking_Fundamentals) && (counterNF <= 30)) {
			//fillStudentRecord.printf("%i%s%s%i", studentID, fn, ln + courseID);
			fillStudentRecord.printf("%s%s%s%s", "Charlie,", "Brown,", "sss-ss-ssss,", "12345" + "\n");
			counterNF += counterNF;
			//Need to call Course.txt and read its current 'Number Enrolled' before the above 'if'
			//Need to call Course.txt and update 'Number Enrolled' field with counterNF to be stored
			//I am including delimitters ',' to read data later (this can be done differently)
		}

		if ((Wireless_Communications) && (counterWC <= 30)) {
			fillStudentRecord.printf("%s%s%s%s", "John,", "Doe,", "sss-ss-ssss,", "12346");
			counterWC += counterWC;
			//call Course.txt and update 'Number Enrolled' field with counterWC
		}

		if ((Intro_to_Programming) && (counterItoP <= 30)) {
			fillStudentRecord.printf("%s%s%s%s", "Charlie ", "Brown ", "sss-ss-ssss ", "12347 ");
			counterItoP += counterItoP;
			//call Course.txt and update 'Number Enrolled' field with counterItoP
		}

		if ((Intro_to_Programming_2) && (counterItoP2 <= 30)) {
			fillStudentRecord.printf("%s%s%s%s", "John ", "Doe ", "sss-ss-ssss ", "12348 ");
			counterItoP2 += counterItoP2;
			//call Course.txt and update 'Number Enrolled' field with counterItoP2
		}

	}

	public void closeFile() {
		fillStudentRecord.close();
	}
}
