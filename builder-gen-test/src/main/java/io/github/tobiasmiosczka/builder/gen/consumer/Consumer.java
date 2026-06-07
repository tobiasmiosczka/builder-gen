package io.github.tobiasmiosczka.builder.gen.consumer;

import io.github.tobiasmiosczka.builder.gen.test.model.Order;

import static io.github.tobiasmiosczka.builder.gen.test.model.address.AddressBuilder.address;
import static io.github.tobiasmiosczka.builder.gen.test.model.OrderBuilder.order;

public class Consumer {

    static void main() {
        Order order = order()
                .address(address()
                        .postalCode("12345")
                        .place("Some Place")
                        .street("Some Street"))
                .get();
    }

}
