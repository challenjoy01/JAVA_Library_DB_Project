-- 0. 표준 도서 분류 테이블 (KDC 분류)
INSERT INTO book_category (category_code, category_name) VALUES ('000', '총류');
INSERT INTO book_category (category_code, category_name) VALUES ('100', '철학');
INSERT INTO book_category (category_code, category_name) VALUES ('200', '종교');
INSERT INTO book_category (category_code, category_name) VALUES ('300', '사회과학');
INSERT INTO book_category (category_code, category_name) VALUES ('400', '자연과학');
INSERT INTO book_category (category_code, category_name) VALUES ('500', '기술과학');
INSERT INTO book_category (category_code, category_name) VALUES ('600', '예술');
INSERT INTO book_category (category_code, category_name) VALUES ('700', '언어');
INSERT INTO book_category (category_code, category_name) VALUES ('800', '문학');
INSERT INTO book_category (category_code, category_name) VALUES ('900', '역사');

-- 1. 도서 종류 테이블 (샘플 10종: 단행본 + 시리즈)

INSERT INTO book_info (book_id, title, author, publisher, category_code, series_info)
VALUES ('9788936434595', '채식주의자', '한강', '창비', '800', 0);

INSERT INTO book_info (book_id, title, author, publisher, category_code, series_info)
VALUES ('9788983927620', '해리 포터와 마법사의 돌 1', '조앤 K. 롤링', '문학수첩', '800', 1);

INSERT INTO book_info (book_id, title, author, publisher, category_code, series_info)
VALUES ('9788983927750', '해리 포터와 비밀의 방', '조앤 K. 롤링', '문학수첩', '800', 2);

INSERT INTO book_info (book_id, title, author, publisher, category_code, series_info)
VALUES ('9788966260959', '클린 코드', '로버트 C. 마틴', '인사이트', '000', 0);

INSERT INTO book_info (book_id, title, author, publisher, category_code, series_info)
VALUES ('9788994492032', '자바의 정석', '남궁성', '도우출판', '000', 0);

INSERT INTO book_info (book_id, title, author, publisher, category_code, series_info)
VALUES ('9788934972464', '사피엔스', '유발 하라리', '김영사', '900', 0);

INSERT INTO book_info (book_id, title, author, publisher, category_code, series_info)
VALUES ('9788983711892', '코스모스', '칼 세이건', '사이언스북스', '400', 0);

INSERT INTO book_info (book_id, title, author, publisher, category_code, series_info)
VALUES ('9788937834790', '정의란 무엇인가', '마이클 샌델', '와이즈베리', '100', 0);

INSERT INTO book_info (book_id, title, author, publisher, category_code, series_info)
VALUES ('9791191056372', '돈의 심리학', '모건 하우절', '인플루엔셜', '300', 0);

INSERT INTO book_info (book_id, title, author, publisher, category_code, series_info)
VALUES ('9788932472959', '도시는 무엇으로 사는가', '유현준', '을유문화사', '500', 0);

-- 2. 실물 도서 테이블 (샘플 14권: 청구 기호는 규칙에 맞게 작성함)
INSERT INTO physical_book (physical_book_id, book_id, call_number) VALUES ('PB0001', '9788936434595', '800 한595채 c.1');
INSERT INTO physical_book (physical_book_id, book_id, call_number) VALUES ('PB0002', '9788936434595', '800 한595채 c.2');
INSERT INTO physical_book (physical_book_id, book_id, call_number) VALUES ('PB0003', '9788983927620', '800 조620해 v.1 c.1');
INSERT INTO physical_book (physical_book_id, book_id, call_number) VALUES ('PB0004', '9788983927620', '800 조620해 v.1 c.2');
INSERT INTO physical_book (physical_book_id, book_id, call_number) VALUES ('PB0005', '9788983927750', '800 조750해 v.2 c.1');
INSERT INTO physical_book (physical_book_id, book_id, call_number) VALUES ('PB0006', '9788966260959', '000 로959클 c.1');
INSERT INTO physical_book (physical_book_id, book_id, call_number) VALUES ('PB0007', '9788966260959', '000 로959클 c.2');
INSERT INTO physical_book (physical_book_id, book_id, call_number) VALUES ('PB0008', '9788994492032', '000 남032자 c.1');
INSERT INTO physical_book (physical_book_id, book_id, call_number) VALUES ('PB0009', '9788934972464', '900 유464사 c.1');
INSERT INTO physical_book (physical_book_id, book_id, call_number) VALUES ('PB0010', '9788934972464', '900 유464사 c.2');
INSERT INTO physical_book (physical_book_id, book_id, call_number) VALUES ('PB0011', '9788983711892', '400 칼892코 c.1');
INSERT INTO physical_book (physical_book_id, book_id, call_number) VALUES ('PB0012', '9788937834790', '100 마790정 c.1');
INSERT INTO physical_book (physical_book_id, book_id, call_number) VALUES ('PB0013', '9791191056372', '300 모372돈 c.1');
INSERT INTO physical_book (physical_book_id, book_id, call_number) VALUES ('PB0014', '9788932472959', '500 유959도 c.1');

-- 3. 회원 테이블 (관리자 1명 + 일반 회원 3명)
INSERT INTO member (member_number, user_id, password, user_name, phone, user_type)
VALUES (1, 'admin', 'admin1234', '관리자', '010-0000-0001', 'ADMIN');

INSERT INTO member (member_number, user_id, password, user_name, phone, user_type)
VALUES (2, 'hong01', '1234', '홍길동', '010-1111-2222', 'USER');

INSERT INTO member (member_number, user_id, password, user_name, phone, user_type)
VALUES (3, 'kim02', '1234', '김철수', '010-3333-4444', 'USER');

INSERT INTO member (member_number, user_id, password, user_name, phone, user_type)
VALUES (4, 'lee03', '1234', '이영희', '010-5555-6666', 'USER');

-- 4. 대출 테이블 

-- 테스트용 데이터를 넣는 insert문.(정상 대출 3건 + 연체 1건)

-- 홍길동: 정상 대출 2권
INSERT INTO book_loan (physical_book_id, member_number, loan_date, return_date)
VALUES ('PB0001', 2, SYSDATE - 3, SYSDATE + 11);

INSERT INTO book_loan (physical_book_id, member_number, loan_date, return_date)
VALUES ('PB0006', 2, SYSDATE - 1, SYSDATE + 13);

-- 김철수: 연체 1권 + 정상 대출 1권
-- 연채
INSERT INTO book_loan (physical_book_id, member_number, loan_date, return_date)
VALUES ('PB0003', 3, SYSDATE - 20, SYSDATE - 6);

INSERT INTO book_loan (physical_book_id, member_number, loan_date, return_date)
VALUES ('PB0008', 3, SYSDATE - 5, SYSDATE + 9);

COMMIT;