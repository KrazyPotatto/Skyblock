package com.github.krazypotatto.skyblock.utils.serializables;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.file.Files;

public abstract class AbstractSerializable<T extends AbstractSerializable<T>> {

    private final transient Class<T> type;
    private final transient File file;

    public AbstractSerializable(Class<T> type, File file){
        this.type = type;
        if(!file.getParentFile().exists()) file.getParentFile().mkdirs();
        this.file = file;
    }


    public void loadData() throws IOException {
        if(!file.exists()) return;
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(file.toPath());
        applyValues(gson.fromJson(reader, type));
        reader.close();
    }

    public void saveData() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Writer write = Files.newBufferedWriter(file.toPath());
        gson.toJson(this, write);
        write.close();
    }

    abstract void applyValues(T readClass);

}
