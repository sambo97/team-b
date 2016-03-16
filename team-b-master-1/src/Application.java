import java.io.File;
import java.util.Scanner;
import java.lang.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.io.FileNotFoundException;

public class Application {

    //Private Instance Variables
    private ArrayList<Course> courseList = new ArrayList<Course>();
    private ArrayList<Account> accountList = new ArrayList<Account>();
    private Account activeUser;
    private Scanner scanner;
    private Scanner lineScanner;
    private Scanner in = new Scanner(System.in);
    private boolean isLoginScreenActive = false;
    private boolean isCreateAccountScreenActive = false;
    private boolean isCourseRegistrationScreenActive = false;
    private boolean isProfileScreenActive = false;
    private boolean isNavigationOptionsScreenActive = false;

    /*
    This main Constructor acts as an "initialization" of the entire application. It will initialize the data by calling the
    loadCoursesData() and loadAccountsData() methods, and printout out the Login Screen.
    */
    public Application()
    {
        this.clearScreen();
        this.loadCoursesData();
        this.loadAccountsData();

    }
    //runs the application and takes in user input.
    public void run()
    {
        this.isLoginScreenActive = true; //show Login Screen on startup.
        while (true)
        {
            if (isLoginScreenActive)
            {
                this.displayLoginScreen(); //Prints out the Login Screen for Logging In or Creating a New Account
                this.isLoginScreenActive = false;
            }
            else if (isCreateAccountScreenActive)
            {
                this.displayCreateAccountScreen();
                this.isCreateAccountScreenActive = false;
            }
            else if (isCourseRegistrationScreenActive)
            {
                this.displayCourseRegistrationScreen();
                this.isCourseRegistrationScreenActive = false;
            }
            else if (isProfileScreenActive)
            {
                this.displayProfileScreen();
                this.isProfileScreenActive = false;;
            }
            else if (isNavigationOptionsScreenActive)
            {
                this.displayNavigationOptionsScreen();
                this.isNavigationOptionsScreenActive = false;
            }
            
        }
        
    }




    /*
    *********************************
    Private Utility Methods
    *********************************
    */


    /*
    -This private utility method is used to load the Course data from the Courses.txt database. The first line of Courses.txt contains
    the column header names. They can either be ignored or removed from the Courses.txt file. Each subsequent line in the Courses.txt file
    represents a Course object. This method will read in each line and create a new Course object. The new Course object will
    be added to this.courseList.
    -If the Courses.txt database does not exist, this method will terminate the program.
    -After this.courseList has been populated, sort the Courses so that it is in alphabetical order.
    -ASSIGNEE: Sam
    */
    private void loadCoursesData()
    {
        File courseFile;  
        Scanner fileScanner;  
        Course courseTemp; 
            
        try 
        {
            
            courseFile = new File("Courses.txt"); 
            fileScanner = new Scanner(courseFile, "UTF-8"); 
            fileScanner.useDelimiter("\",\"");
                                                    
            while(fileScanner.hasNextLine()) 
            { 
                String[] courseAttributes = fileScanner.nextLine().split("\",\""); 
                
            courseTemp = new Course(courseAttributes[0].trim(), courseAttributes[1], 
                    courseAttributes[2], 
                    courseAttributes[3],
                new Integer(courseAttributes[4]).intValue(), 
                new Integer(courseAttributes[5]).intValue(), 
                courseAttributes[6]); 
                
            this.courseList.add(courseTemp);
    
            }
        
            fileScanner.close(); 
        
        } 
        catch(FileNotFoundException e) 
        {  
            e.printStackTrace();
        } 
        catch(Exception e) 
        {
            e.printStackTrace();
        }
        
        //Sort courseList
        Collections.sort(this.courseList);
        
        /* DEBUG
        System.out.println("courseList Size: " + this.courseList.size());
        
        for (Course course: this.courseList)
        {
            System.out.println(course.getCourseName());
        }
        */


          
    }

