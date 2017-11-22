package tw.com.smartowl.smartowl;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ming on 2017/5/21.
 */
@IgnoreExtraProperties
public class Product {

    public String uid;
    public String name;
    public String company;
    public String category;
    public int price;
    public int starCount = 0;
    public String detail;
    public String key;
    public int quantity = 0;
    public String armodel;
    public String jpg;
    //public Map<String, Boolean> stars = new HashMap<>();
    public Product() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Product(String name, int price, String company, String detail, String category, String jpg) {
        this.name = name;
        this.price = price;
        this.company = company;
        this.detail = detail;
        this.key = key;
        this.category = category;
        this.jpg = jpg;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("price", price);
        result.put("company", company);
        result.put("detail", detail);
        result.put("key", key);
        result.put("category",category);
        result.put("armodel",armodel);
        result.put("jpg",jpg);
        return result;
    }

}