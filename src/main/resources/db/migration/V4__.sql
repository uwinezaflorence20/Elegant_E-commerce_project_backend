ALTER TABLE order_items
    ADD total_price DECIMAL;

ALTER TABLE order_items
DROP
COLUMN price;