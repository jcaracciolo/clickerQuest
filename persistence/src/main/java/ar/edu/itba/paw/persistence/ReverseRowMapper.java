package ar.edu.itba.paw.persistence;

import java.util.Map;

/**
 * Created by juanfra on 05/04/17.
 */
public interface ReverseRowMapper<T> {
    public Map<String, Object> toArgs(T t);
}
