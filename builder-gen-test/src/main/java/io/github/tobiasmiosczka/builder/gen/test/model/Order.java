package io.github.tobiasmiosczka.builder.gen.test.model;

import io.github.tobiasmiosczka.builder.gen.test.model.address.Address;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Order {

    private Address address;
    private List<OrderPosition> positions;
    private boolean priority;
    private Set<String> tags;
    private Map<String, String> additionalProperties;
    private Map<String, Address> additionalAddresses;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<OrderPosition> getPositions() {
        return positions;
    }

    public void setPositions(List<OrderPosition> positions) {
        this.positions = positions;
    }

    public boolean isPriority() {
        return priority;
    }

    public void setPriority(boolean priority) {
        this.priority = priority;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public Map<String, String> getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(Map<String, String> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    public Map<String, Address> getAdditionalAddresses() {
        return additionalAddresses;
    }

    public void setAdditionalAddresses(Map<String, Address> additionalAddresses) {
        this.additionalAddresses = additionalAddresses;
    }
}
