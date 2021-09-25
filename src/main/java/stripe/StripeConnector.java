package stripe;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.Price;
import com.stripe.param.PriceCreateParams;

public class StripeConnector {

    private final String email;
    private final String productName;
    private final long price;

    //Stripe.apiKey = "sk_test_51JdDm9BEnZ269CvXRkyRE4hB5gQi7vToBMHrofMuxBaOEu6IzbCvty0VuY3ak1CKRL2wyUUeGcX9om68cZuz0fvd00yoxQVqid";


    public StripeConnector(String email, String productName, int price) {
        this.email = email;
        this.productName = productName;
        this.price = price;
    }


    Price getPrice(){

        PriceCreateParams params = PriceCreateParams.builder()
                .setCurrency("pln")
                .setUnitAmount(this.price)
                .setProductData(
                        PriceCreateParams.ProductData.builder()
                            .setName(this.productName)
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


    /*
    PriceCreateParams params =
  PriceCreateParams.builder()
    .setProduct("{{PRODUCT_ID}}"
)
    .setUnitAmount(2000L)
    .setCurrency("usd")
    .build();

Price price = Price.create(params);
     */

}
