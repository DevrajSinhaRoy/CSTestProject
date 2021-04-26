package utilities.POJO;

import lombok.*;
import utilities.ApiRequest;

@Getter
public class ValidateApiRequest extends ApiRequest {
    private String customer;
    private String ccyPair;
    private String type;
    private String direction;
    private String legalEntity;
    private String tradeDate;
    private String valueDate;
    private String trader;
    private double amount1;
    private double amount2;
    private float rate;
    private String style;
    private String deliveryDate;
    private String strategy;
    private String expiryDate;
    private String payCcy;
    private float premium;
    private String premiumCcy;
    private String premiumType;
    private String premiumDate;
    private String exerciseStartDate;

    public ValidateApiRequest setcustomer(String customer) {
        this.customer = customer;
        return this;
    }

    public ValidateApiRequest setstyle(String style) {
        this.style = style;
        return this;
    }

    public ValidateApiRequest setdeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
        return this;
    }

    public ValidateApiRequest setstrategy(String strategy) {
        this.strategy = strategy;
        return this;
    }

    public ValidateApiRequest setexpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }

    public ValidateApiRequest setpayCcy(String payCcy) {
        this.payCcy = payCcy;
        return this;
    }

    public ValidateApiRequest setpremium(float premium) {
        this.premium = premium;
        return this;
    }

    public ValidateApiRequest setpremiumCcy(String premiumCcy) {
        this.premiumCcy = premiumCcy;
        return this;
    }

    public ValidateApiRequest setpremiumType(String premiumType) {
        this.premiumType = premiumType;
        return this;
    }

    public ValidateApiRequest setpremiumDate(String premiumDate) {
        this.premiumDate = premiumDate;
        return this;
    }

    public ValidateApiRequest setccypair(String CcyPair) {
        this.ccyPair = CcyPair;
        return this;
    }

    public ValidateApiRequest settype(String Type) {
        this.type = Type;
        return this;
    }

    public ValidateApiRequest setdirection(String direction) {
        this.direction = direction;
        return this;
    }

    public ValidateApiRequest setlegalentity(String legalEntity) {
        this.legalEntity = legalEntity;
        return this;
    }

    public ValidateApiRequest settradedate(String tradeDate) {
        this.tradeDate = tradeDate;
        return this;
    }

    public ValidateApiRequest setvaluedate(String valueDate) {
        this.valueDate = valueDate;
        return this;
    }

    public ValidateApiRequest settrader(String trader) {
        this.trader = trader;
        return this;
    }

    public ValidateApiRequest setamount1(double amount1) {
        this.amount1 = amount1;
        return this;
    }

    public ValidateApiRequest setamount2(double amount2) {
        this.amount2 = amount2;
        return this;
    }

    public ValidateApiRequest setrate(float rate) {
        this.rate = rate;
        return this;
    }

    public ValidateApiRequest setexerciseStartDate(String exerciseStartDate) {
        this.exerciseStartDate = exerciseStartDate;
        return this;
    }
}
