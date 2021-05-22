
import com.ou_software_testing.ou_software_testing.pojo.User;
import com.ou_software_testing.ou_software_testing.services.JdbcServices;
import com.ou_software_testing.ou_software_testing.services.UserServices;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserTester {
    private final String email = "dung@gmail.com";
    private final String password = "123";
    Connection conn;
    
    @Test
    public void testGetUser() throws SQLException {
        conn = JdbcServices.getConnection();
        User u = new UserServices(conn).getUserInfo(email, password);
        Assertions.assertNotNull(u);
    }
    
    @Test
    public void testAddOldInfo(){
        conn = JdbcServices.getConnection();
        Boolean rs = new UserServices(conn).addUserInfo("", "", "", "", email, password);
        Assertions.assertFalse(rs);
    }
}
