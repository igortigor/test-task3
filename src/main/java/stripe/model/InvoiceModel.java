package stripe.model;

public class InvoiceModel {

    private final String id;
    private final long price;
    private final String customer;

    public InvoiceModel(String id, long price, String customer) {
        this.id = id;
        this.price = price;
        this.customer = customer;
    }

    public String getId() {
        return id;
    }

    public long getPrice() {
        return price;
    }

    public String getCustomer() {
        return customer;
    }
}
