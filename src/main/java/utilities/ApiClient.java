package utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.messages.internal.com.google.common.base.Preconditions;
import io.cucumber.messages.internal.com.google.gson.JsonObject;
import io.cucumber.messages.internal.com.google.gson.JsonParser;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utilities.POJO.ValidateApiRequest;
import utilities.POJO.ValidateBatchApiRequest;
import utilities.Services.Localhost;
import utilities.support.ObjectToJson;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class ApiClient {

    public String BASEURL = null;
    private RequestSpecification requestSpecification;
    private Response response;
    private PayloadFactory payloadFactory;
    private String relativePath;
    private ApiRequest apiRequest;
    public VerifyAssertions verifyAssertions;
    private ObjectToJson objectToJson;

    public ApiClient(String BASEURL) {
        this.BASEURL = BASEURL;
        payloadFactory = new PayloadFactory(BASEURL);
        RestAssured.baseURI = payloadFactory.getBaseUrl();
        requestSpecification = setUpRestAssuredGiven();
        verifyAssertions = new VerifyAssertions(this);
        objectToJson = new ObjectToJson();
        apiRequest= null;
    }

    private RequestSpecification setUpRestAssuredGiven() {
        return RestAssured.given();
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    /* hardcoded the return type, need to implement better*/
        apiRequest = generateValidPayload(relativePath);
    }

    private ApiRequest generateValidPayload(String relativePath) {
        Preconditions.checkNotNull(BASEURL, "Base Url cannot be null");
        return payloadFactory.validPayload(relativePath);
    }

    public ApiClient setContentType(String contentType) {
        if (contentType.contains("json")) {
            requestSpecification.contentType(ContentType.JSON);
            return this;
        }
        return this;
    }

    public ApiClient setHeader(String key, String value) {
        if (key.equalsIgnoreCase("content-type")) {
            setContentType(value);
            return this;
        }
        requestSpecification.headers(key, value);
        return this;
    }

    public ApiClient requestPost() {
        try {
            String jsonFormat = objectToJson.serializeToJson(apiRequest);
            if (apiRequest instanceof ValidateBatchApiRequest) {
                JsonObject jsonObject = new JsonParser().parse(jsonFormat).getAsJsonObject();
                jsonFormat = jsonObject.get("list").toString();
            }
            requestSpecification.body(jsonFormat);
            response = requestSpecification.post(relativePath);
        }
        catch (JsonProcessingException jpe) {
            jpe.getLocation();
        }
        return this;
    }

    public ApiClient requestGet() {
        response = requestSpecification.get(relativePath);
        return this;
    }

    public ApiClient requestPut() {
        requestSpecification.body(apiRequest);
        response = requestSpecification.put(relativePath);
        return this;
    }

    public VerifyAssertions verify() {
        verifyAssertions.toJsonResponseString(response);
        return verifyAssertions;
    }

    public Response getResponse() {
        return response;
    }

    public ApiRequest getApiRequest() {
        return apiRequest;
    }

    public ApiClient setJsonValue(String key, String value) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String funcName = "set"+key;
        apiRequest.getClass().getDeclaredMethod(funcName, String.class).invoke(apiRequest, value);
        return this;
    }

    public ApiClient updateExistingBody(String key, String value) {
        // Need to find a way to recoginize which apirequest, domain name and api are we working with
        apiRequest = payloadFactory.updatePayloadFor(relativePath, key, value);
        return this;
    }

    public ApiClient addEntity(String key[], String value[]) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (apiRequest instanceof ValidateBatchApiRequest) {
            Localhost localhost = new Localhost();
            ValidateApiRequest validateApiRequest = (ValidateApiRequest) localhost.getPayloadfor("validate");
                for (int i=0;i<key.length; i++) {
                    String funcName = "set"+key[i];
                    Method methods[] = validateApiRequest.getClass().getDeclaredMethods();
                    for (int j=0;j< methods.length;j++) {
                        Method method = methods[j];
                        if (method.getName().contains(funcName)) {
                            method.invoke(validateApiRequest, value[i]);
                        }
                    }
                }
                ((ValidateBatchApiRequest) apiRequest).setlist(validateApiRequest);

        }
        return this;
    }
    
    public void clear() {
        if (apiRequest instanceof ValidateBatchApiRequest) {
            ((ValidateBatchApiRequest) apiRequest).emptyList();
        }
    }
}
