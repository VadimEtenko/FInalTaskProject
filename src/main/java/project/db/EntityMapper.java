package project.db;

import java.sql.ResultSet;

/**
 * Nested class interface, for convert
 *  result set row to entities
 *
 * @param <T>
 *      entity of result set row
 */

public interface EntityMapper<T> {
    T mapRow(ResultSet rs);
}
