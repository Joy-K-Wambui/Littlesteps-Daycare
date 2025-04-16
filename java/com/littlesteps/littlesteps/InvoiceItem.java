package com.littlesteps.littlesteps;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class InvoiceItem {
    // Properties for all the columns
    private final SimpleStringProperty service;
    private final SimpleStringProperty issueDate;
    private final SimpleDoubleProperty discount;
    private final SimpleDoubleProperty amountDue;
    private final SimpleDoubleProperty lateFee;
    private final SimpleDoubleProperty totalAmount;
    private final SimpleStringProperty dueDate;
    private final SimpleStringProperty status;
    private final SimpleStringProperty paymentMethod;
    private final SimpleStringProperty paymentDate;
    private final SimpleIntegerProperty tenantId;

    // Constructor to initialize the properties
    public InvoiceItem(String service, String issueDate, Double discount, Double amountDue,
                       Double lateFee, Double totalAmount, String dueDate, String status,
                       String paymentMethod, String paymentDate, Integer tenantId) {
        this.service = new SimpleStringProperty(service);
        this.issueDate = new SimpleStringProperty(issueDate);
        this.discount = new SimpleDoubleProperty(discount);
        this.amountDue = new SimpleDoubleProperty(amountDue);
        this.lateFee = new SimpleDoubleProperty(lateFee);
        this.totalAmount = new SimpleDoubleProperty(totalAmount);
        this.dueDate = new SimpleStringProperty(dueDate);
        this.status = new SimpleStringProperty(status);
        this.paymentMethod = new SimpleStringProperty(paymentMethod);
        this.paymentDate = new SimpleStringProperty(paymentDate);
        this.tenantId = new SimpleIntegerProperty(tenantId);
    }


    public String getService() {
        return service.get();
    }

    public SimpleStringProperty serviceProperty() {
        return service;
    }

    // For Issue Date Column

    public String getIssueDate() {
        return issueDate.get();
    }

    public SimpleStringProperty issueDateProperty() {
        return issueDate;
    }

    // For Discount Column

    public Double getDiscount() {
        return discount.get();
    }

    public SimpleDoubleProperty discountProperty() {
        return discount;
    }

    // For Amount Due Column

    public Double getAmountDue() {
        return amountDue.get();
    }

    public SimpleDoubleProperty amountDueProperty() {
        return amountDue;
    }

    // For Late Fee Column

    public Double getLateFee() {
        return lateFee.get();
    }

    public SimpleDoubleProperty lateFeeProperty() {
        return lateFee;
    }

    // For Total Amount Column

    public Double getTotalAmount() {
        return totalAmount.get();
    }

    public SimpleDoubleProperty totalAmountProperty() {
        return totalAmount;
    }

    // For Due Date Column

    public String getDueDate() {
        return dueDate.get();
    }

    public SimpleStringProperty dueDateProperty() {
        return dueDate;
    }

    // For Status Column
    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }

    // For Payment Method Column

    public String getPaymentMethod() {
        return paymentMethod.get();
    }

    public SimpleStringProperty paymentMethodProperty() {
        return paymentMethod;
    }

    // For Payment Date Column

    public String getPaymentDate() {
        return paymentDate.get();
    }

    public SimpleStringProperty paymentDateProperty() {
        return paymentDate;
    }

    // For Tenant ID Column

    public Integer getTenantId() {
        return tenantId.get();
    }

    public SimpleIntegerProperty tenantIdProperty() {
        return tenantId;
    }

    public double getAmount() {
        return totalAmount.get();
    }
}