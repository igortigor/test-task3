import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.CustomerCollection;
import com.stripe.model.Invoice;
import com.stripe.model.InvoiceItem;
import com.stripe.model.InvoiceItemCollection;
import com.stripe.model.Price;
import com.stripe.param.InvoiceItemCreateParams;
import com.stripe.param.PriceCreateParams;

import java.util.HashMap;
import java.util.Map;

public class Main {


    public static void testCreateInvoice() {

        Map<String, Object> params = new HashMap<>();
        params.put("customer", "cus_KHnW828G0fhwJh");
        //params.put("")

        try {
            Invoice invoice = Invoice.create(params);

        } catch (StripeException e) {
            e.printStackTrace();

        }
    }

    public static void testRetrieveOneInvoice() {

        try {
            InvoiceItem invoiceItem =
                    InvoiceItem.retrieve(
                            "ii_1JdE5LBEnZ269CvXq87OhQ1d"
                    );
            System.out.println(invoiceItem);

        } catch (StripeException e) {
            e.printStackTrace();

        }
    }

    public static void testRetrieveAllInvoices() {

        Map<String, Object> params = new HashMap<>();
        params.put("limit", 3);

        try {
            InvoiceItemCollection invoiceItems =
                    InvoiceItem.list(params);
            System.out.println(invoiceItems);

        } catch (StripeException e) {
            e.printStackTrace();

        }
    }

    public static void testCreateCustomer() {


        Map<String, Object> customerMap = new HashMap<String, Object>();
        customerMap.put("description", "Example description");
        customerMap.put("email", "test_igor@example.com");
        customerMap.put("payment_method", "pm_card_visa"); // obtained via Stripe.js

        try {
            Customer customer = Customer.create(customerMap);
            System.out.println(customer);

        } catch (StripeException e) {
            e.printStackTrace();

        }
    }


    public static void testCreatePrice() {

        PriceCreateParams params = PriceCreateParams.builder()
                .setCurrency("pln")
                .setUnitAmount(100L)
                .setProductData(
                        PriceCreateParams.ProductData.builder()
                                .setName("Test product name")
                                .build()
                )
                .build();

        try {
            Price price = Price.create(params);
            System.out.println(price);
        } catch (StripeException e) {
            e.printStackTrace();
        }

    }


    public static void testFindCustomer(String email) {

        Customer customer;

        //search customer params
        Map<String, Object> existingCustomerParams = new HashMap<>();
        existingCustomerParams.put("limit", 1);
        existingCustomerParams.put("email", email);

        //create customer params
        Map<String, Object> newCustomerParams = new HashMap<String, Object>();
        newCustomerParams.put("description", "Example description");
        newCustomerParams.put("email", email);
        newCustomerParams.put("payment_method", "pm_card_visa");

        try {
            CustomerCollection customers =
                    Customer.list(existingCustomerParams);

            if (customers.getData().size() == 0) {
                customer = Customer.create(newCustomerParams);
            } else {
                customer = customers.getData().get(0);
            }


            System.out.println(customer);

        } catch (StripeException e) {
            e.printStackTrace();

        }
    }

    public static Customer testGetCustomer(String email) {

        //search customer params
        Map<String, Object> existingCustomerParams = new HashMap<>();
        existingCustomerParams.put("limit", 1);
        existingCustomerParams.put("email", email);

        //create customer params
        Map<String, Object> newCustomerParams = new HashMap<String, Object>();
        newCustomerParams.put("description", "Example description");
        newCustomerParams.put("email", email);
        newCustomerParams.put("payment_method", "pm_card_visa");

        try {
            CustomerCollection customers =
                    Customer.list(existingCustomerParams);

            if (customers.getData().size() == 0) {
                return Customer.create(newCustomerParams);
            } else {
                return customers.getData().get(0);
            }

        } catch (StripeException e) {
            e.printStackTrace();

        }
        return null;
    }

    public static Price testGetPrice(long amount, String productName) {

        PriceCreateParams params = PriceCreateParams.builder()
                .setCurrency("pln")
                .setUnitAmount(amount)
                .setProductData(
                        PriceCreateParams.ProductData.builder()
                                .setName(productName)
                                .build()
                )
                .build();

        try {
            return Price.create(params);
        } catch (StripeException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void testCreateInvoiceFull(String email, String productName, long price) {

        InvoiceItemCreateParams invoiceItemParams =
                InvoiceItemCreateParams.builder()
                        .setCustomer(testGetCustomer(email).getId())
                        .setPrice(testGetPrice(price * 100, productName).getId())
                        .build();


        try {
            System.out.println(InvoiceItem.create(invoiceItemParams));
        } catch (StripeException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {

        Stripe.apiKey = "sk_test_51JdDm9BEnZ269CvXRkyRE4hB5gQi7vToBMHrofMuxBaOEu6IzbCvty0VuY3ak1CKRL2wyUUeGcX9om68cZuz0fvd00yoxQVqid";

        //testCreateInvoice();

        // testCreateCustomer();

        //testRetrieveOneInvoice();

        //testRetrieveAllInvoices();

        //testCreatePrice();

        //testFindCustomer("test_igor@example.com2");

        //System.out.println(testGetCustomer("test_igor@example.com"));

        //System.out.println(testGetPrice(100, "Test2 product name"));

        testCreateInvoiceFull("i.andrievsky@mail.ru", "Test3 product name", 200);


    }
}
