CREATE TABLE IF NOT EXISTS cram_schools (
    cram_school_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) UNIQUE
);

CREATE TABLE IF NOT EXISTS roles (
    role_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) UNIQUE
);

CREATE TABLE IF NOT EXISTS schools (
    school_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    cram_school_id INT NOT NULL,
    UNIQUE KEY(name, cram_school_id),
    FOREIGN KEY (cram_school_id) REFERENCES cram_schools(cram_school_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS students (
    student_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    el1 INT NOT NULL,
    code INT UNIQUE, -- 学生コードの一意性を保障
    name VARCHAR(255) NOT NULL,
    furigana VARCHAR(255) NOT NULL,
    cram_school_id INT NOT NULL,
    FOREIGN KEY (cram_school_id) REFERENCES cram_schools(cram_school_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS classes (
    class_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    subject VARCHAR(50) NOT NULL,
    name VARCHAR(50) NOT NULL,
    sort_order TINYINT(1) NOT NULL,--個別はすべて-1 を入れる。集団は一番順位が低いクラスを１として自然数を昇順に入れていく
    cram_school_id INT NOT NULL,
    is_individual BOOLEAN NOT NULL,--個別か集団か判定する
    FOREIGN KEY (cram_school_id) REFERENCES cram_schools(cram_school_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS regular_test_sets (
    regular_test_set_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    term INT NOT NULL,
    grade INT NOT NULL,
    semester INT NOT NULL,
    is_mid INT NOT NULL, -- 1なら中間
    cram_school_id INT NOT NULL,
    FOREIGN KEY (cram_school_id) REFERENCES cram_schools(cram_school_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS statuses (
    status_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS class_students (
    class_student_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    class_id INT NOT NULL,
    created_at DATE NOT NULL,
    changed_at DATE NOT NULL,
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE,
    FOREIGN KEY (class_id) REFERENCES classes(class_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS school_students (
    school_student_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    school_id INT NOT NULL,
    student_id INT NOT NULL,
    created_at DATE NOT NULL,
    changed_at DATE NOT NULL,
    FOREIGN KEY (school_id) REFERENCES schools(school_id) ON DELETE CASCADE,
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS status_students (
    status_student_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    status_id INT NOT NULL,
    student_id INT NOT NULL,
    created_at DATE NOT NULL,
    changed_at DATE NOT NULL,
    FOREIGN KEY (status_id) REFERENCES statuses(status_id) ON DELETE CASCADE,
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS regular_tests (
    regular_test_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regular_test_set_id INT NOT NULL,
    school_id INT NOT NULL,
    date DATE NOT NULL,
    japanese_full_score INT,--満点の情報
    math_full_score INT,
    english_full_score INT,
    science_full_score INT,
    social_full_score INT,
    music_full_score INT,
    art_full_score INT,
    tech_full_score INT,
    pe_full_score INT,
    japanese_average_score INT,
    math_average_score INT,
    english_average_score INT,
    science_average_score INT,
    social_average_score INT,
    music_average_score INT,
    art_average_score INT,
    tech_average_score INT,
    pe_average_score INT,
    UNIQUE KEY(regular_test_set_id, school_id),
    FOREIGN KEY (school_id) REFERENCES schools(school_id) ON DELETE CASCADE,
    FOREIGN KEY (regular_test_set_id) REFERENCES regular_test_sets(regular_test_set_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS regular_test_results (
    regular_test_result_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regular_test_id INT NOT NULL,
    student_id INT NOT NULL,
    japanese INT,
    math INT,
    english INT,
    science INT,
    social INT,
    music INT,
    art INT,
    tech INT,
    pe INT,
    total5 INT GENERATED ALWAYS AS (COALESCE(japanese,0) + COALESCE(math,0) + COALESCE(english,0) + COALESCE(science,0) + COALESCE(social,0)) STORED,
    total9 INT GENERATED ALWAYS AS (COALESCE(japanese,0) + COALESCE(math,0) + COALESCE(english,0) + COALESCE(science,0) + COALESCE(social,0) + COALESCE(music,0) + COALESCE(art,0) + COALESCE(tech,0) + COALESCE(pe,0)) STORED,
    FOREIGN KEY (regular_test_id) REFERENCES regular_tests(regular_test_id) ON DELETE CASCADE,
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS mock_tests (
    mock_test_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) UNIQUE,
    date DATE,
    cram_school_id INT NOT NULL,
    FOREIGN KEY (cram_school_id) REFERENCES cram_schools(cram_school_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS mock_test_results (
    mock_test_id INT NOT NULL,
    student_id INT NOT NULL,
    japanese INT,
    japanese_ss INT,
    math INT,
    math_ss INT,
    english INT,
    english_ss INT,
    science INT,
    science_ss INT,
    social INT,
    social_ss INT,
    total3 INT GENERATED ALWAYS AS (COALESCE(japanese,0) + COALESCE(math,0) + COALESCE(english,0)) STORED,
    total3_ss INT,
    total5 INT GENERATED ALWAYS AS (COALESCE(japanese,0) + COALESCE(math,0) + COALESCE(english,0) + COALESCE(science,0) + COALESCE(social,0)) STORED,
    total5_ss INT,
    dream_school1 VARCHAR(100),
    dream_school1_probability INT,
    dream_school2 VARCHAR(100),
    dream_school2_probability INT,
    dream_school3 VARCHAR(100),
    dream_school3_probability INT,
    dream_school4 VARCHAR(100),
    dream_school4_probability INT,
    dream_school5 VARCHAR(100),
    dream_school5_probability INT,
    dream_school6 VARCHAR(100),
    dream_school6_probability INT,
    PRIMARY KEY (mock_test_id, student_id),
    FOREIGN KEY (mock_test_id) REFERENCES mock_tests(mock_test_id) ON DELETE CASCADE,
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS school_record_sets (
    school_record_set_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    term INT NOT NULL,
    grade INT NOT NULL,
    semester INT NOT NULL,
    UNIQUE KEY (term, grade, semester),
    cram_school_id INT NOT NULL,
    FOREIGN KEY (cram_school_id) REFERENCES cram_schools(cram_school_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS school_records (
    school_record_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    school_id INT NOT NULL,
    school_record_set_id INT NOT NULL,
    UNIQUE KEY (school_id, school_record_set_id),
    FOREIGN KEY (school_id) REFERENCES schools(school_id) ON DELETE CASCADE,
    FOREIGN KEY (school_record_set_id) REFERENCES school_record_sets(school_record_set_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS school_record_results (
    school_record_result_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    school_record_id INT NOT NULL,
    japanese INT,
    math INT,
    english INT,
    science INT,
    social INT,
    music INT,
    art INT,
    tech INT,
    pe INT,
    total5 INT GENERATED ALWAYS AS (COALESCE(japanese,0) + COALESCE(math,0) + COALESCE(english,0) + COALESCE(science,0) + COALESCE(social,0)) STORED,
    total9 INT GENERATED ALWAYS AS (COALESCE(japanese,0) + COALESCE(math,0) + COALESCE(english,0) + COALESCE(science,0) + COALESCE(social,0) + COALESCE(music,0) + COALESCE(art,0) + COALESCE(tech,0) + COALESCE(pe,0)) STORED,
    adjusted_sum INT GENERATED ALWAYS AS (COALESCE(japanese,0) + COALESCE(math,0) + COALESCE(english,0) + COALESCE(science,0) + COALESCE(social,0) + 2*COALESCE(music,0) + 2*COALESCE(art,0) + 2*COALESCE(tech,0) + 2*COALESCE(pe,0)) STORED,
    UNIQUE KEY (student_id, school_record_id),
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE,
    FOREIGN KEY (school_record_id) REFERENCES school_records(school_record_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS users (
    user_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) UNIQUE,
    password VARCHAR(255),
    role_id INT NOT NULL,
    cram_school_id INT NOT NULL,
    FOREIGN KEY (role_id) REFERENCES roles(role_id) ON DELETE CASCADE,
    FOREIGN KEY (cram_school_id) REFERENCES cram_schools(cram_school_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS other_tests (
    other_test_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    school_id INT NOT NULL,
    date DATE NOT NULL,
    semester TINYINT(1) NOT NULL,
    grade TINYINT(1) NOT NULL,
    japanese_full_score INT,--満点の情報
    math_full_score INT,
    english_full_score INT,
    science_full_score INT,
    social_full_score INT,
    music_full_score INT,
    art_full_score INT,
    tech_full_score INT,
    pe_full_score INT,
    japanese_average_score INT,
    math_average_score INT,
    english_average_score INT,
    science_average_score INT,
    social_average_score INT,
    music_average_score INT,
    art_average_score INT,
    tech_average_score INT,
    pe_average_score INT,
    UNIQUE KEY (school_id, semester, grade),
    FOREIGN KEY (school_id) REFERENCES schools(school_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS other_test_results (
    other_test_result_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    other_test_id INT NOT NULL,
    student_id INT NOT NULL,
    japanese INT,--満点を入れる
    math INT,
    english INT,
    science INT,
    social INT,
    music INT,
    art INT,
    tech INT,
    pe INT,
    total5 INT GENERATED ALWAYS AS (COALESCE(japanese,0) + COALESCE(math,0) + COALESCE(english,0) + COALESCE(science,0) + COALESCE(social,0)) STORED,
    total9 INT GENERATED ALWAYS AS (COALESCE(japanese,0) + COALESCE(math,0) + COALESCE(english,0) + COALESCE(science,0) + COALESCE(social,0) + COALESCE(music,0) + COALESCE(art,0) + COALESCE(tech,0) + COALESCE(pe,0)) STORED,
    UNIQUE KEY(other_test_id, student_id),
    FOREIGN KEY (other_test_id) REFERENCES other_tests(other_test_id) ON DELETE CASCADE,
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS cram_school_users(
    cram_school_user_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    cram_school_id INT NOT NULL,
    user_id INT NOT NULL,
    UNIQUE KEY(cram_school_id,user_id),
    FOREIGN KEY (cram_school_id) REFERENCES cram_schools(cram_school_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE
);

--教師とクラスを結びつけるためのジャンクションテーブル
CREATE TABLE IF NOT EXISTS class_users(
    class_user_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    class_id INT NOT NULL,--クラス
    grade TINYINT(2) NOT NULL,--1から12まで
    user_id INT NOT NULL,--教師
    start_date DATE ,
    end_date DATE ,
    FOREIGN KEY(class_id) REFERENCES classes(class_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

--流入経路を保存するためのテーブル
CREATE TABLE IF NOT EXISTS funnels(
    funnel_id INT NOT NULL  PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS inquiries (
    inquiry_id INT AUTO_INCREMENT PRIMARY KEY,
    funnel_id INT NOT NULL,
    school_branch_id INT NOT NULL,
    student_id INT,
    introducer_id INT,
    el1 INT,
    name_kanji VARCHAR(100) NOT NULL,
    name_kana VARCHAR(100) NOT NULL,
    inquiry_date DATE NOT NULL,
    code INT,
    enrollment_date DATE,
    withdrawal_date DATE,
    FOREIGN KEY (funnel_id) REFERENCES funnels(funnel_id),
    FOREIGN KEY (cram_school_id) REFERENCES　cram_schools(cram_school_id),
    FOREIGN KEY (student_id) REFERENCES students(student_id),
    FOREIGN KEY (introducer_id) REFERENCES students(student_id)
);

CREATE TABLE IF NOT EXISTS actions (
    action_id INT AUTO_INCREMENT PRIMARY KEY,
    action_name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS action_histories (
    action_history_id INT AUTO_INCREMENT PRIMARY KEY,
    inquiry_id INT NOT NULL,
    action_id INT NOT NULL,
    action_date DATE NOT NULL,
    user_id INT NOT NULL,--担当者を入れる
    comment TEXT,
    FOREIGN KEY (inquiry_id) REFERENCES inquiries(inquiry_id),
    FOREIGN KEY (action_id) REFERENCES actions(action_id),
    FOREIGN KEY (staff_id) REFERENCES users(user_id)
);


