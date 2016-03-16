import java.io.File; 
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections; 
import java.util.Scanner;  

public class CourseDemo 
{ 
  
    public static void main (String[] args) 
    {
	File courseFile;  
	Scanner fileScanner;  
	Course courseTemp; 
	ArrayList<Course> courseData = new ArrayList<Course>();
	
	try {
	    
	    courseFile = new File("CourseDatabase.txt"); 
	    fileScanner = new Scanner(courseFile, "UTF-8"); 
	    fileScanner.useDelimiter(",");
                                                
	    while(fileScanner.hasNextLine()) { 
	    	   String[] courseAttributes = fileScanner.nextLine().split(","); 
	    	
	    courseTemp = new Course(courseAttributes[0].trim(), courseAttributes[1], 
	    		 courseAttributes[2], 
	    		 courseAttributes[3],
	    	   new Integer(courseAttributes[4]).intValue(), 
	    	   new Integer(courseAttributes[5]).intValue(), 
	    	   courseAttributes[6]); 
	    	   
           courseData.add(courseTemp);
           //System.out.println(courseTemp.toString());
  
	}
		
		for (Course course: courseData) {
			//System.out.println(course.toString());
	}
		
	    fileScanner.close(); 
	
	} catch(FileNotFoundException e) {  
	    e.printStackTrace();
	} catch(Exception e) {
		 e.printStackTrace();
	}


        
}
}
