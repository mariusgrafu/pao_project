package src.blog.services;

import java.io.FileWriter;
import java.util.Date;

public class AuditService {
    private static AuditService instance = null;
    private final String path;

    private AuditService(String path) {
        this.path = path;
    }

    public static AuditService getInstance(String path) {
        if(instance == null) {
            instance = new AuditService(path);
        }

        return instance;
    }

    public void log(String message) {
        try {
            FileWriter fw = new FileWriter(this.path, true);
            fw.write(message + "," + new Date() + "," + Thread.currentThread().getName() + "\r\n");
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
