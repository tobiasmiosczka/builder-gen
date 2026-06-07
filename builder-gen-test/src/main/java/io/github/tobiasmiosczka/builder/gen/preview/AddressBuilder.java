package io.github.tobiasmiosczka.builder.gen.preview;

import io.github.tobiasmiosczka.builder.gen.test.model.address.Address;

import java.util.function.Supplier;

public class AddressBuilder implements Supplier<Address> {

    private String postalCode;
    private String street;
    private String place;


    @Override
    public Address get() {
        var result = new Address();
        result.setPostalCode(this.postalCode);
        result.setStreet(this.street);
        result.setPlace(this.place);
        return result;
    }

    public AddressBuilder postalCode(String value) {
        this.postalCode = value;
        return this;
    }

    public AddressBuilder street(String value) {
        this.street = value;
        return this;
    }

    public AddressBuilder place(String value) {
        this.place = value;
        return this;
    }
}
