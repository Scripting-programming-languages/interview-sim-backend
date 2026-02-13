CREATE TABLE IF NOT EXISTS user_data.attempt_tab (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES user_data.user_tab(id),
    course_id INTEGER NOT NULL REFERENCES user_data.course_tab(id),
    status user_data.attempt_status_enum DEFAULT 'in_progress',
    overall_score INTEGER,
    overall_feedback TEXT,
    timestamp_start TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    timestamp_end TIMESTAMP WITH TIME ZONE
);