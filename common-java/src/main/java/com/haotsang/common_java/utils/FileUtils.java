package com.haotsang.common_java.utils;

import android.content.Context;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.channels.FileChannel;

public class FileUtils {

    public static void saveSerializable(Object objectToSave, File outFile) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(outFile);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(objectToSave);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object readSerializable(File inFile) {
        try (FileInputStream fileInputStream = new FileInputStream(inFile);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static byte[] readByte(InputStream is) throws IOException {
        byte buffer[] = new byte[1024 * 8];
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        int n;
        while ((n = is.read(buffer)) >= 0) {
            bout.write(buffer, 0, n);
        }
        return bout.toByteArray();
    }

    public static boolean copyFile(File in, File out) {
        try (FileChannel inChannel = new FileInputStream(in).getChannel();
             FileChannel outChannel = new FileOutputStream(out).getChannel()) {
            inChannel.transferTo(0, inChannel.size(), outChannel);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
