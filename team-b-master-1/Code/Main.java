import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {

		// read courses that are available
		readCourseState courseRecord = new readCourseState();
		courseRecord.readCourseData();

		// create record for student registration
		createRecordFile studentFile = new createRecordFile();
		studentFile.createFile();
		studentFile.updateRecords();
		studentFile.closeFile();

		// read the above student record registration
		System.out.println();
		readRecordFile studentRecord = new readRecordFile();
		studentRecord.readStudentFile();
    }
}
