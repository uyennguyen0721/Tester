import com.ou_software_testing.ou_software_testing.pojo.ListProduct;
import com.ou_software_testing.ou_software_testing.pojo.Product;
import com.ou_software_testing.ou_software_testing.services.ProductServices;
import com.ou_software_testing.ou_software_testing.services.JdbcServices;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
/**
 *
 * @author Alive Nguyễn
 */
public class ProduceTester {
    Connection conn1;
    Product p;
    
    //Số lượng từng sản phẩm không vượt quá 200.
    @Test
    public void testQuantityBiggest() throws SQLException {
        Connection conn = JdbcServices.getConnection();
        boolean flag = false;
        ListProduct listProds = new ProductServices(conn).getAllProduct();
        
        for(Product p: listProds.getListProduct()){
            if(p.getCount() > 200)  {
                flag = true;
                break;
            }
        }
        Assertions.assertFalse(flag);
    } 
    
    //Số lượng từng sản phẩm không được ít hơn 3 sản phẩm
    @Test
    public void testQuantitySmallest() throws SQLException {
        Connection conn = JdbcServices.getConnection();
        boolean flag = false;
        ListProduct listProds = new ProductServices(conn).getAllProduct();
        for(Product p: listProds.getListProduct()){
            if(p.getCount() < 3)  {
                flag = true;
                break;
            }
        }
        Assertions.assertFalse(flag);
    } 
    //Edit với số lượng < 200 && >3
    @Test 
    public void testEditProductById() {
        Connection conn = JdbcServices.getConnection();
        p = new Product("ao den","USA","XL",10,1,new BigDecimal("5000"));
        p.setId(1);
        Boolean rs = new ProductServices(conn).editProductById(p);
        Assertions.assertTrue(rs);
        EditBack();
    }
    //Edit với số lượng > 200 
    @Test
    public void testEditProductByIdBigQuantity() {
        Connection conn = JdbcServices.getConnection();
        p = new Product("ao den thui","USA","XL",201,1,new BigDecimal("5000"));
        p.setId(1);
        Boolean rs = new ProductServices(conn).editProductById(p);
        Assertions.assertFalse(rs);
    }
    //Edit với số lượng <3
    @Test
    public void testEditProductByIdSmallQuantity() {
        Connection conn = JdbcServices.getConnection();
        p = new Product("ao den thui","USA","XL",2,1,new BigDecimal("5000"));
        p.setId(1);
        Boolean rs = new ProductServices(conn).editProductById(p);
        Assertions.assertFalse(rs);
    }
    //Edit với trùng tên
    @Test
    public void testEditProductByIdSameName() {
        Connection conn = JdbcServices.getConnection();
        p = new Product("ao den2","USA","XL",2,1,new BigDecimal("5000"));
        p.setId(1);
        Boolean rs = new ProductServices(conn).editProductById(p);
        Assertions.assertFalse(rs);
    }
    @Test
    public void testCheckUniqueName() {
        Connection conn = JdbcServices.getConnection();
        p = new Product("ao den thui","USA","XL",2,1,new BigDecimal("5000"));
        Boolean rs = new ProductServices(conn).checkUniqueName(p);
        Assertions.assertTrue(rs);
    }

    //còn getbyname
    //delete 
    @Test
    public void testDeleteId() {
        Connection conn = JdbcServices.getConnection();
        Boolean rs = new ProductServices(conn).deleleProductById(-1);
        Assertions.assertFalse(rs);
    }
    @Test
    public void testDeleteName() {
        Connection conn = JdbcServices.getConnection();
        Boolean rs = new ProductServices(conn).deleleProductByName("");
        Assertions.assertFalse(rs);
    }
    //count < 200
    @Test
    public void testInsert() throws SQLException {
        Connection conn = JdbcServices.getConnection();
        p = new Product("ao den test","AUS","M",80,1,new BigDecimal("5000"));
        Boolean rs = new ProductServices(conn).insertProduct(p);
        Assertions.assertTrue(rs);
        deteleTest();
    }
    //count > 200
    @Test
    public void testInsertBigQuantity() throws SQLException {
        Connection conn = JdbcServices.getConnection();
        p = new Product("ao den test","AUS","M",201,1,new BigDecimal("5000"));
        Boolean rs = new ProductServices(conn).insertProduct(p);
        Assertions.assertFalse(rs);
    }
    //count <3
    @Test
    public void testInsertSmallQuantity() throws SQLException {
        Connection conn = JdbcServices.getConnection();
        p = new Product("ao den test","AUS","M",2,1,new BigDecimal("5000"));
        Boolean rs = new ProductServices(conn).insertProduct(p);
        Assertions.assertFalse(rs);
    }
    //get product id = 0
    @Test
    public void testGetById0() throws SQLException {
        Connection conn = JdbcServices.getConnection();
        Product rs = new ProductServices(conn).getProductById(0);
        Assertions.assertNull(rs);
    }
    //get product id = 0
    @Test
    public void testGetById1() throws SQLException {
        Connection conn = JdbcServices.getConnection();
        Product rs = new ProductServices(conn).getProductById(1);
        Assertions.assertNotNull(rs);
    }
    //get by name
    @Test
    public void testGetByName() throws SQLException {
        Connection conn = JdbcServices.getConnection();
        ListProduct rs = new ProductServices(conn).getProductByName("");
        Assertions.assertNotNull(rs);
    }
    @Test
    public void testReduceProductCount() throws SQLException {
        Connection conn = JdbcServices.getConnection();
        ProductServices productServices = new ProductServices(conn);
        boolean rs = productServices.reduceProductCount(1,20);
        Assertions.assertFalse(rs);
    }
    @Test
    public void testReduceProductCount2() throws SQLException {
        Connection conn = JdbcServices.getConnection();
        ProductServices productServices = new ProductServices(conn);
        boolean rs = productServices.reduceProductCount(1,1);
        Assertions.assertTrue(rs);
    }
    
    //support functions
    private void deteleTest() {
        Connection conn = JdbcServices.getConnection();
        Boolean rs = new ProductServices(conn).deleleProductByName("ao den test");
    }
    private void EditBack() {
        Connection conn = JdbcServices.getConnection();
        p = new Product("ao den1","USA","XL",10,1,new BigDecimal("5000"));
        p.setId(1);
        Boolean rs = new ProductServices(conn).editProductById(p);
    }
}
