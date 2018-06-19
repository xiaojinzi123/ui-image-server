package com.xiaojinzi.image.util;

import com.xiaojinzi.image.init.ProjectUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;

public class ImageFileManager {

    private static ImageFileManager instance = new ImageFileManager();

    private static File imageFolder;

    public static ImageFileManager getInstance() {
        return instance;
    }

    private ImageFileManager() {
        File rootFilder = ProjectUtil.rootFilder;
        imageFolder = new File(rootFilder, "actionImageCache");
        imageFolder.mkdirs();
    }

    @Nullable
    public File saveImage(@NotNull CommonsMultipartFile multipartFile) throws IOException {

        if (!imageFolder.exists() || multipartFile == null) {
            return null;
        }

        String fileName = multipartFile.getOriginalFilename();

        File resultFile = new File(imageFolder, fileName);

        InputStream is = multipartFile.getInputStream();

        FileOutputStream out = new FileOutputStream(resultFile);

        byte[] bf = new byte[1024];
        int len = -1;

        while ((len = is.read(bf)) != -1) {

            out.write(bf,0,len);

        }

        is.close();
        out.close();

        return resultFile;

    }

}