    /*
    -This private utility method is used to load the Account data from AccountRecords.txt. If AccountRecords.txt does not already exist,
    it will be created. Each line in Account Records represents an Account object. This method will read in each line and create a
    new Account object. The new Account object will be stored in this.accountList.
    -ASSIGNEE: Noe
    */
    private void loadAccountsData()
    {
        //Check to see if AccountRecords.txt exists. If it doesn't, create it.
        File accountsFile = new File("AccountRecords.txt");
        if (accountsFile.exists() == false)
        {
            //Create the file
            try
            {
                PrintWriter pw = new PrintWriter("AccountRecords.txt","UTF-8");
                pw.close();
            }
            catch (IOException e)
            {
                System.out.println("Exception!");
            }
        }
        else //load in the data from AccountRecords.txt
        {
            try
            {
                scanner = new Scanner(accountsFile);
                while (scanner.hasNext())
                {

                    ArrayList<String> tempAccountArrayList = new ArrayList<String>();
                    ArrayList<String> tempCourseIDArrayList = new ArrayList<String>();
                    ArrayList<Course> tempCourseArrayList = new ArrayList<Course>();


                    //Read in a line.
                    lineScanner = new Scanner(scanner.nextLine());
                    lineScanner.useDelimiter(",");
                    //Read in firstName, lastName, age, gender, ssn, username, password, studentID
                    for (int ii = 0; ii < 8; ii++)
                    {
                        tempAccountArrayList.add(lineScanner.next());
                    }
                    //Read in the registered coursesIDs.
                    while (lineScanner.hasNext())
                    {
                        tempCourseIDArrayList.add(lineScanner.next());
                    }

                    //Populate tempCourseArrayList based on tempCourseIDArrayList
                    for (int ii = 0; ii < tempCourseIDArrayList.size(); ii++)
                    {
                        for (int jj = 0; jj < this.courseList.size(); jj++)
                        {
                            if (this.courseList.get(jj).getCourseID().equals(tempCourseIDArrayList.get(ii)))
                            {
                                tempCourseArrayList.add(this.courseList.get(jj));
                                break; //break out of the inner-loop and continue outer-loop
                            }
                        }
                    }

                    //Create the Account object and add to this.accountList
                    this.accountList.add(new Account(tempAccountArrayList.get(0),
                                                     tempAccountArrayList.get(1),
                                                     Integer.parseInt(tempAccountArrayList.get(2)),
                                                     tempAccountArrayList.get(3),
                                                     tempAccountArrayList.get(4),
                                                     tempAccountArrayList.get(5),
                                                     tempAccountArrayList.get(6),
                                                     tempAccountArrayList.get(7)));
                    this.accountList.get(this.accountList.size() - 1).setRegisteredCourses(tempCourseArrayList);
                    lineScanner.close();
                }
                scanner.close();

            }
            catch (IOException e)
            {
                System.out.println("Exception!");
            }

        }
    }


    /*
    -This private utility method will be used when a user selects to create a new account at the login screen. This method will be
    executed AFTER all the data has been read in from the user.
    -The first step will be to validate that the studentID does not already exist.
        -If the studentID already exists, an error will be presented to the user that the studentID already exists and will be taken back to the initial
        login screen.
        -If the studentID does not already exist, a new Account will be created and added to this.accountList. Then this method
        will add the new Account will be added as a new entry to AccountRecords.txt.
    -ASSIGNEE: Dave
    */
    private boolean createAccount(String firstName,
                               String lastName,
                               String age,
                               String gender,
                               String socialSecurityNumber,
                               String userName,
                               String password,
                               String studentID)

