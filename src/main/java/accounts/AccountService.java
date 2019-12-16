package accounts;

import dbService.DBException;
import dbService.DBService;
import dbService.dataSets.UsersDataSet;

import java.util.HashMap;
import java.util.Map;

/**
 * @author D.Cononovv
 *         <p>
 *         Курс на https://stepic.org/
 *         <p>
 *         Описание курса и лицензия: https://github.com/vitaly-chibrikov/stepic_java_webserver
 */
public class AccountService {
    private final Map<String, UserProfile> loginToProfile;
    private final Map<String, UserProfile> passToProfile;
    private final Map<String, UserProfile> sessionIdToProfile;

    DBService dbService = new DBService();

    public AccountService() {
        loginToProfile = new HashMap<>();
        passToProfile = new HashMap<>();
        sessionIdToProfile = new HashMap<>();
    }

    public void addNewUser(UserProfile userProfile) {




        try {
            long userId = dbService.addUser(userProfile.getName(),userProfile.getPass());
            System.out.println("Added user id: " + userProfile.getName());

            UsersDataSet dataSet = dbService.getUser(userId);
            System.out.println("User data set: " + dataSet);

            loginToProfile.put(userProfile.getName(), userProfile);
            passToProfile.put(userProfile.getPass(), userProfile);
            dbService.printConnectInfo();

        } catch (DBException e) {
            e.printStackTrace();
        }
    }

    public UserProfile getUserByLogin(String login) {
        return loginToProfile.get(login);
    }


    public UserProfile getUserByPassword(String password) {
        return passToProfile.get(password);
    }


    public UsersDataSet getUserByLoginSet(String login) throws DBException {
        return dbService.getUser(dbService.getUserNameId(login));
    }
    ;

    public UserProfile getUserBySessionId(String sessionId) {
        return sessionIdToProfile.get(sessionId);
    }

    public void addSession(String sessionId, UserProfile userProfile) {
        sessionIdToProfile.put(sessionId, userProfile);
    }

    public void deleteSession(String sessionId) {
        sessionIdToProfile.remove(sessionId);
    }
}
