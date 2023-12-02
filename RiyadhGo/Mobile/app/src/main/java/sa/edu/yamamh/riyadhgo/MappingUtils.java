package sa.edu.yamamh.riyadhgo;

import android.util.Log;

import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import sa.edu.yamamh.riyadhgo.data.MeasureUnit;
import sa.edu.yamamh.riyadhgo.data.TransportMethodTypes;

public class MappingUtils {

    public static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static float getFloat(String key, Map<String, Object> data) {
        try {
            if (data.containsKey(key)) {
                String val = data.get(key) != null ?  data.get(key).toString() : "0";
                return Float.parseFloat(val);
            }
        } catch (Exception ex) {
            Log.e("MappingUtils", ex.getMessage());
        }
        return 0;
    }

    public static long getLong(String key, Map<String, Object> data) {
        try {
            if (data.containsKey(key)) {
                String val = data.get(key) != null ?  data.get(key).toString() : "0";
                return Long.parseLong(val);
            }
        } catch (Exception ex) {
            Log.e("MappingUtils", ex.getMessage());
        }
        return 0;
    }

    public static int getInt(String key, Map<String, Object> data) {
        try {
            if (data.containsKey(key)) {
                String val = data.get(key) != null ?  data.get(key).toString() : "0";
                return Integer.parseInt(val);
            }
        } catch (Exception ex) {
            Log.e("MappingUtils", ex.getMessage());
        }
        return 0;
    }

    public static double getDouble(String key, Map<String, Object> data) {
        try {
            if (data.containsKey(key)) {
                String val = data.get(key) != null ?  data.get(key).toString() : "0";
                return Double.parseDouble(val);
            }
        } catch (Exception ex) {
            Log.e("MappingUtils", ex.getMessage());
        }
        return 0;
    }

    public static String getString(String key, Map<String, Object> data) {
        try {
            if (data.containsKey(key)) {
                String val = data.get(key) != null ?  data.get(key).toString() : "";
                return  val;
            }
        } catch (Exception ex) {
            Log.e("MappingUtils", ex.getMessage());
        }
        return "";
    }

    public static boolean getBoolean(String key, Map<String, Object> data) {
        try {
            if (data.containsKey(key)) {
                String val = data.get(key) != null ?  data.get(key).toString() : "false";
                return Boolean.parseBoolean(val);
            }
        } catch (Exception ex) {
            Log.e("MappingUtils", ex.getMessage());
        }
        return false;
    }

    public static Map<String, Object> getMap(String key, Map<String, Object> data) {
        if (data.containsKey(key)) {
            Object val = data.get(key);
            if (val instanceof Map) {
                return (Map<String, Object>) val;
            }
        }
        return null;
    }

    public static LocalDateTime getLocalDateTime(String key, Map<String, Object> data) {
        try {
            if (data.containsKey(key)) {
                String val = data.get(key) != null ?  data.get(key).toString() : LocalDateTime.now().format(DATE_TIME_FORMATTER);
                return LocalDateTime.parse(val, DATE_TIME_FORMATTER);
            }
        } catch (Exception ex) {
            Log.e("MappingUtils", ex.getMessage());
        }

        return null;
    }

    public static MeasureUnit getMeasureUnit(String key, Map<String, Object> data) {
        try {
            if (data.containsKey(key)) {
                String val =data.get(key) != null ?  data.get(key).toString() : MeasureUnit.KiloMeter.toString();
                return MeasureUnit.valueOf(val);
            }
        } catch (Exception ex) {
            Log.e("MappingUtils", ex.getMessage());
        }
        return null;
    }

    public static TransportMethodTypes getTransportMethodType(String key, Map<String, Object> data) {
        try {
            if (data.containsKey(key)) {
                String val =data.get(key) != null ?  data.get(key).toString() : TransportMethodTypes.CAR.toString();
                return TransportMethodTypes.valueOf(val);
            }
        } catch (Exception ex) {
            Log.e("MappingUtils", ex.getMessage());
        }
        return null;
    }


    //JSON OBJECT METHODS.
    public static float getFloat(String key, JSONObject data) {
        try {
            if (data.has(key)) {
                String val = data.get(key) != null ?  data.get(key).toString() : "0";
                return Float.parseFloat(val);
            }
        } catch (Exception ex) {
            Log.e("MappingUtils", ex.getMessage());
        }
        return 0;
    }

    public static long getLong(String key, JSONObject data) {
        try {
            if (data.has(key)) {
                String val = data.get(key) != null ?  data.get(key).toString() : "0";
                return Long.parseLong((String) val);
            }
        } catch (Exception ex) {
            Log.e("MappingUtils", ex.getMessage());
        }
        return 0;
    }

    public static int getInt(String key, JSONObject data) {
        try {
            if (data.has(key)) {
                String val = data.get(key) != null ?  data.get(key).toString() : "0";
                return Integer.parseInt((String) val);
            }
        } catch (Exception ex) {
            Log.e("MappingUtils", ex.getMessage());
        }
        return 0;
    }

    public static double getDouble(String key, JSONObject data) {
        try {
            if (data.has(key)) {
                String val = data.get(key) != null ?  data.get(key).toString() : "0";
                return Double.parseDouble(val);
            }
        } catch (Exception ex) {
            Log.e("MappingUtils", ex.getMessage());
        }
        return 0;
    }

    public static String getString(String key, JSONObject data) {
        try {
            if (data.has(key)) {
                return data.get(key) != null ?  data.get(key).toString() : "0";
            }
        } catch (Exception ex) {
            Log.e("MappingUtils", ex.getMessage());
        }
        return "";
    }

    public static boolean getBoolean(String key, JSONObject data) {
        try {
            if (data.has(key)) {
                String val = data.get(key) != null ?  data.get(key).toString() : "false";
                return Boolean.parseBoolean(val);
            }
        } catch (Exception ex) {
            Log.e("MappingUtils", ex.getMessage());
        }
        return false;
    }

    public static Map<String, Object> getMap(String key, JSONObject data) {
        try {
            if (data.has(key)) {
                Object val = data.get(key);
                if (val instanceof Map) {
                    return (Map<String, Object>) val;
                }
            }
        } catch (Exception ex) {
            Log.e("MappingUtils", ex.getMessage());
        }
        return null;
    }

    public static LocalDateTime getLocalDateTime(String key, JSONObject data) {
        try {
            if (data.has(key)) {
                String val = data.getString(key);
                return LocalDateTime.parse(val, DATE_TIME_FORMATTER);
            }
        } catch (Exception ex) {
            Log.e("MappingUtils", ex.getMessage());
        }

        return null;
    }

    public static MeasureUnit getMeasureUnit(String key, JSONObject data) {
        try {
            if (data.has(key)) {
                String val = data.getString(key);
                return MeasureUnit.valueOf(val);
            }
        } catch (Exception ex) {
            Log.e("MappingUtils", ex.getMessage());
        }
        return null;
    }

    public static TransportMethodTypes getTransportMethodType(String key, JSONObject data) {
        try {
            if (data.has(key)) {
                String val = data.getString(key);
                return TransportMethodTypes.valueOf(val);
            }
        } catch (Exception ex) {
            Log.e("MappingUtils", ex.getMessage());
        }
        return null;
    }
}
