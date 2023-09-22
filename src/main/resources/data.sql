-- 더미 데이터 삽입
INSERT INTO MEMBER (member_id, mod_date, reg_date, member_address, member_email_id, member_name, password, phone_number, role)
VALUES
(1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '주소1', 'email1@example.com', '사용자1', '암호1', '010-1111-1111', 'USER'),
(2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '주소2', 'email2@example.com', '사용자2', '암호2', '010-2222-2222', 'USER'),
(3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '주소3', 'email3@example.com', 'ADMIN', '암호3', '010-3333-3333', 'ADMIN');