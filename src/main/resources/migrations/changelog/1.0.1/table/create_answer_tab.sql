CREATE TABLE IF NOT EXISTS user_data.answer_tab (
    id SERIAL PRIMARY KEY,
    attempt_id INTEGER NOT NULL REFERENCES user_data.attempt_tab(id) ON DELETE CASCADE,
    question_id INTEGER NOT NULL REFERENCES user_data.question_tab(id),
    user_answer TEXT,
    score INTEGER,
    feedback TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);