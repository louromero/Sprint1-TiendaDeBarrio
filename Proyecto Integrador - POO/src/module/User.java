package module;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User {
    final String name;
    final  String paymentType;
    final float totalPrice;
    List<Product> products = new ArrayList<>();
    final Date date;

    public User(String name, String paymentType, float totalPrice, List<Product> products, Date date){
        this.name = name;
        this.paymentType = paymentType;
        this.totalPrice = totalPrice;
        this.products = products;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public float getTotal() {
        return totalPrice;
    }

    public String getProducts() {
        return products.toString();
    }

    public Date getDate() {
        return date;
    }
}
