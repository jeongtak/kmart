package ktcloud.mvp.kmart.db;

import ktcloud.mvp.kmart.KMartApplication;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class ProductQuery {

    public static void main(String[] args) {
        ProductQuery.orderProduct("체리");
    }

    public static int initProduct() {
        Connection conn = getConnection();

        String sql = "update product set status='판매중', inventory_count =100, delivery_count=0";
        return execUpdateSQL(sql, conn);
    }

    public static int orderProduct(String productName) {
        Connection conn = getConnection();

        String sql = "update product set inventory_count = inventory_count -1, delivery_count = delivery_count +1 where product_name = '" + productName + "'";
        return execUpdateSQL(sql, conn);
    }

    public static int deliverProduct(String productName) {
        Connection conn = getConnection();

        String sql = "update product set status = '배송중' where product_name = '" + productName + "'";
        return execUpdateSQL(sql, conn);
    }

    public static Map<String, Product> getProductMap() {
        Connection conn = getConnection();
        String sql = "select * from product";
        return execSQL(sql, conn);
    }

    private static Connection getConnection() {

        Connection conn = null;

        String dbURL = "jdbc:postgresql://product-db:5432/product_db";
        String username = "kmart";
        String password = "kmart";

        try {

            Class.forName("org.postgresql.Driver");

            try {

                conn = DriverManager.getConnection(dbURL, username, password);

            }catch (Exception ce) {

                dbURL = "jdbc:postgresql://host.docker.internal:30033/product_db";

                conn = DriverManager.getConnection(dbURL, username, password);

            }

        }  catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println("connection URL >>>>"+dbURL);

        return conn;
    }

    private static Map<String, Product> execSQL(String sql, Connection conn) {
        Statement stmt = null;
        ResultSet rs = null;
        Map<String, Product> productMap = new HashMap<>();

        try {

            KMartApplication.log(sql);

            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String productName = rs.getString("product_name");
                int inventoryCount = rs.getInt("inventory_count");
                int deliveryCount = rs.getInt("delivery_count");
                String status = rs.getString("status");

                Product product = new Product(productName, inventoryCount, deliveryCount, status);
                productMap.put(productName, product);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return productMap;

    }

    private static int execUpdateSQL(String sql, Connection conn) {
        Statement stmt = null;
        int result = 0;

        try {

            KMartApplication.log(sql);

            stmt = conn.createStatement();
            result = stmt.executeUpdate(sql);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return result;
    }
}
