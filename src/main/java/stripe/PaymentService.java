package stripe;

import stripe.model.InvoiceModel;

public interface PaymentService {

    /**
     * Create an invoice.
     * @param email
     * @param productName
     * @param price Price with cents (ex: 1 pln = 100)
     * @return Invoice payment system ID
     */
    String createInvoice(String email, String productName, long price);

    InvoiceModel retrieveInvoice(String invoiceId);

}
