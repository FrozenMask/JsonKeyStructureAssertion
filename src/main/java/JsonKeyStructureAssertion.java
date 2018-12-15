import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.jmeter.assertions.Assertion;
import org.apache.jmeter.assertions.AssertionResult;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.testelement.AbstractTestElement;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

public class JsonKeyStructureAssertion extends AbstractTestElement implements Serializable, Assertion {

    /**
     *
     */
    private static final long serialVersionUID = -1589642660440130543L;
    private static final String JSONSTRING = "JSON_STRING";
    private static final String JSONKEYSTRUCTURE = "JSON_KEY_STRUCTURE";

    public void setJsonKeyStructure(String jsonKeyStructure) {
        setProperty(JSONKEYSTRUCTURE, jsonKeyStructure);
    }

    public void setJsonString(String jsonString) {
        setProperty(JSONSTRING, jsonString);
    }

    public String getJsonKeyStructure() {
        return getPropertyAsString(JSONKEYSTRUCTURE);
    }

    public String getJsonString() {
        return getPropertyAsString(JSONSTRING);
    }

    @Override
    public AssertionResult getResult(SampleResult arg0) {
        AssertionResult result = new AssertionResult(getName());
        String responseData = arg0.getResponseDataAsString();
        if (responseData.isEmpty()) {
            return result.setResultForNull();
        }

        result.setFailure(false);
        result.setFailureMessage("");
        try {
            doAssert(responseData);
        } catch (IllegalStateException e) {
            result.setFailure(true);
            result.setFailureMessage(e.getMessage());
        }
        return result;
    }

    private void doAssert(String jsonString) {
        JSON json = (JSON) JSON.parse(jsonString);
        StringBuffer jsonPath = new StringBuffer();
        printJsonPath(json, jsonPath);
        if (jsonPath.toString().equals(getJsonKeyStructure())) {
            return;
        }else {
            String msg="Value expected to be '%s', but found '%s'";
            throw new IllegalStateException(String.format(msg, getProperty(JSONKEYSTRUCTURE), jsonPath));
        }
    }

    private boolean isBasicType(Object javaObject) {
        boolean basicType = javaObject instanceof String || javaObject instanceof Number || javaObject instanceof Date
                || javaObject instanceof UUID;
        return basicType;
    }

    private void parshJsonArray(JSONArray array, StringBuffer jsonPath) {
        jsonPath.append("[");
        if (array.size() > 0) {
            Object arrayObject = array.get(0);
            if (arrayObject instanceof JSONArray) {
                parshJsonArray((JSONArray) arrayObject, jsonPath);
            } else if (arrayObject instanceof JSONObject) {
                printJsonPath((JSON) arrayObject, jsonPath);
            }
        }
        jsonPath.append("]");
    }

    private void printJsonPath(JSON json, StringBuffer jsonPath) {
        if (json instanceof JSONObject) {
            jsonPath.append("{");
            Set<String> keySet = ((JSONObject) json).keySet();
            int i = 0;
            for (String key : keySet) {
                if (i != 0) {
                    jsonPath.append(",");
                }
                jsonPath.append("\""+ key + "\":");
                Object value = ((JSONObject) json).get(key);
                if (isBasicType(value)) {
                    jsonPath.append("\"\"");
                }else {
                    if (value instanceof JSONArray) {
                        parshJsonArray((JSONArray) value, jsonPath);
                    }
                    if (value instanceof JSONObject) {
                        printJsonPath((JSON) value, jsonPath);
                    }
                }
                i++;
            }
            jsonPath.append("}");
        }else if (json instanceof JSONArray) {
            parshJsonArray((JSONArray)json, jsonPath);
        }
    }

    public String generateJsonKeyStructure(String jsonString) {
        StringBuffer sBuffer = new StringBuffer();
        JSON json = (JSON) JSON.parse(jsonString);
        printJsonPath(json, sBuffer);
        return sBuffer.toString();
    }
}