    {
        //Validate that studentID does not already exist
        //System.out.println("start validation....");
        for (Account account: accountList)
        {
            // System.out.println("AccountID: " + account.getID());
            // System.out.println("InputID: " + studentID);
            if (account.getID().equals(studentID))
            {
                System.out.println("\n");
                
                return false;
            }
        }
        
        //Create New Account
        Account newAccount = new Account(firstName,
                                         lastName,
                                         Integer.parseInt(age),
                                         gender,
                                         socialSecurityNumber,
                                         userName,
                                         password,
                                         studentID);
                                         
        //add new account to accountList
        this.accountList.add(newAccount);
        
        //Add new Account to AccountRecords.txt
        FileWriter fw = null;;
        try
        {
            File accountsFile = new File("AccountRecords.txt");
            
            fw = new FileWriter(accountsFile,true);
            String newAccountRecordEntry = newAccount.getFirstName() + ","
                                           + newAccount.getLastName() + ","
                                           + newAccount.getAge() + ","
                                           + newAccount.getGender() + ","
                                           + newAccount.getSocialSecurityNumber() + ","
                                           + newAccount.getUserName() + ","
                                           + newAccount.getPassword() + ","
                                           + newAccount.getID() + ",";
                                           
            fw.write(newAccountRecordEntry + "\r\n");
            fw.flush();
        }
        catch (IOException e)
        {
            System.out.println("IO Exception");
        }
        
        try
        {
            fw.close();
        }
        catch (IOException e)
        {
            System.out.println("IO Exception");
        }
        
        return true;
    }

    /*
    -This private utility method will be called from the displayLoginScreen() method when a user attempts to login. The displayLoginScreen() method will read
    in a userName and password and pass the values to this method. This method will validate that the userName and password pair is valid. if it is valid,
    it returns true, otherwise it returns false.
    -ASSIGNEE: Dave
    */
    private boolean loginUser(String userName, String password)
    {
        for (Account account: this.accountList)
        {
            if (account.getUserName().equals(userName) && account.getPassword().equals(password))
            {
                this.activeUser = account;
                return true;
            } 
        }
        
        return false; //no match for username/password pair.
    }    

    /*
    -This private utility method attempts to register a Course for the user. Based on the courseID provided, this method will call the
    activeUser.addCourse() method.
    -The addCourse() method will return true if the registration is successful and false if it is not.
    -This method will return the same true/false value.
    -ASSIGNEE: John
    */
    private boolean registerCourse(String courseID) {
	        boolean isCourseInArrayList = false;
	        for (Course courseIdentification : courseList) {
	            if (courseIdentification.getCourseID().equals(courseID)) {
	                isCourseInArrayList = true;
	                break;
	            }
	        }
	        if (isCourseInArrayList == false) {
	            return isCourseInArrayList;
	        }
	        //get course from courselist
	        Course theCourse = null;
	        for (Course course : courseList) {
	            if (course.getCourseID().equals(courseID)) {
	                theCourse = course;
	            }
	        }
	        return activeUser.addCourse(theCourse);
    }

    /*
    -This private utility method attempts to unregister a Course for the user. Based on the courseID provided, this method will call the
    activeUser.removeCourse() method.
    -The removeCourse() method will return true if the registration is successful and false if it is not.
    -This method will return the same /true/false value.
    -ASSIGNEE: Noe
    */
    private boolean unRegisterCourse(String courseID)
    {
        Course selectedCourse = null;
        //Get Course based on courseID
        for (int ii = 0; ii < this.courseList.size(); ii++)
        {
            if (this.courseList.get(ii).getCourseID().equals(courseID))
            {
                selectedCourse = this.courseList.get(ii);
                break; //break for loop
            }
        }

        if (selectedCourse == null)
        {
            //If execution gets to this point, that means a courseID was not matched with a course in this.courseList. Return false
            //because the courseID is not recognized by the system.
            return false;
        }

        //Call removeCourse() from Account class and return success or failure.
        return this.activeUser.removeCourse(selectedCourse);
    }

