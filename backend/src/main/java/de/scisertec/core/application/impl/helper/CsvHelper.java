package de.scisertec.core.application.impl.helper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CsvHelper {

    public static byte[] convertToByteArrayWithUTF8(String csv) {
        try {
            byte[] csvAsByteArray;
            csvAsByteArray = csv.getBytes("UTF-8");
            ByteArrayOutputStream fos = new ByteArrayOutputStream();
            fos.write(239);
            fos.write(187);
            fos.write(191);
            fos.write(csvAsByteArray);
            fos.close();
            return fos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
