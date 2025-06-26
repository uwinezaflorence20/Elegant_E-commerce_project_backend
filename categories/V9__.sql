ALTER TABLE categories
    ADD created_at TIMESTAMP WITHOUT TIME ZONE;

ALTER TABLE categories
    ALTER COLUMN created_at SET NOT NULL;

ALTER TABLE categories
DROP
COLUMN image_url;