    /*
    -This private utility method displays the initial screen to the user.
    -It may have a welcome message such as "Welcome to the Course Registration System"
    -It will prompt the user if he/she is an existing user.
        -If yes, the user will be prompted to enter userName and password, then this.loginUser() will be called.
        -If no, the user will be taken to the createAccountScreen(
    -If this.loginUser() returns false, then the user is prompted to reenter the userName and password.
    -If this.loginUser() returns true, then the user is presented with the following navigation options:
        -1 Course Registration
        -2 Profile
        -3 Logout
    -If the user selects "1" then display the Course Registration Screen.
    -If the user selects "2" then display the Profile Screen.
    -If the user selects "3" then display the Login Screen.
    -ASSIGNEE: Dave
    */
    private void displayLoginScreen()
    {
        
        //Ask User if existing user. Validate that the command is correct.
        boolean validCommand = false;
        String userInput = "";
        while (!validCommand)
        {
            //Opening statement
            System.out.println("Navigation Options: \n");
            System.out.println("       1 Login");
            System.out.println("       2 Create New Account");
            System.out.println("       3 Quit Application\n\n");
            System.out.print("Enter Your Option (1, 2, 3): ");
            userInput = in.next();
            userInput = userInput.toUpperCase();
            
            
            if (userInput.equals("1") || userInput.equals("2") || userInput.equals("3"))
            {
                validCommand = true;
            }
            else
            {
                System.out.println("ERROR: INVALID COMMAND. Please try again.");
            }
            
        }
        
        
        
        if (userInput.equals("1"))
        {
            //Read in userName and password for the existing user and validate. If after three tries the user is incorrect,
            //the user will be redirected the Create Account Screen.
            validCommand = false; //set to false
            int failedAttempts = 0;
            while (!validCommand)
            {
        	    System.out.println("Please enter your Username: ");
        	    String userNameInput = in.next();
        	    System.out.println("Please enter your Password: ");
        	    String passwordInput = in.next();
                
                boolean isLoginSuccessful = loginUser(userNameInput,passwordInput);
                if (isLoginSuccessful)
                {
                    
                    System.out.print("\033[H\033[2J"); //clear screen
                    System.out.flush();
                    System.out.println("Login Successful! \n");

                    validCommand = true;
                    this.isNavigationOptionsScreenActive = true;
                }
                else if (!isLoginSuccessful && failedAttempts < 3) //isLoginSuccessful == false
                {
                    System.out.println("Invalid Username/Password pair. Please try again.");
                    failedAttempts++;
                }
                else //Three straight failed attempts.
                {
                    this.clearScreen();
                    System.out.println("ERROR: Consecutive failed attempts. Navigating to Create Account Screen.");
                    validCommand = true;
                    this.isCreateAccountScreenActive = true;
                }
            }
             
        }
        else if (userInput.equals("2"))
        {
            //navigate the user to Create Account Screen.
            this.isCreateAccountScreenActive = true;
        }
        else //userInputs.equals("3") is true.
        {
            this.quitApplication();
        }
        
        //in.close(); //close scanner used for user input.
        
    }

