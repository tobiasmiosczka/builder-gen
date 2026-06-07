package io.github.tobiasmiosczka.builder.gen.preview;

import io.github.tobiasmiosczka.builder.gen.test.model.OrderPosition;

import java.util.UUID;
import java.util.function.Supplier;

public final class OrderPositionBuilder implements Supplier<OrderPosition> {

    private UUID articleId;
    private int quantity;

    private OrderPositionBuilder(OrderPositionBuilder original) {
        this.articleId = original.articleId;
        this.quantity = original.quantity;
    }

    private OrderPositionBuilder() {}

    public static OrderPositionBuilder orderPositionBuilder() {
        return new OrderPositionBuilder();
    }

    public OrderPositionBuilder but() {
        return new OrderPositionBuilder(this);
    }

    @Override
    public OrderPosition get() {
        var result = new OrderPosition();
        result.setArticleId(this.articleId);
        result.setQuantity(this.quantity);
        return result;
    }

    public OrderPositionBuilder articleId(UUID value) {
        this.articleId = value;
        return this;
    }

    public OrderPositionBuilder quantity(int value) {
        this.quantity = value;
        return this;
    }
}
