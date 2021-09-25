package stripe;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class StripePaymentServiceTest {

    private static final String API_KEY = "sk_test_51JdDm9BEnZ269CvXRkyRE4hB5gQi7vToBMHrofMuxBaOEu6IzbCvty0VuY3ak1CKRL2wyUUeGcX9om68cZuz0fvd00yoxQVqid";
    private static final String TEST_EMAIL = "test@mail.com";
    private static final int TEST_PRICE = 1;
    private static final String TEST_PRODUCT = "testProduct";

    final StripePaymentService service = new StripePaymentService(API_KEY);

    @Test
    void createInvoice() {
        final var result = createTestInvoice();

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void retrieveInvoice() {
        final var testInvoice = createTestInvoice();

        final var result = service.retrieveInvoice(testInvoice);

        assertNotNull(result);
        assertEquals(100, result.getPrice());
        assertNotNull(result.getCustomer());
        assertFalse(result.getCustomer().isEmpty());
        assertEquals(testInvoice, result.getId());
    }

    private String createTestInvoice() {
        return service.createInvoice(TEST_EMAIL, TEST_PRODUCT, TEST_PRICE);
    }

}