    /*
    -This private utility method displays the "Create Account" screen after the user has prompted that they do not have an existing account.
    -This method will read in firstName, lastName, age, gender, socialSecurityNumber, userName, password, and studentID
    -Once the data has been acquired, the data will be passed to the this.createAccount() method.
    -ASSIGNEE: Dave
    */
    private void displayCreateAccountScreen()
    {
        boolean validCommand = false;
        int failedAttempts = 0;
        String userNameInput = null;;
        String passwordInput = null;;
        String firstNameInput = null;;
        String lastNameInput = null;;
        String ageInput = null;;
        String genderInput = null;;
        String socialSecurityNumberInput = null;;
        String studentIDInput = null;

        while (!validCommand)
        {            
            System.out.println("Create New Account \n");
            
            try
            {
                boolean validDataFormat = false;

                //username
                System.out.println("UserName: ");
                userNameInput = in.next();
                
                //password
                System.out.println("Password: ");
                passwordInput = in.next();
                
                //first name
                System.out.println("First Name: ");
                firstNameInput = in.next();
                
                //last name
                System.out.println("Last Name: ");
                lastNameInput = in.next();
                
                
                while (!validDataFormat)
                {
                    //age
                    System.out.println("Age: ");
                    ageInput = in.next();
                    
                    //Validate age is an INT
                    try
                    {
                        int ageValidated = Integer.parseInt(ageInput);
                        validDataFormat = true;    
                    }
                    catch (NumberFormatException e)
                    {
                        System.out.println("Invalid Age input. Must be a number. Try again.");
                    }
                }
                
                
                validDataFormat = false;
                while (!validDataFormat)
                {
                    //gender
                    System.out.println("Gender (M/F): ");
                    genderInput = in.next();
                    //Validate gender is M or F
                    if (!genderInput.toUpperCase().equals("M") && !genderInput.toUpperCase().equals("F"))
                    {
                        System.out.println("Invalid Gender Input. Try again.");
                    }
                    else //valid data
                    {
                        validDataFormat = true;
                    }
                }
                
                validDataFormat = false;
                while (!validDataFormat)
                {
                    //SSN
                    System.out.println("Social Security Number (XXXXXXXXX): ");
                    socialSecurityNumberInput = in.next();
                    
                    //Validate SSN format
                    if (String.valueOf(socialSecurityNumberInput).length() == 9)
                    {
                        try
                        {
                            int ssnValidated = Integer.parseInt(socialSecurityNumberInput);
                            validDataFormat = true;
                        }
                        catch (NumberFormatException e)
                        {
                            System.out.println("Invalid SSN Input. Must be a number of the format XXXXXXXXX. Try again.");
                        }
                    }
                    else
                    {
                        System.out.println("Invalid SSN Input. Must be a number of the format XXXXXXXXX. Try again.");
                    }
                }
                
                
                validDataFormat = false;
                while (!validDataFormat)
                {
                    //Student ID
                    System.out.println("Student ID (XXXXX): ");
                    studentIDInput = in.next();
                    
                    //Validate Student ID
                    if (String.valueOf(studentIDInput).length() == 5)
                    {
                        try
                        {
                            int studentIDValidated = Integer.parseInt(studentIDInput);
                            validDataFormat = true;
                        }
                        catch (NumberFormatException e)
                        {
                            System.out.println("Invalid Student ID Input. Must be a number of the format XXXXX. Try again.");
                        }
                    }
                    else
                    {
                        System.out.println("Invalid Student ID Input. Must be a number of the format XXXXX. Try again.");
                    }
                }
                
            
            
            }
            catch (InputMismatchException e)
            {
                System.out.println("Input Mismatch Exception");
            }
                                    
            //Attempt to create account
            boolean isAccountCreationSuccessful = this.createAccount(firstNameInput,
                                                                lastNameInput,
                                                                ageInput,
                                                                genderInput,
                                                                socialSecurityNumberInput,
                                                                userNameInput,
                                                                passwordInput,
                                                                studentIDInput);
                                             
            if (isAccountCreationSuccessful)
            {
                this.clearScreen();
                
                System.out.println("\n Account Creation Successful!");
                validCommand = true;
                this.isLoginScreenActive = true;
            }
            else if (!isAccountCreationSuccessful && failedAttempts < 2)
            {
                this.clearScreen();
                
                System.out.println("Student ID already exists. Please try again");
                failedAttempts++;
            }
            else //three straight failed attempts
            {
                this.clearScreen();
                
                System.out.println("ERROR: Consecutive failed attempts. Redirecting to Login Screen");
                validCommand = true;
                this.isLoginScreenActive = true;
                
            }
            

           
        }
        

        
        //in.close();
        
        
    }

