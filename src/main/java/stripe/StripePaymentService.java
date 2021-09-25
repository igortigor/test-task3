package stripe;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.CustomerCollection;
import com.stripe.model.InvoiceItem;
import com.stripe.model.Price;
import com.stripe.param.InvoiceItemCreateParams;
import com.stripe.param.PriceCreateParams;
import stripe.model.InvoiceModel;

import java.util.HashMap;
import java.util.Map;

public class StripePaymentService implements PaymentService {

    public StripePaymentService(String api_key) {
        Stripe.apiKey = api_key;
    }

    @Override
    public String createInvoice(String email, String productName, long price) {


        final var customer = getCustomerByEmail(email);
        final var priceObj = createPrice(price * 100, productName);

        if (customer == null || priceObj == null) {
            return null;
        }

        final var invoiceCreateParams = InvoiceItemCreateParams.builder()
                .setCustomer(customer.getId())
                .setPrice(priceObj.getId())
                .build();

        try {
            return InvoiceItem.create(invoiceCreateParams).getId();
        } catch (StripeException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public InvoiceModel retrieveInvoice(String invoiceId) {

        try {
            InvoiceItem invoiceItem = InvoiceItem.retrieve(invoiceId);

            return new InvoiceModel(invoiceItem.getId(), invoiceItem.getAmount(), invoiceItem.getCustomer());
        } catch (StripeException e) {
            e.printStackTrace();
        }


        return null;
    }

    private Customer getCustomerByEmail(String email) {

        //search customer params
        Map<String, Object> existingCustomerParams = new HashMap<>();
        existingCustomerParams.put("limit", 1);
        existingCustomerParams.put("email", email);

        //create customer params
        Map<String, Object> newCustomerParams = new HashMap<>();
        newCustomerParams.put("description", "Example description");
        newCustomerParams.put("email", email);
        newCustomerParams.put("payment_method", "pm_card_visa");

        try {
            CustomerCollection customers =
                    Customer.list(existingCustomerParams);

            if (customers.getData().isEmpty()) {
                return Customer.create(newCustomerParams);
            } else {
                return customers.getData().get(0);
            }

        } catch (StripeException e) {
            e.printStackTrace();

        }
        return null;
    }

    private Price createPrice(long amount, String productName) {
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

}
