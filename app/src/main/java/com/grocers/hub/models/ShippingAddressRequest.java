package com.grocers.hub.models;

public class ShippingAddressRequest {

    private AddressInformation addressInformation;

    public AddressInformation getAddressInformation() {
        return addressInformation;
    }

    public void setAddressInformation(AddressInformation addressInformation) {
        this.addressInformation = addressInformation;
    }



    public static class AddressInformation {
        private Shipping_address shipping_address;
        private ExtensionAttributes extension_attributes;
        private Billing_address billing_address;
        private String shipping_carrier_code;
        private String shipping_method_code;

        public ExtensionAttributes getExtension_attributes() {
            return extension_attributes;
        }

        public void setExtension_attributes(ExtensionAttributes extension_attributes) {
            this.extension_attributes = extension_attributes;
        }

        public Billing_address getBilling_address() {
            return billing_address;
        }

        public void setBilling_address(Billing_address billing_address) {
            this.billing_address = billing_address;
        }

        public Shipping_address getShipping_address() {
            return shipping_address;
        }

        public void setShipping_address(Shipping_address shipping_address) {
            this.shipping_address = shipping_address;
        }

        public String getShipping_carrier_code() {
            return shipping_carrier_code;
        }

        public void setShipping_carrier_code(String shipping_carrier_code) {
            this.shipping_carrier_code = shipping_carrier_code;
        }

        public String getShipping_method_code() {
            return shipping_method_code;
        }

        public void setShipping_method_code(String shipping_method_code) {
            this.shipping_method_code = shipping_method_code;
        }
    }

    public static class ExtensionAttributes{

    }

    public static class Shipping_address {
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


    public static class Billing_address {
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


}
