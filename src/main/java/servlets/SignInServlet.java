package servlets;

import accounts.AccountService;
import accounts.UserProfile;
import com.google.gson.Gson;
import dbService.DBException;
import dbService.DBService;
import dbService.dao.UsersDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author D.Cononovv
 * <p>
 * Курс на https://stepic.org/
 * <p>
 * Описание курса и лицензия: https://github.com/vitaly-chibrikov/stepic_java_webserver
 */
public class SignInServlet extends HttpServlet {
        private final AccountService accountService;

        public SignInServlet(AccountService accountService) {
                this.accountService = accountService;
        }

        //get logged user profile
        public void doGet(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
                String sessionId = request.getSession().getId();
                UserProfile profile = accountService.getUserBySessionId(sessionId);
                if (profile == null) {
                        response.setContentType("text/html;charset=utf-8");
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                } else {
                        Gson gson = new Gson();
                        String json = gson.toJson(profile);
                        response.setContentType("text/html;charset=utf-8");
                        response.getWriter().println(json);
                        response.setStatus(HttpServletResponse.SC_OK);
                }
        }

        //sign in
        public void doPost(HttpServletRequest request,
                           HttpServletResponse response) throws ServletException, IOException {
                String login = request.getParameter("login");
                String password = request.getParameter("password");
                UserProfile profile = accountService.getUserByLogin(login);

                if (login == null || password == null) {
                        response.setContentType("text/html;charset=utf-8");
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        return;
                }

                try {
                        System.out.println();
                        if ((accountService.getUserByLoginSet(login).getPassword() == null)
                            || !(accountService.getUserByLoginSet(login).getPassword().equals(password))) {
                                accountService.addSession(request.getSession().getId(), profile);
                                response.setContentType("text/html;charset=utf-8");
                                response.getWriter().println("Unauthorized");
                                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                                return;
                        }
                } catch (DBException e) {
                        e.printStackTrace();
                }

                UserProfile profile12 = new UserProfile(login, password);
                accountService.addSession(request.getSession().getId(), profile12);
                response.setContentType("text/html;charset=utf-8");
                response.getWriter().println("Authorized: " + login);
                response.setStatus(HttpServletResponse.SC_OK);


        }

        //sign out
        public void doDelete(HttpServletRequest request,
                             HttpServletResponse response) throws ServletException, IOException {
                String sessionId = request.getSession().getId();
                UserProfile profile = accountService.getUserBySessionId(sessionId);
                if (profile == null) {
                        response.setContentType("text/html;charset=utf-8");
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                } else {
                        accountService.deleteSession(sessionId);
                        response.setContentType("text/html;charset=utf-8");
                        response.getWriter().println("Goodbye!");
                        response.setStatus(HttpServletResponse.SC_OK);
                }

        }
}
