CREATE TABLE cart_item
(
    id         BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    user_id    BIGINT,
    product_id BIGINT,
    quantity   INTEGER                                 NOT NULL,
    CONSTRAINT pk_cartitem PRIMARY KEY (id)
);

CREATE TABLE review
(
    id         BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    user_id    BIGINT,
    product_id BIGINT,
    comment    VARCHAR(255),
    rating     INTEGER                                 NOT NULL,
    CONSTRAINT pk_review PRIMARY KEY (id)
);

ALTER TABLE cart_item
    ADD CONSTRAINT FK_CARTITEM_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (id);

ALTER TABLE cart_item
    ADD CONSTRAINT FK_CARTITEM_ON_USER FOREIGN KEY (user_id) REFERENCES user_table (id);

ALTER TABLE review
    ADD CONSTRAINT FK_REVIEW_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (id);

ALTER TABLE review
    ADD CONSTRAINT FK_REVIEW_ON_USER FOREIGN KEY (user_id) REFERENCES user_table (id);