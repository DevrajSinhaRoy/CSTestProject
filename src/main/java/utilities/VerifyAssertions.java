package utilities;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import utilities.POJO.ValidateBatchApiRequest;

import java.util.List;
import java.util.stream.Collectors;

public class VerifyAssertions{
    ApiClient apiClient;
    String response;

    public VerifyAssertions(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public void toJsonResponseString(Response response) {
        this.response = response.asString();
    }

    public ApiClient statusCode(int statusCode) {
        Assert.assertEquals("Status Code", statusCode, apiClient.getResponse().statusCode());
        return apiClient;
    }

    public ApiClient parameterValue(String key, String value) {
        if (key.equalsIgnoreCase("messages") && !value.equalsIgnoreCase("null")) {
            List<String> message = JsonPath.from(response).get("messages");
            List<String> filtered = message.stream().filter(k->k.contains(value)).collect(Collectors.toList());
            if (filtered.isEmpty()) {
                Assert.assertFalse(true);
                return apiClient;
            } else {
                Assert.assertTrue(true);
                return apiClient;
            }
        }
        if (value.equalsIgnoreCase("null")) {
            Assert.assertEquals("The value of "+key, null, JsonPath.from(response).get(key));
            return apiClient;
        }
        Assert.assertEquals("The value of "+key, value, JsonPath.from(response).get(key));
        return apiClient;
    }

    public ApiClient parameterValue(String key[], String value[], int number) {
        int index = number-1;
        if (apiClient.getApiRequest() instanceof ValidateBatchApiRequest) {
            for (int i=0;i<key.length;i++) {
                if (key[i].contains("messages")) {
                    List<String> messages = JsonPath.from(response).get("["+index+"]."+key[i]);
                    System.out.println(value[i]);
                    System.out.println(messages.get(0));
                    Assert.assertTrue("Validate Messages",messages.contains(value[i]));
                } else {
                    String toResponse = JsonPath.from(response).get("[" + index + "]." + key[i]);
                    Assert.assertTrue("Validate property " + key[i] + " for index " + number, toResponse.contains(value[i]));
                }
            }
        }
        return apiClient;
    }

}
