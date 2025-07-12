ALTER TABLE orders
    ADD total_price DECIMAL;

ALTER TABLE order_items
DROP
COLUMN price;

ALTER TABLE order_items
    ADD price DECIMAL;

ALTER TABLE product
DROP
COLUMN price;

ALTER TABLE product
    ADD price DECIMAL;

ALTER TABLE product
    ALTER COLUMN price DROP NOT NULL;