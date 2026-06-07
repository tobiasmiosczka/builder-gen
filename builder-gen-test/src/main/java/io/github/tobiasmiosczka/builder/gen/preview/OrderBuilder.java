package io.github.tobiasmiosczka.builder.gen.preview;

import io.github.tobiasmiosczka.builder.gen.test.model.address.Address;
import io.github.tobiasmiosczka.builder.gen.test.model.Order;
import io.github.tobiasmiosczka.builder.gen.test.model.OrderPosition;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class OrderBuilder implements Supplier<Order> {

    private Supplier<Address> address;
    private List<Supplier<OrderPosition>> positions;

    private OrderBuilder(){
    }

    private OrderBuilder(OrderBuilder original) {
        this.address = original.address;
        this.positions = original.positions;
    }

    public static OrderBuilder orderBuilder() {
        return new OrderBuilder();
    }

    @Override
    public Order get() {
        var result = new Order();
        result.setAddress(address.get());
        result.setPositions(positions.stream().map(Supplier::get).collect(Collectors.toList()));
        return result;
    }

    public OrderBuilder but() {
        return new OrderBuilder(this);
    }

    public OrderBuilder address(Supplier<Address> value) {
        this.address = value;
        return this;
    }

    public OrderBuilder positions(List<Supplier<OrderPosition>> value) {
        this.positions = value;
        return this;
    }
}
