package com.mooracle.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Instant;

/** Entry 24: Creating Service TimestampDeserializer.java class
 *  1.  This class is intended to Deserialize a Timestamp for time function data
 *  2.  This function is needed for the Instant timestamp in the dto package
 *  3.  Just extends this class with JsonDeserializer and implements the @Override method (there is only 1 method)
 *  4.  More on the JsonDeserializer there's a link in the README
 *
 * */
public class TimestampDeserializer extends JsonDeserializer<Instant> {
    @Override
    public Instant deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        Long timestamp = p.getLongValue(); // <-- p here is JsonParser
        return Instant.ofEpochSecond(timestamp); // return the timestamp as seconds epoch (UNIX) time
    }
}
