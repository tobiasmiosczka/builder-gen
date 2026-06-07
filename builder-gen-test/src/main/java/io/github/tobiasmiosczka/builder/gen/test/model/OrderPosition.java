package io.github.tobiasmiosczka.builder.gen.test.model;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderPosition {

    private UUID articleId;
    private int quantity;
    private BigDecimal price;

    public UUID getArticleId() {
        return articleId;
    }

    public void setArticleId(UUID articleId) {
        this.articleId = articleId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
