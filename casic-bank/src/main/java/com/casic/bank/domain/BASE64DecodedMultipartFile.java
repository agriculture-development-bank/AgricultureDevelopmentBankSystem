package com.casic.bank.domain;

import org.springframework.core.io.Resource;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

/**
 * Created by PC-015 on 2019/5/24.
 */
public class BASE64DecodedMultipartFile implements MultipartFile{

    private byte[] imgContent;
    private String header;
    private String fileName;

    public BASE64DecodedMultipartFile() {
    }

    public BASE64DecodedMultipartFile(byte[] imgContent, String header, String fileName) {
        this.imgContent = imgContent;
        this.header = header;
        this.fileName = fileName;
    }

    @Override
    public String getName() {
        return fileName;
    }

    @Nullable
    @Override
    public String getOriginalFilename() {
        return fileName;
    }

    @Nullable
    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public long getSize() {
        return 0;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return new byte[0];
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return null;
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {

    }

    @Override
    public Resource getResource() {
        return null;
    }

    @Override
    public void transferTo(Path dest) throws IOException, IllegalStateException {

    }
}
