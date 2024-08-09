import connection.AppConnection;
import model.ElectionResult;

public class Main {
    public static void main(String[] args) throws Exception {
        ElectionResult[] test = new ElectionResult().findAll(new AppConnection());
    }
}
