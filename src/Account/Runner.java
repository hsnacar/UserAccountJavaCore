package Account;

import Utility.Utility;

import java.util.*;

/**
 * @author Hasan Acar
 */
public class Runner {
    private static String username = null;
    private static String id = null;
    private static String email = null;
    private static String password = null;
    private static boolean loginStatus = false;
    private static boolean exit = false;
    private static boolean registerCancel = false;
    private static Map<Character, String> mainmenu = new LinkedHashMap<>();

    public static void main(String[] args) {
        mainmenuOptions();
        do {
            mainmenu();
        } while (!exit);
    }

    public static void mainmenuOptions() {
        mainmenu.clear();
        if (!loginStatus) mainmenu.put('r', "(R)egister");
        if (!loginStatus) mainmenu.put('g', "Lo(g)in");
        if (!loginStatus) mainmenu.put('c', "Re(c)overy");
        if (loginStatus) mainmenu.put('p', "(P)rofile");
        if (loginStatus) mainmenu.put('l', "(L)ogout");
        mainmenu.put('h', "(H)elp");
        mainmenu.put('x', "E(x)it");
        System.out.println("___---===== Account System =====---___");
        mainmenu.forEach((k, v) -> System.out.println("  " + v));
    }

    public static void mainmenu() {
        System.out.println("\nPlease select a option: ");
        char pick = Utility.inputNext().toLowerCase().charAt(0);
        if (!loginStatus) {
            switch (pick) {
                case 'r':
                    register();
                    break;
                case 'g':
                    login();
                    break;
                case 'c':
                    recovery();
                    break;
                case 'h':
                    help();
                    break;
                case 'x':
                    exit();
                    break;
                default:
            }
        }

        if (loginStatus) {
            switch (pick) {
                case 'l':
                    logout();
                    break;
                case 'p':
                    profile();
                    break;
                case 'h':
                    help();
                    break;
                case 'x':
                    exit();
                    break;
                default:
            }
        }

        if (pick != 'x') mainmenuOptions();

    }

    public static void exit() {
        exit = true;
    }

    public static void register() {
        System.out.println("=========== New User Register ===========");
        System.out.println("Username:\nminimum 6 characters, not start a number, no special character");
        System.out.println("Email   :\nhotmail, gmail, yandex, yahoo or outlook");
        System.out.println("Password:\nminimum 8 characters, number, uppercase and special character @ # $ % . < > , & + =");
        System.out.println("-----------------------------");

        getUsername();
        if(registerCancel) return;
        getEmail();
        if(registerCancel) return;
        getPassword();
        if(registerCancel) return;

        System.out.println("User create starting..");

        Register newAccount = new Register();

        Map<String, Boolean> result = newAccount.checkAndCreateNewAccount(username, password, email);

        if (result.get(username)) {
            username = null;
            System.err.println("Username already exist, please try again");
        } else if (result.get(email)) {
            email = null;
            System.err.println("Email already exist, please try again");
        } else if (!result.get(password)) {
            password = null;
            System.err.println("Password not valid, please try again");
        } else {
            System.err.println("\nNew Account created. Good luck.\n");
            username = null;
            password = null;
            email = null;
            id = null;
        }
    }

    private static void getUsername() {
        System.out.println("Please input username: ");
        do {
            String inputUsername = Utility.inputNext();
            if (inputUsername.equals("exit") || inputUsername.equals("quit") || inputUsername.equals("cancel")) {
                registerCancel = true;
                break;
            }
            String resultValidator = Utility.usernameValidator(inputUsername);
            if (resultValidator == "") {
                username = inputUsername;
                registerCancel = false;
            } else System.err.println("\nError(s), " + resultValidator + "\n\nPlease re-input username:");
        } while (username == null);
    }

    private static void getEmail() {
        System.out.println("Please input email: ");
        do {
            String inputEmail = Utility.inputNext();
            if (inputEmail.equals("exit") || inputEmail.equals("quit") || inputEmail.equals("cancel")) {
                registerCancel = true;
                break;
            }
            String resultValidator = Utility.emailValidator(inputEmail);
            if (resultValidator == "") {
                email = inputEmail;
                registerCancel = false;
            } else System.err.println("\nError(s), " + resultValidator + "\n\nPlease re-input email:");
        } while (email == null);
    }

    private static void getPassword() {
        System.out.println("Please input password: ");
        do {
            String inputPassword = Utility.inputNext();
            if (inputPassword.equals("exit") || inputPassword.equals("quit") || inputPassword.equals("cancel")) {
                registerCancel = true;
                break;
            }
            String resultValidator = Utility.passwordValidator(inputPassword);
            if (resultValidator == "") {
                password = inputPassword;
                registerCancel = false;
            } else System.err.println("\nError(s), " + resultValidator + "\n\nPlease re-input password:");
        } while (password == null);
    }

    public static void login() {
        System.out.println("Please input username or email");
        String loginUsernameOrEmail = Utility.inputNext();
        System.out.println("Please input password");
        String loginPassword = Utility.inputNext();
        String returnId = Login.checkLogin(loginUsernameOrEmail, loginPassword);
        if (returnId == null) {
            id = null;
            loginStatus = false;
            System.err.println("\nLogin inputs are not correct! Please try again!\n");
        } else {
            loginStatus = true;
            id = returnId;
            System.err.println("\nLogin successful!\n");
        }
    }

    public static void logout() {
        username = null;
        email = null;
        password = null;
        id = null;
        loginStatus = false;
        System.err.println("\nLogout successful!\n");
    }

    public static void help() {
        System.out.println("Help info");
    }

    public static void profile() {
        System.out.println("Your Id: " + id);
    }

    public static void recovery() {
        System.out.println("Account Recovery");
    }
}
