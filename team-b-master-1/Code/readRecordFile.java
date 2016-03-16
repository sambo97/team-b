import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class readRecordFile {

	public void readStudentFile() 	{
		File testFile;
		Scanner fileScanner;

		try {
			testFile = new File("studentRegistrationRecord.txt");
			fileScanner = new Scanner(testFile);
			fileScanner.useDelimiter(",");

			while (fileScanner.hasNext()) {
				System.out.println(fileScanner.next());
			}

			fileScanner.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}