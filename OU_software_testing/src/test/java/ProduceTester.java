/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.ou_software_testing.ou_software_testing.pojo.ListProduct;
import com.ou_software_testing.ou_software_testing.pojo.Product;
import com.ou_software_testing.ou_software_testing.services.ProductServices;
import com.ou_software_testing.ou_software_testing.services.JdbcServices;
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
    
    //Số lượng sản phẩm từng loại không vượt quá 200.
    @Test
    public void testQuantityBiggest() throws SQLException {
        Connection conn = JdbcServices.getConnection();
        ListProduct listProds = new ProductServices(conn).getAllProduct();
        List<Product> prods = listProds.getListProduct();
        List<Integer> temps = new ArrayList<>();
        int count = 0;
        prods.forEach(p -> temps.add(p.getCount()));
        for(int i = 0; i < temps.size(); i++){
            count += temps.get(i);
        }
        Assertions.assertTrue(count <= 200);
    } 
    
    //Số lượng sản phẩm từng loại không được ít hơn 3 sản phẩm
    @Test
    public void testQuantitySmallest() throws SQLException {
        Connection conn = JdbcServices.getConnection();
        ListProduct listProds = new ProductServices(conn).getAllProduct();
        List<Product> prods = listProds.getListProduct();
        List<Integer> temps = new ArrayList<>();
        int count = 0;
        prods.forEach(p -> temps.add(p.getCount()));
        for(int i = 0; i < temps.size(); i++){
            count += temps.get(i);
        }
        Assertions.assertTrue(count >= 3);
    } 
}
