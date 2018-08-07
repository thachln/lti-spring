package mks.ownbank.lti;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonReader {

    public static <T> T readJsonFromUrl(String url, Class<T> claz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
//        mapper.configure(DeserializationConfigFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        InputStream is = new URL(url).openStream();
        try {
            return mapper.readValue(is, claz);
        } finally {
            is.close();
        }
    }

}