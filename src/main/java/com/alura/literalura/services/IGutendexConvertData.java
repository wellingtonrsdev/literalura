package com.alura.literalura.services;

public interface IGutendexConvertData {

    <T> T  getData(String json, Class<T> model);

}
