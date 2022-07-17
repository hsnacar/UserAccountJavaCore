package Utility;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @author Hasan Acar
 */
public class Utility {

    public static boolean isFileExists(String filename, String extension) {
        File readFile = new File(filename + "." + extension);
        return readFile.exists();
    }

    public static String inputNext() {
        Scanner input = new Scanner(System.in);
        String inputString = null;
        do {
            inputString = input.next();
        } while (inputString == null || inputString.equals(""));
        return inputString;
    }

    public static boolean isEmailProvider(String provider) {
        String[] approvedProviders = {"hotmail", "gmail", "yandex", "yahoo", "outlook"};
        for (String approvedProvider : approvedProviders) {
            if (approvedProvider.equals(provider)) return true;
        }
        return false;
    }

    public static String usernameValidator(String inputUsername) {
        String errorMessage = "";
        if (inputUsername.replaceAll("[A-Za-z0-9]", "").length() > 0) {
            errorMessage += " special character is not allowed /";
        } else if (!((inputUsername.charAt(0) > 64 && inputUsername.charAt(0) < 91) || (inputUsername.charAt(0) > 96 && inputUsername.charAt(0) < 123))) {
            errorMessage += " username cannot start with a number /";
        } else if (inputUsername.length() < 6) {
            errorMessage += " username must be 6 characters or longer /";
        }
        return errorMessage;
    }

    // https://en.wikipedia.org/wiki/Email_address


    public static String passwordValidator(String password) {
        String errorMessage = "";
        if (password.length() < 8) errorMessage += " only one dot is allowed /";
        else if (password.replaceAll("[^A-Z]", "").length() == 0) errorMessage += " only one dot is allowed /";
        else if (password.replaceAll("[^a-z]", "").length() == 0) errorMessage += " only one dot is allowed /";
        else if (password.replaceAll("[^0-9]", "").length() == 0) errorMessage += " only one dot is allowed /";
        else if (password.replaceAll("[^@#$%.<>,&+=]", "").length() == 0) errorMessage += " only one dot is allowed /";

        return errorMessage;
    }

    public static String emailValidator(String inputEmail) {
        String localPart = "", domain = "", domainExtension = "";
        String errorMessage = "";
        if (inputEmail.split("@").length != 2) {
            errorMessage += " only one @ is allowed /";
        }
        try {
            localPart = inputEmail.split("@")[0];
            domain = inputEmail.split("@")[1].split("[.]")[0];
            domainExtension = inputEmail.split("@")[1].split("[.]")[1];
        } catch (Exception e) {

        }
        if (!Utility.isEmailProvider(domain)) errorMessage += " unsupported provider /";
        else if (domain.length() > 63) errorMessage += " provider is longer than 63 characters /";
        else if (localPart.length() > 64 || localPart.length() < 1)
            errorMessage += " mail address is longer than 64 characters /";
        else if (inputEmail.contains("..")) errorMessage += " only one dot is allowed /";
        else if (localPart.charAt(0) == '.') errorMessage += " first character cannot be a dot /";
        else if (localPart.charAt(localPart.length() - 1) == '.') errorMessage += " last character cannot be a dot /";
        else if (inputEmail.contains(" ")) errorMessage += " mail address cannot have spaces /";
        else if (domainExtension.length() < 2) errorMessage += " mail address extension not correct /";
        return errorMessage;
    }

    public static String encodeSHA256(String str) {
        MessageDigest crypto = null;
        try {
            crypto = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException ignored) {

        }
        assert crypto != null;
        crypto.update(str.getBytes());
        byte[] encrypted = crypto.digest();
        StringBuilder passwordEncode = new StringBuilder();
        for (byte b : encrypted) {
            passwordEncode.append(Integer.toHexString(0xFF & b));
        }
        return String.valueOf(passwordEncode);
    }

    public static String generateUUID() {
        return String.valueOf(UUID.randomUUID());
    }

    public static String getId(String filename, String extension, String password) {
        String id = null;
        String passwordFromFile = null;
        FileReader readId = null;
        try {
            readId = new FileReader(filename + "." + extension);
        } catch (FileNotFoundException e) {
            return null;
        }
        BufferedReader readIdBuffer = new BufferedReader(readId);
        String line;
        while (true) {
            try {
                if (((line = readIdBuffer.readLine()) == null)) break;
            } catch (IOException e) {
                return null;
            }
            if (line.contains("password=")) {
                passwordFromFile = line.split("=")[1];
            }
            if (line.contains("id=")) {
                id = line.split("=")[1];
            }
        }
        try {
            readIdBuffer.close();
        } catch (IOException e) {

        }

        if (passwordFromFile.equals(password)) {
            return id;
        } else return null;

    }
}
