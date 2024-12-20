package jm.task.core.jdbc.util;

public final class SqlString {
    public static final String CREATE;
    public static final String GETALLHQL;
    public static final String DROP;
    public static final String SAVE;
    public static final String REMOVE;
    public static final String GETALLSQL;
    public static final String CLEAN;

    static {

        CREATE = """
                CREATE TABLE IF NOT EXISTS users
                (
                    id        INT PRIMARY KEY AUTO_INCREMENT,
                    name      VARCHAR(45) NOT NULL,
                    last_name VARCHAR(45) NOT NULL,
                    age       TINYINT     NOT NULL
                )
                """;

        GETALLHQL = """
                select user from User user
                """;

        DROP = """
                DROP TABLE IF EXISTS users
                """;
        SAVE = """
                INSERT INTO users
                (
                    name, last_name, age
                ) VALUES (?,?,?)
                """;
        REMOVE = """
                DELETE FROM users
                WHERE id =?
                """;
        GETALLSQL = """
                SELECT
                    id,
                    name,
                    last_name,
                    age
                FROM users
                """;
        CLEAN = """
                TRUNCATE TABLE users
                """;
    }
    private SqlString() {
    }
}