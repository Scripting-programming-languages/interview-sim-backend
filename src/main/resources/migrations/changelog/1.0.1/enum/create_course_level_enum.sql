DO LANGUAGE plpgsql '
DECLARE
BEGIN
    IF to_regtype(''user_data.course_level_enum'') IS NULL THEN
        CREATE TYPE user_data.course_level_enum AS ENUM (''junior'', ''middle'', ''senior'');
    end if;
END;
';