public class Person implements Comparable<Person> {

    //Private Instance Variables
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private String socialSecurityNumber;


    //Constructor with parameters
    public Person(String firstName,
                  String lastName,
                  int age,
                  String gender,
                  String socialSecurityNumber)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.socialSecurityNumber = socialSecurityNumber;
    }

    //Set methods
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public void setSocialSecurityNumber(String ssn)
    {
        this.socialSecurityNumber = ssn;
    }

    //Get methods
    public String getFirstName()
    {
        return this.firstName;
    }

    public String getLastName()
    {
        return this.lastName;
    }
    public String getFullName()
    {
        String result = this.firstName + " " + this.lastName;
        return result;
    }

    public int getAge()
    {
        return this.age;
    }

    public String getGender()
    {
        return this.gender;
    }

    public String getSocialSecurityNumber()
    {
        return this.socialSecurityNumber;
    }

    public void talk()
    {
        System.out.println("Why hello there! How are you doing on this fine day?");
    }


    //CompareTo override for sorting by Last Name
    public int compareTo(Person person)
    {
        return (this.lastName.compareTo(person.getLastName()));
    }





    //Print out all private instance variable values
    public String toString()
    {
        String result = "   First Name: " + getFirstName() + "\n" +
                        "    Last Name: " + getLastName() + "\n" +
                        "          Age: " + getAge() + "\n" +
                        "       Gender: " + getGender() + "\n" +
                        "          SSN: " + getSocialSecurityNumber() + "\n";

        return result;
    }

}