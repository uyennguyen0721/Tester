
import com.ou_software_testing.ou_software_testing.pojo.Order;
import com.ou_software_testing.ou_software_testing.services.JdbcServices;
import com.ou_software_testing.ou_software_testing.services.OrderServices;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Admin
 */
public class OrderTester {
    Connection conn;
    
    //year < 1
    @Test
    public void testGetOrderYear0() throws SQLException {
        conn = JdbcServices.getConnection();
        List<Order> rs = new OrderServices(conn).getListOrders("0");
        Assertions.assertNull(rs);
    }
    //year > 1
    @Test
    public void testGetOrderYearNot0() throws SQLException {
        conn = JdbcServices.getConnection();
        List<Order> rs = new OrderServices(conn).getListOrders("2020");
        Assertions.assertNotNull(rs);
    }
    //month < 1
    @Test
    public void testGetOrderMonth0() throws SQLException {
        conn = JdbcServices.getConnection();
        List<Order> rs = new OrderServices(conn).getListOrders("0","2020");
        Assertions.assertNull(rs);
    }
    //month > 12
    @Test
    public void testGetOrderMonth13() throws SQLException {
        conn = JdbcServices.getConnection();
        List<Order> rs = new OrderServices(conn).getListOrders("13","2020");
        Assertions.assertNull(rs);
    }
    //year < 1  && 0 < month < 13 
    @Test
    public void testGetOrderMonth12Year() throws SQLException {
        conn = JdbcServices.getConnection();
        List<Order> rs = new OrderServices(conn).getListOrders("5","0");
        Assertions.assertNull(rs);
    }
    
}
