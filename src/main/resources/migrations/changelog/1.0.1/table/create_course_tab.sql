CREATE TABLE IF NOT EXISTS user_data.course_tab (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(5000),
    level VARCHAR(255) NOT NULL
);