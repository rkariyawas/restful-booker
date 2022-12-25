package com.payconiq.services.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.payconiq.pojo.config.CommonKeys;
import com.payconiq.pojo.config.Config;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class CommonUtils {

    private static Logger logger = LoggerFactory.getLogger(CommonUtils.class);
    private ObjectMapper objectMapper = getDefaultObjectMapper();
    public static Config config;

    /**
     * get the encoded password and decode it
     * then returns the decoded password
     * @param password
     * @return decoded password
     */
    public static String passwordDecode(String password){
        int i=0;
        while (i<2) {
            // Decode data on other side, by processing encoded data
            byte[] valueDecoded = Base64.decodeBase64(password);
            password = new String(valueDecoded);
            i++;
        }
        return password;
    }

    private ObjectMapper getDefaultObjectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        return objectMapper;
    }

    private JsonNode parse(String src) throws IOException {
        return objectMapper.readTree(new File(src));
    }

    public static JsonNode objectToJsonNode(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.valueToTree(obj);
    }

    private <A> A fromJson(JsonNode node, Class<A> className) throws JsonProcessingException {
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        return objectMapper.treeToValue(node, className);
    }

    public Object fileReader(String src, String clsName) {
        try {
            JsonNode jsonNode = parse( src);
            return fromJson(jsonNode, Class.forName(clsName));

        }catch (Exception e){
            logger.error("File path :"+src+ " with class :"+clsName, e.getMessage(), e.getStackTrace());
            return null;
        }
    }

    public static String getStringJson (Object obj) {
        try {
            ObjectWriter objectW = new ObjectMapper().writer().withDefaultPrettyPrinter();
            return objectW.writeValueAsString(obj);

        } catch (JsonProcessingException e){
            logger.error("Error while write String to Json", e.getMessage(), e.getStackTrace());
        }
      return null;
    }

    public static JsonNode getJsonFromString (String strJson){
        try {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(strJson);
    } catch (JsonProcessingException e){
        logger.error("Error while write Json to String", e.getMessage(), e.getStackTrace());
    }
        return null;
    }

    /**
     * Initialize configurations from config json file
     */
    public static void setConfigJson(){
        CommonUtils jsonReader = new CommonUtils();
        // configuration JSON file location
        String src = CommonKeys.CONFIG_FILE;
        // Assign configuration JSON to ConfigJson Class
        config =  (Config) jsonReader.fileReader(src, Config.class.getName());
        System.out.println(config.getServiceUrl());
    }

    /**
     * TODO since date validation is must as per the api spec
     * can be use this method for
     * validate date format
     * @param date string date
     * @return
     */
    public static String dateFormatVerify(String date){
       try {
           SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
           Date convertedCurrentDate = sdf.parse(date);
           return sdf.format(convertedCurrentDate );
       }catch (ParseException e){
           logger.error("Error while Date Format verification", e.getMessage(), e.getStackTrace());
       }
        return null;
    }

    /**
     * set service request headers
     * @param token
     * @return header map
     */
    public Map<String, String> serviceHeader(String token){
        Map<String, String> headers = new TreeMap<>();
        headers.put("Content-Type","application/json");
        if(token != null){
            headers.put("Cookie", token);
        }
        return headers;
    }
}
