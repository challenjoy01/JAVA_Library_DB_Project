-- 0. 표준 도서 분류 테이블
CREATE TABLE book_category (
    category_code VARCHAR2(20) PRIMARY KEY, -- 분류 코드('800' 등 3자리 숫자 )
    category_name VARCHAR2(100) UNIQUE NOT NULL    -- 분류명 ('문학' 등 분류 문자열)
);

-- 1. 도서 종류 테이블
CREATE TABLE book_info (
    book_id VARCHAR2(50) PRIMARY KEY,   -- 도서 ID(ISBN)
    title VARCHAR2(200) NOT NULL,       -- 책 이름
    author VARCHAR2(100) NOT NULL,      -- 저자
    publisher VARCHAR2(100),            -- 출판사
    category_code VARCHAR2(20) NOT NULL,    -- 분류 코드 (예: 문학일 경우 800)
    series_info INT NOT NULL,           -- 시리즈 여부 및 몇 권인지(시리즈 아니면 0 시리즈일 경우 몇 권인지)
    FOREIGN KEY (category_code) REFERENCES book_category(category_code)
);

-- 2. 실물 도서 테이블
CREATE TABLE physical_book (
    physical_book_id VARCHAR2(50) PRIMARY KEY, -- 실물 도서 ID
    book_id VARCHAR2(50),                      -- 도서 ID
    call_number VARCHAR2(50),                  -- 청구 기호 -> 정해진 규칙에 따라 저장해야 함
    FOREIGN KEY (book_id) REFERENCES book_info(book_id)
);

-- 3. 회원 테이블
CREATE TABLE member (
    member_number INT PRIMARY KEY,                      -- 회원 번호
    user_id VARCHAR2(50) UNIQUE NOT NULL,               -- 회원 아이디
    password VARCHAR2(100) NOT NULL,                    -- 비밀번호
    user_name VARCHAR2(50) NOT NULL,                    -- 회원 이름
    phone VARCHAR2(20),                                 -- 전화번호
    user_type VARCHAR2(20) DEFAULT 'USER' NOT NULL               -- 회원 유형, 권한('ADMIN', 'USER' 등)
);

-- 4. 대출 테이블
CREATE TABLE book_loan (
    physical_book_id VARCHAR2(50) PRIMARY KEY, -- 실물 도서 ID
    member_number INT NOT NULL,                         -- 회원 번호
    loan_date DATE DEFAULT SYSDATE,            -- 대출 일자
    return_date DATE,                          -- 반납 일자
    FOREIGN KEY (physical_book_id) REFERENCES physical_book(physical_book_id),
    FOREIGN KEY (member_number) REFERENCES member(member_number)
);

