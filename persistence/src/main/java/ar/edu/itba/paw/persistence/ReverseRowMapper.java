package ar.edu.itba.paw.persistence;

import java.util.Map;

/**
 * Created by juanfra on 05/04/17.
 */
interface ReverseRowMapper<T> {
    Map<String, Object> toArgs(T t);
}
