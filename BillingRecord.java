// BillingRecord.java
import java.io.Serializable;

class BillingRecord implements Serializable {
    private String name;
    private String phoneNumber;
    private float billingAmount;

    public BillingRecord(String name, String phoneNumber, float billingAmount) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.billingAmount = billingAmount;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public float getBillingAmount() {
        return billingAmount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setBillingAmount(float billingAmount) {
        this.billingAmount = billingAmount;
    }

    @Override
    public String toString() {
        return "Name: " + name + "\n" +
               "Phone Number: " + phoneNumber + "\n" +
               "Billing Amount: " + String.format("%.2f", billingAmount) + "\n" +
               "----------------------------";
    }
}
