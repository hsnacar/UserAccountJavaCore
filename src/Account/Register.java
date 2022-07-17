package Account;

import Utility.Utility;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Hasan Acar
 */
public class Register {

    private String id;


    private String password;
    private String username;
    private String email;
    public static final String fileExtension = "dat";

    public Map<String, Boolean> checkAndCreateNewAccount(String username, String password, String email) {
        Map<String, Boolean> result = new HashMap<>();
        if (Utility.isFileExists(username, fileExtension)) {
            result.put(username, true);
        } else {
            result.put(username, false);
        }
        if (Utility.isFileExists(email, fileExtension)) {
            result.put(email, true);
        } else {
            result.put(email, false);
        }
        if (Utility.passwordValidator(password) == "") {
            result.put(password, true);
        } else {
            result.put(password, false);
        }
        if (!result.get(username) && !result.get(email) && result.get(password)) {
            this.id = Utility.generateUUID();
            this.username = username;
            this.email = email;
            this.password = Utility.encodeSHA256(password);
            saveUser();
        }
        return result;
    }

    private void saveUser() {
        try {
            FileWriter usernameFW = new FileWriter(this.username + "." + fileExtension, true);
            BufferedWriter usernameBuffer = new BufferedWriter(usernameFW);
            usernameBuffer.append("password=" + this.password);
            usernameBuffer.newLine();
            usernameBuffer.append("id=" + this.id);
            usernameBuffer.newLine();
            usernameBuffer.append("username=" + this.username);
            usernameBuffer.newLine();
            usernameBuffer.append("email=" + this.email);
            usernameBuffer.close();
        } catch (IOException e) {
            System.err.println("file could not be created");
        }
        // Backup data (mail file)
        try {
            FileWriter emailFW = new FileWriter(this.email + "." + fileExtension, true);
            BufferedWriter emailFWBuffer = new BufferedWriter(emailFW);
            emailFWBuffer.append("password=" + this.password);
            emailFWBuffer.newLine();
            emailFWBuffer.append("id=" + this.id);
            emailFWBuffer.newLine();
            emailFWBuffer.append("username=" + this.username);
            emailFWBuffer.newLine();
            emailFWBuffer.append("email=" + this.email);
            emailFWBuffer.close();
        } catch (IOException e) {
            System.err.println("file could not be created");
        }
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
