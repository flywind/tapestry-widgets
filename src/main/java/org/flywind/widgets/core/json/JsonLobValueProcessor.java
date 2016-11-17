package org.flywind.widgets.core.json;

import java.sql.Clob;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class JsonLobValueProcessor implements JsonValueProcessor {

    public Object processArrayValue(Object value, JsonConfig jsonConfig) {
        return process(value);
    }

    public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
        return process(value);
    }

    private Object process(Object value) {
        try {
            if (value instanceof Clob) {
            	Clob clob = (Clob) value;
                return (clob != null ? clob.getSubString(1, (int) clob.length()) : null);
            }
            return value == null ? "" : value.toString();
        } catch (Exception e) {
            return "";
        }

    }

}
