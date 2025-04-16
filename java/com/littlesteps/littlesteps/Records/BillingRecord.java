package com.littlesteps.littlesteps.Records;

import javafx.beans.property.*;

import java.sql.Date;

public class BillingRecord {
    private StringProperty service;
    private ObjectProperty<Date> issueDate;
    private DoubleProperty discount;
    private DoubleProperty amountDue;
    private DoubleProperty lateFee;
    private DoubleProperty totalAmount;
    private ObjectProperty<Date> dueDate;
    private StringProperty status;
    private StringProperty paymentMethod;
    private ObjectProperty<Date> paymentDate;
    private IntegerProperty tenantId;

    // Constructor
    public BillingRecord() {
        this.service = new SimpleStringProperty();
        this.issueDate = new SimpleObjectProperty<>();
        this.discount = new SimpleDoubleProperty();
        this.amountDue = new SimpleDoubleProperty();
        this.lateFee = new SimpleDoubleProperty();
        this.totalAmount = new SimpleDoubleProperty();
        this.dueDate = new SimpleObjectProperty<>();
        this.status = new SimpleStringProperty();
        this.paymentMethod = new SimpleStringProperty();
        this.paymentDate = new SimpleObjectProperty<>();
        this.tenantId = new SimpleIntegerProperty();
    }

    public BillingRecord(String service, Date issueDate, double discount, double amountDue, double lateFee, double totalAmount, Date dueDate, String status, String paymentMethod, Date paymentDate, int tenantId) {
        this();
        setService(service);
        setIssueDate(issueDate);
        setDiscount(discount);
        setAmountDue(amountDue);
        setLateFee(lateFee);
        setTotalAmount(totalAmount);
        setDueDate(Date.valueOf(dueDate.toLocalDate()));
        setStatus(status);
        setPaymentMethod(paymentMethod);
        setPaymentDate(paymentDate);
        setTenantId(tenantId);
    }

    // Getters and Setters for each property

    // Service
    public StringProperty getService() {
        return service;
    }

    public void setService(String service) {
        this.service.set(service);
    }

    // Issue Date
    public Date getIssueDate() {
        return issueDate.get();
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate.set(issueDate);
    }

    // Discount
    public DoubleProperty getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount.set(discount);
    }

    // Amount Due
    public DoubleProperty getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(double amountDue) {
        this.amountDue.set(amountDue);
    }

    // Late Fee
    public DoubleProperty getLateFee() {
        return lateFee;
    }

    public void setLateFee(double lateFee) {
        this.lateFee.set(lateFee);
    }

    // Total Amount
    public DoubleProperty getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount.set(totalAmount);
    }

    // Due Date
    public Date getDueDate() {
        return dueDate.get();
    }

    public void setDueDate(Date dueDate) {
        this.dueDate.set(dueDate);
    }

    // Status
    public StringProperty getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    // Payment Method
    public StringProperty getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod.set(paymentMethod);
    }

    // Payment Date
    public Date getPaymentDate() {
        return paymentDate.get();
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate.set(paymentDate);
    }

    // Tenant ID
    public IntegerProperty getTenantId() {
        return tenantId;
    }

    public void setTenantId(int tenantId) {
        this.tenantId.set(tenantId);
    }


    public BillingRecord(String service, String issueDate, double discount, double amountDue, double lateFee, double totalAmount, String dueDate, String status, String paymentMethod, String paymentDate, int tenantId) {
        this.service.set(service);
        this.issueDate.set(Date.valueOf(issueDate));
        this.discount.set(discount);
        this.amountDue.set(amountDue);
        this.lateFee.set(lateFee);
        this.totalAmount.set(totalAmount);
        this.dueDate.set(Date.valueOf(dueDate));
        this.status.set(status);
        this.paymentMethod.set(paymentMethod);
        this.paymentDate.set(Date.valueOf(paymentDate));
        this.tenantId.set(tenantId);
    }

    // Getters for properties
    public StringProperty serviceProperty() { return service; }
    public ObjectProperty<Date> getIssueDateProperty() { return issueDate; }
    public DoubleProperty discountProperty() { return discount; }
    public DoubleProperty amountDueProperty() { return amountDue; }
    public DoubleProperty lateFeeProperty() { return lateFee; }
    public DoubleProperty totalAmountProperty() { return totalAmount; }
    public ObjectProperty<Date> getDueDateProperty() { return dueDate; }
    public StringProperty statusProperty() { return status; }
    public StringProperty paymentMethodProperty() { return paymentMethod; }
    public ObjectProperty<Date> getPaymentDateProperty() { return paymentDate; }
    public IntegerProperty tenantIdProperty() { return tenantId; }
}