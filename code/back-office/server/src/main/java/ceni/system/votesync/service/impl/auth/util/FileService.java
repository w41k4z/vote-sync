package ceni.system.votesync.service.impl.auth.util;

import java.io.File;
import java.util.List;

public class FileService {
    public static final List<String> SUPPORTED_IMAGE_EXTENSION = List.of("jpeg", "jpg", "png", "webp");

    public static boolean isImage(File file) {
        String extension = getFileExtension(file);
        return SUPPORTED_IMAGE_EXTENSION.contains(extension);
    }

    private static String getFileExtension(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return "";
        }
        return name.substring(lastIndexOf + 1);
    }
}
