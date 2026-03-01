DO LANGUAGE plpgsql '
DECLARE
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM pg_constraint
        WHERE conname = ''uk_answer_attempt_question''
    ) THEN
        ALTER TABLE user_data.answer_tab
            ADD CONSTRAINT uk_answer_attempt_question UNIQUE (attempt_id, question_id);
    END IF;
END;
';