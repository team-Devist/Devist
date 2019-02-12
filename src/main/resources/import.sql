insert into users (username, password, enabled) values ('nesoy', '$2a$10$uwwQHwBZY9aEtuiSgW7I3eNyc0JzSmK9UAFI3o8abnii/SmeKZXlK', 1);
insert into authorities (username, authority) values ('nesoy', 'ROLE_USER');

insert into users (username, password, enabled) values ('cjh5414', '$2a$10$uwwQHwBZY9aEtuiSgW7I3eNyc0JzSmK9UAFI3o8abnii/SmeKZXlK', 1);
insert into authorities (username, authority) values ('cjh5414', 'ROLE_USER');

insert into repeat_day (repeat_type, id, days_of_week) values ('FixedRepeatDay', 0, 127);
insert into repeat_day (repeat_type, id, days_of_week) values ('FixedRepeatDay', 1, 127);
insert into repeat_day (repeat_type, id, days_of_week) values ('FixedRepeatDay', 2, 127);
insert into repeat_day (repeat_type, id, days_of_week) values ('FixedRepeatDay', 3, 127);
insert into repeat_day (repeat_type, id, days_of_week) values ('FixedRepeatDay', 4, 127);

insert into todos (todo_id, title, description, created_time, done_rate, username) values (0, '매일 하는 일', '백준에서 알고리즘 문제 1개 풀기.', '2019-01-01 16:54:04.000', 0.0, 'cjh5414');
insert into todos (todo_id, title, description, created_time, done_rate, username) values (1, 'spring 동영상 강의 보기', 'youtube spring 검색. www.youtube.com', '2019-01-02 11:20:14.000', 0,'cjh5414');
insert into todos (todo_id, title, description, created_time, done_rate, username) values (2, '블로그 쓰기', '수요일 마다', '2019-01-02 11:20:14.000', 0,'cjh5414');
insert into todos (todo_id, title, description, created_time, done_rate, username) values (3, '완료된 할 일', '매일 하는 일인데 테스트 할라고 완료 시킴', '2019-01-02 11:20:14.000', 0, 'cjh5414');
insert into todos (todo_id, title, description, created_time, done_rate, username) values (4, 'Daily check 많이 가지고 있는 할 일', 'Daily Check 4에 1개만 완료.', '2018-01-02 11:20:14.000', 25.00, 'cjh5414');

insert into daily_checks (daily_check_id, planed_date, is_done, todo_id) values (0, '2018-12-22', 1, 3);

insert into daily_checks (daily_check_id, planed_date, is_done, todo_id) values (1, '2018-12-20', 0, 4);
insert into daily_checks (daily_check_id, planed_date, is_done, todo_id) values (2, '2018-12-21', 0, 4);
insert into daily_checks (daily_check_id, planed_date, is_done, todo_id) values (3, '2018-12-22', 1, 4);
insert into daily_checks (daily_check_id, planed_date, is_done, todo_id) values (4, '2018-12-23', 0, 4);