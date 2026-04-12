ALTER TABLE user_data.attempt_tab
    ADD COLUMN IF NOT EXISTS overall_speech_score INTEGER;

ALTER TABLE user_data.attempt_tab
    ADD COLUMN IF NOT EXISTS overall_speech_feedback TEXT;
