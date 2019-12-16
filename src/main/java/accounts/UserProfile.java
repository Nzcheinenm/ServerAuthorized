package accounts;

/**
 * @author D.Cononovv
 *         <p>
 *         Курс на https://stepic.org/
 *         <p>
 *         Описание курса и лицензия: https://github.com/vitaly-chibrikov/stepic_java_webserver
 */
public class UserProfile {
    private final String name;
    private final String password;


    public UserProfile(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public UserProfile(String name) {
        this.name = name;
        this.password = name;
    }

    public String getName() {
        return name;
    }

    public String getPass() {
        return password;
    }


}
