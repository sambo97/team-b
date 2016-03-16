import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Login {

    public static void main(String[] args) {
        File file;
        Scanner scannerRead, loginUser, displayLoginScreen;
        String username, password, usernameInput, passwordInput, fName, lName, age, gender, ssn;
        ArrayList<String> usernameArrayList = new ArrayList<String>();
        ArrayList<String> passwordArrayList = new ArrayList<String>();
        ArrayList<String> fNameArrayList = new ArrayList<String>();
        ArrayList<String> lNameArrayList = new ArrayList<String>();
        ArrayList<String> ageArrayList = new ArrayList<String>();
        ArrayList<String> genderArrayList = new ArrayList<String>();
        ArrayList<String> ssnArrayList = new ArrayList<String>();
        loginUser = new Scanner(System.in);
        displayLoginScreen = new Scanner(System.in);
        String storage[] = new String [50];


		//Opening statement
		System.out.println("Existing User? (Y/N)");
		String f = displayLoginScreen.next();
	if (f.equals ("Y")){

        	try {
            	file = new File("AccountRecords.txt");
            	scannerRead = new Scanner(file);
            	scannerRead.useDelimiter(",");
				for(int i=0; i<storage.length &&  scannerRead.hasNext(); i++) {
				storage[i]=scannerRead.nextLine();
					}

            	//Stores the names and passwords from the .txt file into a name and password ArrayList
            	while (scannerRead.hasNext()) {
                	username = scannerRead.next();
                	password = scannerRead.next();
                	fName = scannerRead.next();
                	lName = scannerRead.next();
                	age = scannerRead.next();
                	gender = scannerRead.next();
                	ssn = scannerRead.next();
                	usernameArrayList.add(username);
                	passwordArrayList.add(password);
                	fNameArrayList.add(fName);
                	lNameArrayList.add(lName);
                	ageArrayList.add(age);
                	genderArrayList.add(gender);
                	ssnArrayList.add(ssn);
            	}
				loginUser = new Scanner(System.in);
        	    scannerRead.close();

        	} catch (FileNotFoundException e) {
        	    System.out.println("File Not Found");
        	}

        	//Option 1. Compares user input to the contents of the username and password ArrayLists
        	for (int i = 0; i < 3; i++) {
        	    System.out.println("Please enter your Username: ");
        	    usernameInput = loginUser.next();
        	    System.out.println("Please enter your Password: ");
        	    passwordInput = loginUser.next();
        	    if (usernameArrayList.contains(usernameInput) && passwordArrayList.contains(passwordInput)) {
        	        System.out.println("Login Successful");
        	        break;
        	    } else {
        	        System.out.println("Name and Password do not match, please try again" + "\n");
        	    }
        	}


	} //Options if "N" is selected for "Existing user Y/N"
	else if (f.equals ("N")){
	//Option 2. New user: Stores each string with a "," and creates a new line
	System.out.println("Create new user");

	   try {
		System.out.println("Username: ");
		BufferedReader outbr = new BufferedReader(new InputStreamReader(System.in));
		String userNameOut = outbr.readLine();
		System.out.println("Password: ");
		String passwordOut = outbr.readLine();
		System.out.println("First Name: ");
		String fnameOut = outbr.readLine();
		System.out.println("Last Name: ");
		String lnameOut = outbr.readLine();
		System.out.println("Age: ");
		String ageOut = outbr.readLine();
		System.out.println("Gender (M/F): ");
		String genderOut = outbr.readLine();
		System.out.println("Social Security Number or (N/A): ");
		String ssnOut = outbr.readLine();

		PrintWriter createAccount = new PrintWriter(new FileWriter ("AccountRecords.txt", true));

		for (int i = 0; i < 1; i++) {
				createAccount.write(userNameOut + "," + passwordOut + "," + fnameOut + "," + lnameOut + "," + ageOut + "," + genderOut + "," + ssnOut + "\n");
	}

		createAccount.close();
   }
   	   catch (IOException e) {
	System.out.println("Error during reading/writing");
	}

	}
	//Option 3. Invalid starting input
	else{
	System.out.println("Not a valid entry");
	}

 }
}
