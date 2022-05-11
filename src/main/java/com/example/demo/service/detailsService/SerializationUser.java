package com.example.demo.service.detailsService;

import com.example.demo.model.Login;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;

public class SerializationUser {
    protected interface SerializationOneImplementation{
        default void serialization(File file, Login entity){
            if (entity.validfoUsername(entity.getUsername())) {

                try {
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false);
                    mapper.writeValue(new File("persons.json"), entity);

                } catch (Exception exception) {
                    exception.printStackTrace();
                }

            }
            else
            {
                System.err.println("User not found");
            }
        }
    }
}
