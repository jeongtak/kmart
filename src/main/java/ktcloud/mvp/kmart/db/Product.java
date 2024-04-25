package ktcloud.mvp.kmart.db;

public class Product {

    private String productName = null;
    private int inventoryCount = 0;
    private int deliveryCount = 0;
    private String status = null;

    public Product(String productName, int inventoryCount, int deliveryCount, String status) {
        this.productName = productName;
        this.inventoryCount = inventoryCount;
        this.deliveryCount = deliveryCount;
        this.status = status;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getInventoryCount() {
        return inventoryCount;
    }

    public void setInventoryCount(int inventoryCount) {
        this.inventoryCount = inventoryCount;
    }

    public int getDeliveryCount() {
        return deliveryCount;
    }

    public void setDeliveryCount(int deliveryCount) {
        this.deliveryCount = deliveryCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
