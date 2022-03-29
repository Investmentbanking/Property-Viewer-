import com.opencsv.CSVReader;

import java.io.FileWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.File;
import java.io.FileReader;

public class Login {
    private String username;                                                            // The username of the user
    private String password;                                                            // The password of the user
    private final URL url = this.getClass().getResource("users.csv");             // The file that stores the usernames acts as a database
    private final String file = new File(url.toURI()).getAbsolutePath();                // The actual path to the file, done like this to avoid repetition

    /**
     * The main constructor of the Login class. Sets the username and password fields to
     * their corresponding values
     *
     * @param username the username
     * @param password the password
     * @throws URISyntaxException when the file can't be found, this is when the file doesn't exist
     */
    public Login(String username, String password) throws URISyntaxException {
        this.username = username;
        this.password = password;
    }

    /**
     * A simple method to check if a user has already been saved and therefore
     * can be considered an "existing user"
     *
     * @return true if this user exists, false otherwise
     */
    public boolean checkLogin(){
        try {
            CSVReader reader = new CSVReader(new FileReader(file));
            String[] line;
            //skip the first row (column headers)
            reader.readNext();
            while ((line = reader.readNext()) != null) {
                String username = line[1];

                if(username.equals(this.username)){
                    return true;
                }
            }
            return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * A simple method to save the user to the file. When saving a user, an automatic id will be
     * assigned to the user so that we can distinguish users with specific ids.
     * Additionally, no two users with the same name are allowed.
     *
     * @return true when a new user is saved to the file, false otherwise.
     */
    public boolean makeLogin(){
        try {
            CSVReader reader = new CSVReader(new FileReader(file));
            FileWriter writer = new FileWriter(file, true);

            String[] line;
            //skip the first row (column headers)
            reader.readNext();
            int lastID = 0;
            while ((line = reader.readNext()) != null) {
                lastID = convertID(line[0]);
                String usernameInDB = line[1];
                if(usernameInDB.equals(this.username)){
                    return false;
                }
            }

            String newUser = (lastID+1) + "," + this.username + "," + hash(this.password) + '\n';

            writer.write(newUser);
            writer.close();
            return true;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * A simple method to convert an id  string representation to a numeric one.
     *
     * @param id the string representation of the id
     * @return a numeric representation of the id
     */
    private int convertID(String id){
        if(id != null && !id.trim().equals("")){
            return Integer.parseInt(id);
        }
        return 0;
    }

    /**
     * A simple method to hash a given password by first adding a string to it for security.
     * The hashing algorithm used is SHA-256
     *
     * @param password the password we wish to hash
     * @return the hashed password
     */
    private String hash(String password) throws NoSuchAlgorithmException {
        password = password + "cYM!HAhJ7T@ETTS8CT&5PgG6ye8TBYFEjs!efr7P";     // first salt the password
        MessageDigest hash = MessageDigest.getInstance("SHA-256");            // Create MessageDigest instance for SHA-256
        hash.update(password.getBytes());                                     // Add password bytes to digest
        byte[] bytes = hash.digest();                                         // Get the hash's bytes

        // bytes[] has bytes in decimal format. Convert it to hexadecimal format
        StringBuilder string = new StringBuilder();
        for (byte aByte : bytes) {
            string.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
        }

        // Get complete hashed password in hex format
        return string.toString();
    }
}
