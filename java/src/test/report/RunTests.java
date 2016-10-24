package test.report;

/**
 * Created by Trent on 9/29/2016.
 */
public class RunTests {
    public static void main(String[] args) {
        String[] testClasses = new String[]
                {
                        "server.ServerPollerTest",
                        "server.ServerProxyTest",
                        "model.PlayerTest",
                        "model.BankTest"
                };
        org.junit.runner.JUnitCore.main(testClasses);
    }
}
