CREATE TABLE IF NOT EXISTS user_data.course_category_tab (
    course_id INTEGER REFERENCES user_data.course_tab(id) ON DELETE CASCADE,
    category_id INTEGER REFERENCES user_data.category_tab(id) ON DELETE CASCADE,
    PRIMARY KEY (course_id, category_id)
);