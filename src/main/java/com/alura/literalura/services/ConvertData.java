package com.alura.literalura.services;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvertData implements IGutendexConvertData {
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T getData(String json, Class<T> model) {
        try {
            return mapper.readValue(json, model);
        } catch (Exception e) {
            System.out.println("Erro ao converter JSON:");
            System.out.println(json);
            e.printStackTrace();
            return null;
        }
    }
}
