insert into users (username, password, enabled) values ('nesoy', '$2a$10$uwwQHwBZY9aEtuiSgW7I3eNyc0JzSmK9UAFI3o8abnii/SmeKZXlK', 1);
insert into authorities (username, authority) values ('nesoy', 'ROLE_USER');

insert into users (username, password, enabled) values ('cjh5414', '$2a$10$uwwQHwBZY9aEtuiSgW7I3eNyc0JzSmK9UAFI3o8abnii/SmeKZXlK', 1);
insert into authorities (username, authority) values ('cjh5414', 'ROLE_USER');

insert into todos (todo_id, title, description, created_time, done_rate, username) values (1, '매일 하는 일', '백준에서 알고리즘 문제 1개 풀기.', '2019-01-01 16:54:04.000', 0.0, 'cjh5414');
insert into todos (todo_id, title, description, created_time, done_rate, username) values (2, 'spring 동영상 강의 보기', 'youtube spring 검색. www.youtube.com', '2019-01-02 11:20:14.000', 0,'cjh5414');
insert into todos (todo_id, title, description, created_time, done_rate, username) values (3, '블로그 쓰기', '수요일 마다', '2019-01-02 11:20:14.000', 0,'cjh5414');
insert into todos (todo_id, title, description, created_time, done_rate, username) values (4, '완료된 할 일', '매일 하는 일인데 테스트 할라고 완료 시킴', '2019-01-02 11:20:14.000', 0, 'cjh5414');
insert into todos (todo_id, title, description, created_time, done_rate, username) values (5, 'Daily check 많이 가지고 있는 할 일', 'Daily Check 4에 1개만 완료.', '2018-01-02 11:20:14.000', 25.00, 'cjh5414');
insert into todos (todo_id, title, description, created_time, done_rate, username) values (6, '주4회 중 3회 완료', '주4회 중 3회 완료.', '2018-01-02 11:20:14.000', 25.00, 'cjh5414');
insert into todos (todo_id, title, description, created_time, done_rate, username) values (7, '주4회 중 4회 완료', '주4회 중 4회 완료.', '2018-01-03 11:10:14.000', 0.00, 'cjh5414');


insert into repeat_day (repeat_type, todo_id, days_of_week) values ('FixedRepeatDay', 1, 127);
insert into repeat_day (repeat_type, todo_id, days_of_week) values ('FixedRepeatDay', 2, 127);
insert into repeat_day (repeat_type, todo_id, days_of_week) values ('FixedRepeatDay', 3, 127);
insert into repeat_day (repeat_type, todo_id, days_of_week) values ('FixedRepeatDay', 4, 127);
insert into repeat_day (repeat_type, todo_id, days_of_week) values ('FixedRepeatDay', 5, 127);
insert into repeat_day (repeat_type, todo_id, doing_count, weeks_count, init_day) values ('FlexibleRepeatDay', 6, 3, 4, 1);
insert into repeat_day (repeat_type, todo_id, doing_count, weeks_count, init_day) values ('FlexibleRepeatDay', 7, 7, 4, 1);

insert into daily_checks (daily_check_id, planed_date, is_done, todo_id) values (1, '2018-12-22', 1, 4);
insert into daily_checks (daily_check_id, planed_date, is_done, todo_id) values (2, '2018-12-20', 0, 5);
insert into daily_checks (daily_check_id, planed_date, is_done, todo_id) values (3, '2018-12-21', 0, 5);
insert into daily_checks (daily_check_id, planed_date, is_done, todo_id) values (4, '2018-12-22', 1, 5);
insert into daily_checks (daily_check_id, planed_date, is_done, todo_id) values (5, '2018-12-24', 0, 5);