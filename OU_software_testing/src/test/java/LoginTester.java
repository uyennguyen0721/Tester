/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.ou_software_testing.ou_software_testing.pojo.User;
import com.ou_software_testing.ou_software_testing.services.JdbcServices;
import com.ou_software_testing.ou_software_testing.services.UserServices;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;



/**
 *
 * @author Alive Nguyễn
 */
public class LoginTester {
     
    @Test
    public void testSuccessfull() throws SQLException{
        Connection conn = JdbcServices.getConnection();
        User user = new UserServices(conn).getUserInfo(null, "123");
        Assertions.assertTrue(user != null , "Lỗi sai username hoặc password");
    }
        
}
