package com.grocers.hub.models;

public class UpdatePayment {

    private String cartId;
    private String grant_total;
    private Billing_address billing_address;
    private PaymentMethod paymentMethod;

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getGrant_total() {
        return grant_total;
    }

    public void setGrant_total(String grant_total) {
        this.grant_total = grant_total;
    }

    public Billing_address getBilling_address() {
        return billing_address;
    }

    public void setBilling_address(Billing_address billing_address) {
        this.billing_address = billing_address;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public static class Billing_address {
        private String customerAddressId;
        private String email;
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
        private ExtensionAttributes extension_attributes;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public ExtensionAttributes getExtension_attributes() {
            return extension_attributes;
        }

        public void setExtension_attributes(ExtensionAttributes extension_attributes) {
            this.extension_attributes = extension_attributes;
        }

        public String getSaveInAddressBook() {
            return saveInAddressBook;
        }

        public void setSaveInAddressBook(String saveInAddressBook) {
            this.saveInAddressBook = saveInAddressBook;
        }

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
    }

    public static class ExtensionAttributes {

    }

    public static class PaymentMethod {
        private String method;
        private String po_number;
        private String additional_data;

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

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
    }
}
