package utilities.Services;

import utilities.ApiRequest;
import utilities.POJO.ValidateApiRequest;
import utilities.POJO.ValidateBatchApiRequest;

public class Localhost {
    private static ValidateBatchApiRequest validateBatchApiRequest= new ValidateBatchApiRequest();
    private static ValidateApiRequest validateApiRequest = new ValidateApiRequest();

    public ApiRequest getPayloadfor(String api) {
        if (api.equalsIgnoreCase("validate")) {
            return
                    new ValidateApiRequest()
                            .setcustomer("PLUTO1")
                            .setccypair("EURUSD")
                            .settype("Spot")
                            .setdirection("BUY")
                            .settradedate("2017-08-11")
                            .setvaluedate("2017-08-15")
                            .settrader("Johann Baumfiddler")
                            .setlegalentity("CSZurich")
                            .setamount1(1000000.00)
                            .setamount2(1100000.00)
                            .setrate(1.12f);
        }
        if (api.equalsIgnoreCase("validateBatch")) {
            validateApiRequest =  (ValidateApiRequest) getPayloadfor("validate");
            validateBatchApiRequest.emptyList();
            validateBatchApiRequest.setlist(validateApiRequest);
            return validateBatchApiRequest;
        }
        return null;
    }

    public static ApiRequest getUpdatedPayloadFor(String api, String key, String value) {
        if (api.equalsIgnoreCase("validate")) {
            return
                    new ValidateApiRequest()
                            .setcustomer("PLUTO1")
                            .setccypair("EURUSD")
                            .settype("VanillaOption")
                            .setstyle("EUROPEAN")
                            .setdirection("BUY")
                            .setstrategy("CALL")
                            .setdeliveryDate("2017-08-22")
                            .setexpiryDate("2017-08-21")
                            .setpayCcy("USD")
                            .setpremium(0.20f)
                            .setpremiumCcy("USD")
                            .setpremiumType("%USD")
                            .setpremiumDate("2017-08-12")
                            .settradedate("2017-08-11")
                            .settrader("Johann Baumfiddler")
                            .setlegalentity("CSZurich")
                            .setamount1(1000000.00)
                            .setamount2(1100000.00)
                            .setrate(1.12f);
        } else
            return null;
    }
}
