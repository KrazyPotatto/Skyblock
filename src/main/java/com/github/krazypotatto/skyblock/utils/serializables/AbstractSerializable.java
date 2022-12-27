package com.github.krazypotatto.skyblock.utils.serializables;

import com.github.krazypotatto.skyblock.Skyblock;
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
        Reader reader = Files.newBufferedReader(file.toPath());
        applyValues(Skyblock.gsonGlobal().fromJson(reader, type));
        reader.close();
    }

    public void saveData() throws IOException {
        Writer write = Files.newBufferedWriter(file.toPath());
        Skyblock.gsonGlobal().toJson(this, write);
        write.close();
    }

    abstract void applyValues(T readClass);

}