    /*
    -This private utility method will display the "Course Registration" screen on successful login to the application and user selection.
    -This screen lists out all of the available courses from this.courseList.
    -This method will read in a courseID for the course in which the user intends to register to and pass it to this.registerCourse()
    -this.registerCourse() will return true or false depending on if the registration was successful or it failed.
        -If true is returned, display this.displayCourseRegistrationStatusScreen() with a successful Registration message.
        -If false is returned, display this.displayCourseRegistrationStatusScreen() with a failure Registration message.
    -ASSIGNEE: Alberto
    */
    private void displayCourseRegistrationScreen()
    {
		System.out.println("\n");

        /*
        -Neater Course List
        */
        for (Course course: this.courseList)
        {
            System.out.println(course.toString() + "\n");
        }

		// for(Course course: courseList) {
		// 	System.out.println(course.getCourseName() + " " + course.getCourseID() + "      " + course.getStartDate() + "    " +
		// 					   course.getEndDate() + "  " + course.getEnrollmentLimit() +  "        " + course.getNumEnrolled());
		// }


        System.out.println();
        System.out.println("       1 Register For a Course");
        System.out.println("       2 Show Navigation Options\n");
        System.out.print("Enter Your Option (1, 2): ");


		boolean validCommand = false;
		String userInput = "";

		while (validCommand == false)
		{
			userInput = in.next();
		 	if (userInput.equals("1") || userInput.equals("2"))
		 		{ validCommand = true; }
		 	else
		 		{ System.out.print("Invalid Option, please try again: "); }
		}
        
        if (userInput.equals("1"))
        {
            System.out.print("\n\nPlease enter a Course ID (XXXX): ");
            String courseID = in.next();
            this.clearScreen();

            //return true, registration was successful. Otherwise, return false.
            boolean registeringCourse = registerCourse(courseID);
            displayCourseRegistrationStatusScreen(registeringCourse);
        }
        else //userInput.equals("2") is true
        {
            this.clearScreen();
            this.isNavigationOptionsScreenActive = true;
        }
	}

    /*
    -This private utility method will display the "Course Registration Status" screen which informs the user if a registration is
    successful or if it failed.
    -After the message is displayed to the user, the user must "Enter any Key" to confirm. Then the user is displayed with navigation options.
        -1 Course Registration
        -2 Profile
        -3 Logout
    -If the user selects "1" then display the Course Registration Screen.
    -If the user selects "2" then display the Profile Screen.
    -If the user selects "3" then display the Login Screen.
    -ASSIGNEE: Alberto
    */
    private void displayCourseRegistrationStatusScreen(boolean registeringCourse)
    {
        
        this.clearScreen();

        
		if (registeringCourse) {
			System.out.println("\n\n");
			System.out.println("       --------------------------------         ");
			System.out.println("   *** YOUR REGISTRATION WAS SUCCESSFUL   ***   ");
		    System.out.println("       --------------------------------         ");
		}
		else {
			System.out.println("\n\n");
		    System.out.println("       ------------------------------------         ");
		    System.out.println("   *** YOUR REGISTRATION WAS NOT SUCCESSFUL   ***   ");
		    System.out.println("       ------------------------------------         ");
		}
        
        isNavigationOptionsScreenActive = true;
        

    }

    /*
    -This private utility method will display the "Course UnRegistration Status" screen which informs the user if an unregistration is
    successful or if it failed.
    -After the message is displayed to the user, the user must "Enter any Key" to confirm. Then the user is displayed with navigation options.
        -1 Course Registration
        -2 Profile
        -3 Logout
    -If the user selects "1" then display the Course Registration Screen.
    -If the user selects "2" then display the Profile Screen.
    -If the user selects "3" then display the Login Screen.
    -ASSIGNEE: Alberto
    */
	private void displayCourseUnRegistrationStatusScreen(boolean unRegisteringCourse){
        this.clearScreen();
        
        if (unRegisteringCourse) {
			System.out.println("\n\n");
			System.out.println("       -----------------------------------         ");
			System.out.println("   *** YOUR UN-REGISTRATION WAS SUCCESSFUL   ***   ");
			System.out.println("       -----------------------------------         ");
		}
		else {
			System.out.println("\n\n");
			System.out.println("       ------------------------------------         ");
			System.out.println("   *** YOUR UN-REGISTRATION WAS NOT SUCCESSFUL   ***   ");
			System.out.println("       ------------------------------------         ");
		}
        
        isNavigationOptionsScreenActive = true;
        

	}

