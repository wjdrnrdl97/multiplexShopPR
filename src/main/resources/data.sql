-- 더미 데이터 삽입
INSERT INTO MEMBER (mod_date, reg_date, member_address, member_email_id, member_name, password, phone_number, role)
VALUES
(CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '연수구 연수동 552-1', 'test@naver.com', '사용자1', 'abc123', '010-1111-1111', 'USER'),
(CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '주소2', 'email2@example.com', '사용자2', '암호2', '010-2222-2222', 'USER'),
(CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '주소3', 'email3@example.com', 'ADMIN', '암호3', '010-3333-3333', 'ADMIN');

---- Products 테이블에 데이터 추가
INSERT INTO PRODUCTS (products_id, product_name, product_price, stock_quantity, select_tag1, select_tag2, categories, image_path, detail_image_path, product_script)
VALUES
(1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '추석 연휴 기간내 주문 관련 공지사항 말씀 드립니다.', '[ NOTICE ] 주문 관련 공지 드립니다.', 'NOTICE', 100, '관리자', 3),
(2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '공지 사항2', '[ NOTICE ] 배송 관련 안내 말씀 드립니다.', 'NOTICE', 50, '관리자', 3),
(3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '게시글 내용 1', '게시글 제목 1', 'POST', 75, '사용자1', 1),
(4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '게시글 내용 2', '게시글 제목 2', 'POST', 75, '사용자1', 1),
(5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '게시글 내용 3', '게시글 제목 3', 'POST', 75, '사용자1', 1),
(6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '게시글 내용 4', '게시글 제목 4', 'POST', 75, '사용자1', 1),
(7, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '게시글 내용 5', '게시글 제목 5', 'POST', 75, '사용자1', 1),
(8, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '게시글 내용 6', '게시글 제목 6', 'POST', 75, '사용자1', 1),
(9, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '게시글 내용 7', '게시글 제목 7', 'POST', 75, '사용자1', 1),
(10, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '게시글 내용 8', '게시글 제목 8', 'POST', 75, '사용자1', 1),
(11, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '게시글 내용 9', '게시글 제목 9', 'POST', 75, '사용자1', 1),
(12, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '게시글 내용 10', '게시글 제목 10', 'POST', 75, '사용자1', 1),
(13, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '게시글 내용 11', '게시글 제목 11', 'POST', 75, '사용자1', 1),
(14, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '게시글 내용 12', '게시글 제목 12', 'POST', 75, '사용자1', 1),
(15, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '게시글 내용 13', '게시글 제목 13', 'POST', 75, '사용자1', 1),
(16, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '게시글 내용 14', '게시글 제목 14', 'POST', 75, '사용자1', 1),
(17, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '게시글 내용 15', '게시글 제목 15', 'POST', 75, '사용자1', 1),
(18, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '게시글 내용 16', '게시글 제목 16', 'POST', 75, '사용자1', 1),
(19, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '게시글 내용 17', '게시글 제목 17', 'POST', 75, '사용자1', 1),
(20, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '게시글 내용 18', '게시글 제목 18', 'POST', 75, '사용자1', 1),
(21, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '게시글 내용 19', '게시글 제목 19', 'POST', 75, '사용자1', 1),
(22, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '게시글 내용 20', '게시글 제목 20', 'POST', 75, '사용자1', 1),
(23, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '게시글 내용 20', '게시글 제목 21', 'POST', 75, '사용자1', 1),
(24, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '게시글 내용 20', '게시글 제목 22', 'POST', 75, '사용자1', 1),
(25, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '게시글 내용 20', '게시글 제목 23', 'POST', 75, '사용자1', 1),
(26, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '게시글 내용 20', '게시글 제목 24', 'POST', 75, '사용자1', 1),
(27, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '게시글 내용 20', '게시글 제목 25', 'POST', 75, '사용자1', 1),
(28, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '게시글 내용 20', '게시글 제목 26', 'POST', 75, '사용자1', 1),
(29, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '게시글 내용 20', '게시글 제목 27', 'POST', 75, '사용자1', 1),
(30, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '게시글 내용 20', '게시글 제목 28', 'POST', 75, '사용자1', 1),
(31, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '게시글 내용 20', '게시글 제목 29', 'POST', 75, '사용자1', 1),
(32, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '게시글 내용 20', '게시글 제목 30', 'POST', 75, '사용자1', 1),
(33, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '게시글 내용 20', '게시글 제목 31', 'POST', 75, '사용자1', 1),
(34, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '게시글 내용 20', '게시글 제목 32', 'POST', 75, '사용자1', 1),
(35, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '게시글 내용 20', '게시글 제목 33', 'POST', 75, '사용자1', 1),
(36, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '게시글 내용 20', '게시글 제목 34', 'POST', 75, '사용자1', 1),
(37, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '게시글 내용 20', '게시글 제목 35', 'POST', 75, '사용자1', 1);


INSERT INTO PRODUCTS (products_id, product_name, product_price, stock_quantity, select_tag1, select_tag2, categories, image_path, detail_image_path, product_script)
VALUES
(1, 'Product 1', 100, 50, 'Tag1', 'Tag2', 'FOOD', '/images/product1.jpg', '/images/detail1.jpg', 'Product 1 description'),
(2, 'Product 2', 150, 30, 'Tag1', 'Tag2', 'FOOD', '/images/product2.jpg', '/images/detail2.jpg', 'Product 2 description'),
(3, 'Product 3', 200, 20, 'Tag1', 'Tag2', 'STUFF', '/images/product3.jpg', '/images/detail3.jpg', 'Product 3 description');
-- Cart 테이블에 데이터 추가
INSERT INTO CART (id, member_id) VALUES (1, 1),(2, 2);
-- CartPRODUCTS 테이블에 데이터 추가
INSERT INTO CART_PRODUCTS (cart_id, products_id, count) VALUES(1, 1, 2), (1, 2, 2);
