package com.example.carparkapp.Config;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import com.example.carparkapp.Models.HDBCarParkInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HDBCarParkInfoSerializer implements RedisSerializer<HDBCarParkInfo> {

    // Allows you to read and write Json, either to or from java objects.
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Storing in redis from HDBCarParkInfo to bytes.
    @Override
    public byte[] serialize(HDBCarParkInfo value) throws SerializationException {
        try {
            return objectMapper.writeValueAsBytes(value);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Error serializing value.", e);
        }
    }

    // Reading from redis from bytes to HDBCarParkInfo.
    @Override
    public HDBCarParkInfo deserialize(byte[] bytes) throws SerializationException {
        try {
            return objectMapper.readValue(bytes, HDBCarParkInfo.class);
        } catch (Exception e) {
            throw new SerializationException("Error desirializing value.", e);
        }
    }
    
}