    /*
    -This private utility method will display the this.activeUser Profile Screen.
    -This screen will display this.activeUser's data by calling this.activeUser.toString()
    -This screen will display a list of all courses that this.activeUser is registered to using this.activeUser.getRegisteredCourses()
    -This method will read in a courseID from the user for the course in which the user intends to unregister, and then pass it to this.unRegisterCourse()
    -this.unRegisterCourse() will return true or false depending on if the unregistration was successful or it failed.
        -If true is returned, display this.displayCourseRegistrationStatusScreen() with a successful Unregistration message.
        -If false is returned, display this.displayCourseRegistrationStatusScreen() with a failure unregistration message.
    -ASSIGNEE: Alberto
    */
    private void displayProfileScreen()
    {
        //Scanner in = new Scanner(System.in);
        
		System.out.println();
		System.out.println("   STUDENT RECORD: ");
		System.out.println("   --------------- ");
		System.out.println(activeUser.toString());
		System.out.println("   Student ID: " + this.activeUser.getID() + "\n");

		ArrayList<Course> registeredCourses = activeUser.getRegisteredCourses();

		System.out.println("   REGISTERED IN THE FOLLOWING COURSE(S):");
		System.out.println("   --------------------------------------");

		for(Course course: registeredCourses) {
			System.out.println("   Course Name: " + course.getCourseName() + "    Course ID: " + course.getCourseID());
			System.out.println("          Start Date: " + course.getStartDate() +
							   "    End Date: " + course.getEndDate() + "    Capacity: " + course.getEnrollmentLimit() +
							   "    Enrolled: " + course.getNumEnrolled());
			System.out.println("         Description: " + course.getDescription() + "\n");
		}
			System.out.println();
			System.out.println("       1 Unregister For a Course");
			System.out.println("       2 Go Back to Course Registration\n");
			System.out.print("Enter Your Option (1, 2): ");

		boolean validCommand = false;
		String userInput = "";

		while (validCommand == false)
		{
			userInput = in.next();
		 	if (userInput.equals("1") || userInput.equals("2"))
		 		{ validCommand = true; }
		 	else
		 		{ System.out.print("Invalid Option, please try again: "); }
		}

		int userIn = Integer.valueOf((String) userInput);

	 	if (userIn == 2) {
			this.clearScreen();
		 	this.isCourseRegistrationScreenActive = true;
		}

		if (userIn == 1) {
			System.out.print("\nPlease Enter Course ID (XXXX): ");
		    String courseID = in.next();
            
            this.clearScreen();

			System.out.println();
			System.out.println("   STUDENT RECORD: ");
			System.out.println("   --------------- ");
			System.out.println(activeUser.toString());
			System.out.println("   Student ID: " + activeUser.getID() + "\n");

		 	boolean unRegisteringCourse = unRegisterCourse(courseID);
			displayCourseUnRegistrationStatusScreen(unRegisteringCourse);
		}
    }
    /*
    -Display the Navigation Options Screen to the user with Options 1-3.
    */
    private void displayNavigationOptionsScreen()
    {
        System.out.println("\n\n");
        System.out.println("Navigation Options: \n");
        System.out.println("       1 Course Registration");
        System.out.println("       2 Profile");
        System.out.println("       3 Logout");
        System.out.println("       4 Quit Application\n\n");
        System.out.print("Enter Your Option (1, 2, 3, 4): ");

        boolean validCommand = false;
        String userInput = "";

        while (validCommand == false)
        {
            userInput = in.next();
            if (userInput.equals("1") || userInput.equals("2") || userInput.equals("3") || userInput.equals("4"))
                { validCommand = true; }
            else
                { System.out.print("Invalid Option, please try again: "); }
        }

        int userIn = Integer.valueOf((String) userInput);

        if (userIn == 1) {
            this.clearScreen();
            this.isCourseRegistrationScreenActive = true;
        }

        if (userIn == 2) {
            this.clearScreen();
            this.isProfileScreenActive = true;
        }

        if (userIn == 3) {
            this.clearScreen();
            System.out.println(this.activeUser.getUserName() + " was logged out.");
            this.isLoginScreenActive = true;
        }
        
        if (userIn == 4) {
            this.quitApplication();
        }
    }
    
    /*
    -Clears previous screen
    */
    private void clearScreen()
    {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    
    /*
    -Quit application
    */
    private void quitApplication()
    {
        //Close in Scanner
        in.close();
        
        this.clearScreen();
        System.out.println("Goodbye!");
        System.exit(0);
        
        
    }

}