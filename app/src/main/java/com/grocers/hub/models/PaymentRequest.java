package com.grocers.hub.models;

public class PaymentRequest {

    private String cartId;
    private String instamojo_order_id;
    private PaymentMethod paymentMethod;
    private BillingAddress billing_address;

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getInstamojo_order_id() {
        return instamojo_order_id;
    }

    public void setInstamojo_order_id(String instamojo_order_id) {
        this.instamojo_order_id = instamojo_order_id;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public BillingAddress getBilling_address() {
        return billing_address;
    }

    public void setBilling_address(BillingAddress billing_address) {
        this.billing_address = billing_address;
    }

    public static class PaymentMethod {

        private String method;
        private String po_number;
        private String additional_data;

        public String getPo_number() {
            return po_number;
        }

        public void setPo_number(String po_number) {
            this.po_number = po_number;
        }

        public String getAdditional_data() {
            return additional_data;
        }

        public void setAdditional_data(String additional_data) {
            this.additional_data = additional_data;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }
    }

    public static class BillingAddress {

        private String customerAddressId;
        private String countryId;
        private String regionId;
        private String regionCode;
        private String region;
        private String customerId;
        private String street;
        private String telephone;
        private String postcode;
        private String city;
        private String firstname;
        private String lastname;
        private String saveInAddressBook;
        private PaymentRequest.ExtensionAttributes extension_attributes;

        public String getCustomerAddressId() {
            return customerAddressId;
        }

        public void setCustomerAddressId(String customerAddressId) {
            this.customerAddressId = customerAddressId;
        }

        public String getCountryId() {
            return countryId;
        }

        public void setCountryId(String countryId) {
            this.countryId = countryId;
        }

        public String getRegionId() {
            return regionId;
        }

        public void setRegionId(String regionId) {
            this.regionId = regionId;
        }

        public String getRegionCode() {
            return regionCode;
        }

        public void setRegionCode(String regionCode) {
            this.regionCode = regionCode;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getPostcode() {
            return postcode;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getSaveInAddressBook() {
            return saveInAddressBook;
        }

        public void setSaveInAddressBook(String saveInAddressBook) {
            this.saveInAddressBook = saveInAddressBook;
        }

        public ExtensionAttributes getExtension_attributes() {
            return extension_attributes;
        }

        public void setExtension_attributes(ExtensionAttributes extension_attributes) {
            this.extension_attributes = extension_attributes;
        }
    }

    public static class ExtensionAttributes {

    }
}
