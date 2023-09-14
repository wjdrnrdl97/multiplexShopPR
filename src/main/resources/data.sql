INSERT INTO MEMBER (member_id, mod_date, reg_date, member_address, member_email_id, member_name, password, phone_number, role)
VALUES
(99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '주소1', 'email1@example.com', '사용자1', '암호1', '010-1111-1111', 'USER');
INSERT INTO ORDERS (member_id, order_status, order_price)
VALUES (99, 'ORDER', 100.0);
