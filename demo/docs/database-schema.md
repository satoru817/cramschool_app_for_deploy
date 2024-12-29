```mermaid

erDiagram
    CRAM_SCHOOLS {
        INT cram_school_id PK
        VARCHAR name UNIQUE
    }

    ROLES {
        INT role_id PK
        VARCHAR name UNIQUE
    }

    SCHOOLS {
        INT school_id PK
        VARCHAR name
        INT cram_school_id FK
    }
    SCHOOLS }|..|| CRAM_SCHOOLS : "cram_school_id"

    STUDENTS {
        INT student_id PK
        INT el1
        INT code UNIQUE
        VARCHAR name
        INT cram_school_id FK
    }
    STUDENTS }|..|| CRAM_SCHOOLS : "cram_school_id"

    CLASSES {
        INT class_id PK
        VARCHAR subject
        VARCHAR name
        TINYINT sort_order
        INT cram_school_id FK
    }
    CLASSES }|..|| CRAM_SCHOOLS : "cram_school_id"

    REGULAR_TEST_SETS {
        INT regular_test_set_id PK
        INT term
        INT grade
        INT semester
        INT is_mid
        INT cram_school_id FK
    }
    REGULAR_TEST_SETS }|..|| CRAM_SCHOOLS : "cram_school_id"

    STATUSES {
        INT status_id PK
        VARCHAR name UNIQUE
    }

    CLASS_STUDENTS {
        INT class_student_id PK
        INT student_id FK
        INT class_id FK
        DATE created_at
        DATE changed_at
    }
    CLASS_STUDENTS }|..|| STUDENTS : "student_id"
    CLASS_STUDENTS }|..|| CLASSES : "class_id"

    SCHOOL_STUDENTS {
        INT school_student_id PK
        INT school_id FK
        INT student_id FK
        DATE created_at
        DATE changed_at
    }
    SCHOOL_STUDENTS }|..|| SCHOOLS : "school_id"
    SCHOOL_STUDENTS }|..|| STUDENTS : "student_id"

    STATUS_STUDENTS {
        INT status_student_id PK
        INT status_id FK
        INT student_id FK
        DATE created_at
        DATE changed_at
    }
    STATUS_STUDENTS }|..|| STATUSES : "status_id"
    STATUS_STUDENTS }|..|| STUDENTS : "student_id"

    REGULAR_TESTS {
        INT regular_test_id PK
        INT regular_test_set_id FK
        INT school_id FK
        DATE date
        INT japanese
        INT math
        INT english
        INT science
        INT social
        INT music
        INT art
        INT tech
        INT pe
    }
    REGULAR_TESTS }|..|| REGULAR_TEST_SETS : "regular_test_set_id"
    REGULAR_TESTS }|..|| SCHOOLS : "school_id"

    REGULAR_TEST_RESULTS {
        INT regular_test_result_id PK
        INT regular_test_id FK
        INT student_id FK
        INT japanese
        INT math
        INT english
        INT science
        INT social
        INT music
        INT art
        INT tech
        INT pe
    }
    REGULAR_TEST_RESULTS }|..|| REGULAR_TESTS : "regular_test_id"
    REGULAR_TEST_RESULTS }|..|| STUDENTS : "student_id"

    MOCK_TESTS {
        INT mock_test_id PK
        VARCHAR name UNIQUE
        DATE date
        INT cram_school_id FK
    }
    MOCK_TESTS }|..|| CRAM_SCHOOLS : "cram_school_id"

    MOCK_TEST_RESULTS {
        INT mock_test_id FK
        INT student_id FK
        INT japanese
        INT japanese_ss
        INT math
        INT math_ss
        INT english
        INT english_ss
        INT science
        INT science_ss
        INT social
        INT social_ss
        INT jme_ss
        INT jmess_ss
        VARCHAR dream_school1
        INT dream_school1_probability
        VARCHAR dream_school2
        INT dream_school2_probability
        VARCHAR dream_school3
        INT dream_school3_probability
        VARCHAR dream_school4
        INT dream_school4_probability
        VARCHAR dream_school5
        INT dream_school5_probability
        VARCHAR dream_school6
        INT dream_school6_probability
    }
    MOCK_TEST_RESULTS }|..|| MOCK_TESTS : "mock_test_id"
    MOCK_TEST_RESULTS }|..|| STUDENTS : "student_id"

    SCHOOL_RECORD_SETS {
        INT school_record_set_id PK
        INT term
        INT grade
        INT semester
        INT cram_school_id FK
    }
    SCHOOL_RECORD_SETS }|..|| CRAM_SCHOOLS : "cram_school_id"

    SCHOOL_RECORDS {
        INT school_record_id PK
        INT school_id FK
        INT school_record_set_id FK
    }
    SCHOOL_RECORDS }|..|| SCHOOLS : "school_id"
    SCHOOL_RECORDS }|..|| SCHOOL_RECORD_SETS : "school_record_set_id"

    SCHOOL_RECORD_RESULTS {
        INT school_record_result_id PK
        INT student_id FK
        INT school_record_id FK
        INT japanese
        INT math
        INT english
        INT science
        INT social
        INT music
        INT art
        INT tech
        INT pe
    }
    SCHOOL_RECORD_RESULTS }|..|| STUDENTS : "student_id"
    SCHOOL_RECORD_RESULTS }|..|| SCHOOL_RECORDS : "school_record_id"

    USERS {
        INT user_id PK
        VARCHAR name UNIQUE
        VARCHAR password
        INT role_id FK
        INT cram_school_id FK
    }
    USERS }|..|| ROLES : "role_id"
    USERS }|..|| CRAM_SCHOOLS : "cram_school_id"

    OTHER_TESTS {
        INT other_test_id PK
        VARCHAR name
        INT school_id FK
        DATE date
        TINYINT semester
        TINYINT grade
        INT japanese
        INT math
        INT english
        INT science
        INT social
        INT music
        INT art
        INT tech
        INT pe
    }
    OTHER_TESTS }|..|| SCHOOLS : "school_id"

    OTHER_TEST_RESULTS {
        INT other_test_result_id PK
        INT other_test_id FK
        INT student_id FK
        INT japanese
        INT math
        INT english
        INT science
        INT social
        INT music
        INT art
        INT tech
        INT pe
    }
    OTHER_TEST_RESULTS }|..|| OTHER_TESTS : "other_test_id"
    OTHER_TEST_RESULTS }|..|| STUDENTS : "student_id"
