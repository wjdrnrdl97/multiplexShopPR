-- 더미 데이터 삽입
INSERT INTO MEMBER (member_id, mod_date, reg_date, member_address, member_email_id, member_name, password, phone_number, role)
VALUES
(1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '주소1', 'email1@example.com', '사용자1', '암호1', '010-1111-1111', 'USER'),
(2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '주소2', 'email2@example.com', '사용자2', '암호2', '010-2222-2222', 'USER'),
(3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '주소3', 'email3@example.com', 'ADMIN', '암호3', '010-3333-3333', 'ADMIN');


-- 더미 데이터 삽입
INSERT INTO BOARD (board_id, mod_date, reg_date, board_content, board_title, board_type, board_view_count, member_name, member_id)
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
