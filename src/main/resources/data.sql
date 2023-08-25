INSERT INTO MEMBER VALUES (1, 'test', 'password1', 'User 1', 'address 1', '1111', 'USER');
INSERT INTO "Board" ("board_id", "member_id", "board_title", "board_content", "member_name", "board_reg_date",
 "board_mod_date", "board_view_count")VALUES (1, 1, '제목1', '내용1', 'aaaa', NOW(), NOW(), 0);