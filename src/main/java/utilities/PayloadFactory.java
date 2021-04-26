package utilities;

import io.cucumber.messages.internal.com.google.common.base.Preconditions;
import utilities.Services.Localhost;

public class PayloadFactory {

    private String serviceUrl=null;
    private String serviceName=null;
    private String microservice = "localhost";

    PayloadFactory(String serviceName) {
       Preconditions.checkNotNull(serviceName, "serviceName cannot be null");
       this.serviceName = serviceName;
       // Implement reflection/inheritance here to get the service name without using if condition
       if (serviceName.equalsIgnoreCase(microservice)) {
           serviceUrl = "http://localhost:12345";
       }
    }

    public String getBaseUrl() {
        Preconditions.checkNotNull(serviceUrl, "serviceUrl cannot be null");
        return serviceUrl;
    }

    public ApiRequest validPayload(String api) {
        // Implement reflection/inheritance here to get the service name without using if condition
        if (serviceName.equalsIgnoreCase(microservice)) {
            Localhost localhost=  new Localhost();
            return localhost.getPayloadfor(api);
        } else
            return null;
    }

    public ApiRequest updatePayloadFor(String api, String key, String value) {
        // Implement reflection/inheritance here to get the service name without using if condition
        if (serviceName.equalsIgnoreCase(microservice)) {
            return Localhost.getUpdatedPayloadFor(api, key, value);
        } else
            return null;
    }
}
