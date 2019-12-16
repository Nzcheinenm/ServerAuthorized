package dbService;

/**
 * @author D.Cononovv
 *         <p>
 *         Курс на https://stepic.org/
 *         <p>
 *         Описание курса и лицензия: https://github.com/vitaly-chibrikov/stepic_java_webserver
 */
public class DBException extends Exception {
    public DBException(Throwable throwable) {
        super(throwable);
    }
}
