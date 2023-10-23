-- 더미 데이터 삽입
INSERT INTO MEMBER (member_id, mod_date, reg_date, member_address, member_email_id, member_name, password, phone_number, role)
VALUES
(3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Address', 'test@naver.com', 'User1', 'abc123', '010-1111-1111', 'USER'),
(4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Address', 'email2@example.com', 'User1', 'abc123', '010-2222-2222', 'USER'),
(5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Address', 'test', 'ADMIN', 'abc123', '010-3333-3333', 'ADMIN');


INSERT INTO BOARD (board_id, mod_date, reg_date, board_content, board_title, board_type, board_view_count, member_name, member_id) VALUES
(11, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Notice Content 1', 'Notice Title1', 'NOTICE', 100, 'ADMIN', 5),
(12, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Notice Content 2', 'Notice Title2', 'NOTICE', 50, 'ADMIN', 5),
(13, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Post Content 1', 'Post Title 1', 'POST', 75, 'User1', 3),
(14, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Post Content 2', 'Post Title 2', 'POST', 75, 'User1', 3),
(15, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Post Content 3', 'Post Title 3', 'POST', 75, 'User1', 3),
(16, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Post Content 4', 'Post Title 4', 'POST', 75, 'User1', 3),
(17, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Post Content 5', 'Post Title 5', 'POST', 75, 'User1', 3),
(18, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Post Content 6', 'Post Title 6', 'POST', 75, 'User1', 3),
(19, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Post Content 7', 'Post Title 7', 'POST', 75, 'User1', 3),
(20, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Post Content 8', 'Post Title 8', 'POST', 75, 'User1', 3),
(21, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Post Content 9', 'Post Title 9', 'POST', 75, 'User1', 3),
(22, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Post Content 10', 'Post Title 10', 'POST', 75, 'User1', 3);

---- Products 테이블에 데이터 추가
INSERT INTO PRODUCTS (products_id, product_name, product_price, stock_quantity, select_tag1, select_tag2, categories, image_path, detail_image_path, product_script)
VALUES
    (4, 'Product 1', 100, 50, 'Tag1', 'Tag2', 'FOOD', 'img/KakaoTalk_Photo_2023-08-24-17-31-37 003.jpeg', 'img/KakaoTalk_Photo_2023-08-24-17-31-37 003.jpeg', 'Product 1 description'),
    (5, 'Product 2', 150, 30, 'Tag1', 'Tag2', 'FOOD', 'img/KakaoTalk_Photo_2023-08-24-17-31-37 002.jpeg', 'img/KakaoTalk_Photo_2023-08-24-17-31-37 002.jpeg', 'Product 2 description'),
    (6, 'Product 3', 200, 20, 'Tag1', 'Tag2', 'STUFF', 'img/KakaoTalk_Photo_2023-08-24-17-31-37 004.jpeg', 'img/KakaoTalk_Photo_2023-08-24-17-31-37 004.jpeg', 'Product 3 description'),
    (7, 'Product 4', 100, 50, 'Tag1', 'Tag2', 'FOOD', 'img/KakaoTalk_Photo_2023-08-24-17-31-37 003.jpeg', 'img/KakaoTalk_Photo_2023-08-24-17-31-37 003.jpeg', 'Product 1 description'),
    (8, 'Product 5', 150, 30, 'Tag1', 'Tag2', 'FOOD', 'img/KakaoTalk_Photo_2023-08-24-17-31-37 002.jpeg', 'img/KakaoTalk_Photo_2023-08-24-17-31-37 002.jpeg', 'Product 2 description'),
    (9, 'Product 6', 200, 20, 'Tag1', 'Tag2', 'STUFF', 'img/KakaoTalk_Photo_2023-08-24-17-31-37 004.jpeg', 'img/KakaoTalk_Photo_2023-08-24-17-31-37 004.jpeg', 'Product 3 description'),
    (10, 'Product 7', 100, 50, 'Tag1', 'Tag2', 'FOOD', 'img/KakaoTalk_Photo_2023-08-24-17-31-37 003.jpeg', 'img/KakaoTalk_Photo_2023-08-24-17-31-37 003.jpeg', 'Product 1 description'),
    (11, 'Product 8', 150, 30, 'Tag1', 'Tag2', 'FOOD', 'img/KakaoTalk_Photo_2023-08-24-17-31-37 002.jpeg', 'img/KakaoTalk_Photo_2023-08-24-17-31-37 002.jpeg', 'Product 2 description'),
    (12, 'Product 9', 200, 20, 'Tag1', 'Tag2', 'STUFF', 'img/KakaoTalk_Photo_2023-08-24-17-31-37 004.jpeg', 'img/KakaoTalk_Photo_2023-08-24-17-31-37 004.jpeg', 'Product 3 description'),
    (13, 'Product 10', 100, 50, 'Tag1', 'Tag2', 'FOOD', 'img/KakaoTalk_Photo_2023-08-24-17-31-37 003.jpeg', 'img/KakaoTalk_Photo_2023-08-24-17-31-37 003.jpeg', 'Product 1 description'),
    (14, 'Product 11', 150, 30, 'Tag1', 'Tag2', 'FOOD', 'img/KakaoTalk_Photo_2023-08-24-17-31-37 002.jpeg', 'img/KakaoTalk_Photo_2023-08-24-17-31-37 002.jpeg', 'Product 2 description'),
    (15, 'Product 12', 200, 20, 'Tag1', 'Tag2', 'STUFF', 'img/KakaoTalk_Photo_2023-08-24-17-31-37 004.jpeg', 'img/KakaoTalk_Photo_2023-08-24-17-31-37 004.jpeg', 'Product 3 description');
-- Cart 테이블에 데이터 추가
INSERT INTO CART (id, member_id) VALUES (3, 3),(4, 4);
-- CartPRODUCTS 테이블에 데이터 추가
INSERT INTO CART_PRODUCTS (cart_id, products_id, count) VALUES(3, 4, 2), (3, 4, 2);