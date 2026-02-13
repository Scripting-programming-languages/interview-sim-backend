DO LANGUAGE plpgsql '
DECLARE
BEGIN
    IF to_regtype(''user_data.attempt_status_enum'') IS NULL THEN
        CREATE TYPE user_data.attempt_status_enum AS ENUM (''in_progress'', ''finished'', ''abandoned'');
    end if;
END;
';