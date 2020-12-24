import com.iocoder.demo01.democlass.serialiseandlombok.Employee;
import com.iocoder.demo01.democlass.serialiseandlombok.User;
import lombok.Cleanup;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.*;

public class UserTest {
    @Test
    public void testSerialise(){
        Employee user = new Employee("ab", 20, 2, 1000);
        String filename = "shubham.txt";

        // Serialization
        try {

            // Saving of user in a file
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(file);

            // Method for serialization of user
            out.writeObject(user);

            out.close();
            file.close();

            System.out.println("Object has been serialized\n"
                    + "Data before Deserialization.");
            Employee.printdata(user);

            // value of static variable changed
            user.b = 2000;
        }

        catch (IOException ex) {
            System.out.println("IOException is caught");
        }

        user = null;

        // Deserialization
        try {

            // Reading the user from a file
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of user
            user = (Employee)in.readObject();

            in.close();
            file.close();
            System.out.println("Object has been deserialized\n"
                    + "Data after Deserialization.");
            Employee.printdata(user);

            // System.out.println("z = " + object1.z);
        }

        catch (IOException ex) {
            System.out.println("IOException is caught");
        }

        catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException" +
                    " is caught");
        }
    }

    @Test
    public void testExternalizable(){
  //      User.printdata(null);

        //Can only have default constructor by using Externalizable interface
    //    User user = new User("ab", "M", "35");
        User user = new User();
        user.setUserName("ab");
        user.setGender("M");
        user.setAge("35");
        String filename = "shubham.txt";

        // Serialization
        try {

            // Saving of user in a file
            @Cleanup FileOutputStream file = new FileOutputStream(filename);
            @Cleanup ObjectOutputStream out = new ObjectOutputStream(file);

            // Method for serialization of user
            user.writeExternal(out);

            System.out.println("Object has been serialized\n"
                    + "Data before Deserialization.");
            System.out.println(user.toString());
        }

        catch (IOException ex) {
            System.out.println("IOException is caught");
        }

        User newUser = new User();

        // Deserialization
        try {

            // Reading the user from a file
            @Cleanup FileInputStream file = new FileInputStream(filename);
            @Cleanup ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of user
            newUser.readExternal(in);

            System.out.println("Object has been deserialized\n"
                    + "Data after Deserialization.");
            System.out.println(user.toString());
        }

        catch (IOException ex) {
            System.out.println("IOException is caught");
        }

        catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException" +
                    " is caught");
        }

        assertTrue(user.equals(newUser));
    }
}