import com.opencsv.CSVReader;

import java.io.FileWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.File;
import java.io.FileReader;

public class Login {
    private String username;
    private String password;
    private final URL url = this.getClass().getResource("users.csv");
    private final String file = new File(url.toURI()).getAbsolutePath();

    public Login(String username, String password) throws URISyntaxException {
        this.username = username;
        this.password = password;
    }

    public boolean checkLogin(){
        try {
            CSVReader reader = new CSVReader(new FileReader(file));
            String[] line;
            //skip the first row (column headers)
            reader.readNext();
            while ((line = reader.readNext()) != null) {
                String username = line[1];
                String password = line[2];

                if(username.equals(this.username) && password.equals(hash(this.password))){
                    return true;
                }
            }
            return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean makeLogin(){
        try {
            CSVReader reader = new CSVReader(new FileReader(file));
            System.out.println(file);

            FileWriter writer = new FileWriter(file, true);

            String[] line;
            //skip the first row (column headers)
            reader.readNext();
            int lastID = 0;
            while ((line = reader.readNext()) != null) {
                lastID = convertID(line[0]);
                String usernameInDB = line[1];
                System.out.println(usernameInDB);
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

    private int convertID(String id){
        if(id != null && !id.trim().equals("")){
            return Integer.parseInt(id);
        }
        return 0;
    }

    /**
     * https://howtodoinjava.com/java/java-security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/
     * @param password
     * @return
     */
    private String hash(String password) throws NoSuchAlgorithmException {
        // first salt the password
        password = password + "cYM!HAhJ7T@ETTS8CT&5PgG6ye8TBYFEjs!efr7P";

        // Create MessageDigest instance for MD5
        MessageDigest md = MessageDigest.getInstance("MD5");

        // Add password bytes to digest
        md.update(password.getBytes());

        // Get the hash's bytes
        byte[] bytes = md.digest();

        // This bytes[] has bytes in decimal format. Convert it to hexadecimal format
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
        }

        // Get complete hashed password in hex format
        return sb.toString();
    }

    public static void main(String[] args) throws URISyntaxException, NoSuchAlgorithmException {
        Login log = new Login("mac", "passwor");
        System.out.println(log.makeLogin());
        System.out.println(log.checkLogin());
        System.out.println(log.hash("root"));
    }
}
