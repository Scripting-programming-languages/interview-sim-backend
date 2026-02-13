CREATE TABLE IF NOT EXISTS user_data.course_tab (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    level user_data.course_level_enum NOT NULL
);