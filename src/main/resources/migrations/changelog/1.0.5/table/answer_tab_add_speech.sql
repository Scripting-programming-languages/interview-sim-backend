ALTER TABLE user_data.answer_tab
ADD COLUMN IF NOT EXISTS speech_score INTEGER;

ALTER TABLE user_data.answer_tab
ADD COLUMN IF NOT EXISTS speech_feedback TEXT;

