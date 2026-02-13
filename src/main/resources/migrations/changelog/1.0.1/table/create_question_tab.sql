CREATE TABLE IF NOT EXISTS user_data.question_tab (
    id SERIAL PRIMARY KEY,
    text TEXT NOT NULL,
    complexity INTEGER,
    correct_answer TEXT,
    key_words TEXT[]
);