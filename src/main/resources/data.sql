-- 더미 데이터 삽입
INSERT INTO MEMBER (member_id, mod_date, reg_date, member_address, member_email_id, member_name, password, phone_number, role)
VALUES
(1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '주소1', 'email1@example.com', '사용자1', '암호1', '010-1111-1111', 'USER'),
(2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '주소2', 'email2@example.com', '사용자2', '암호2', '010-2222-2222', 'USER'),
(3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '주소3', 'email3@example.com', 'ADMIN', '암호3', '010-3333-3333', 'ADMIN');

---- Products 테이블에 데이터 추가
INSERT INTO PRODUCTS (products_id, product_name, product_price, stock_quantity, select_tag1, select_tag2, categories, image_path, detail_image_path, product_script, order_quantity)
VALUES
    (1, 'Product 1', 100, 50, 'Tag1', 'Tag2', 'FOOD', '/images/product1.jpg', '/images/detail1.jpg', 'Product 1 description', 0),
    (2, 'Product 2', 150, 30, 'Tag1', 'Tag2', 'FOOD', '/images/product2.jpg', '/images/detail2.jpg', 'Product 2 description', 0),
    (3, 'Product 3', 200, 20, 'Tag1', 'Tag2', 'STUFF', '/images/product3.jpg', '/images/detail3.jpg', 'Product 3 description', 0);
-- Cart 테이블에 데이터 추가
INSERT INTO CART (id, member_id) VALUES (1, 1),(2, 2);
-- CartPRODUCTS 테이블에 데이터 추가
INSERT INTO CART_PRODUCTS (cart_id, products_id, count) VALUES(1, 1, 2), (1, 2, 1);