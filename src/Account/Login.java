package Account;

import Utility.Utility;

/**
 * @author Hasan Acar
 */
public class Login {
    public static String checkLogin(String usernameOrEmail, String password) {
        String passwordEncoded = Utility.encodeSHA256(password);
        if (Utility.isFileExists(usernameOrEmail, Register.fileExtension)) {
            return Utility.getId(usernameOrEmail, Register.fileExtension, passwordEncoded);
        } else {

            return null;
        }
    }
}
