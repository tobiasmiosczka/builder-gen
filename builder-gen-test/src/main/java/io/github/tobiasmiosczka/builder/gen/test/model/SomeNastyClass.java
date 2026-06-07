package io.github.tobiasmiosczka.builder.gen.test.model;

import io.github.tobiasmiosczka.builder.gen.test.model.address.Address;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SomeNastyClass {

    private List<Address> addresses;
    private Map<String, List<Address>> addressListMap;
    private Map<UUID, Map<String, Address>> addressMapMap;
    private List<TestEnum> enums;
    private Map plainMap;

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public Map<String, List<Address>> getAddressListMap() {
        return addressListMap;
    }

    public void setAddressListMap(Map<String, List<Address>> addressListMap) {
        this.addressListMap = addressListMap;
    }

    public Map<UUID, Map<String, Address>> getAddressMapMap() {
        return addressMapMap;
    }

    public void setAddressMapMap(Map<UUID, Map<String, Address>> addressMapMap) {
        this.addressMapMap = addressMapMap;
    }

    public List<TestEnum> getEnums() {
        return enums;
    }

    public void setEnums(List<TestEnum> enums) {
        this.enums = enums;
    }

    public Map getPlainMap() {
        return plainMap;
    }

    public void setPlainMap(Map plainMap) {
        this.plainMap = plainMap;
    }
}
