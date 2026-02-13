CREATE TABLE IF NOT EXISTS user_data.course_question_tab (
    course_id INTEGER REFERENCES user_data.course_tab(id) ON DELETE CASCADE,
    question_id INTEGER REFERENCES user_data.question_tab(id) ON DELETE CASCADE,
    PRIMARY KEY (course_id, question_id)
);