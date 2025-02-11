package com.zip;

import java.io.*;
import java.util.zip.*;

public class ZipCreator {
	public static void main(String[] args) throws IOException {
		String sourceDir = "path/to/source/directory";
		String zipFile = "path/to/destination/zip/file.zip";
		try{
		FileOutputStream fos = new FileOutputStream(zipFile);
		ZipOutputStream zos = new ZipOutputStream(fos);
		File directory = new File(sourceDir);
		addFilesToZip(directory, directory.getName(), zos);
		zos.close();
		fos.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	private static void addFilesToZip(File file, String fileName, ZipOutputStream zos) throws IOException {
		if (file.isDirectory()) {
			if (fileName.endsWith("/")) {
				zos.putNextEntry(new ZipEntry(fileName));
				zos.closeEntry();
			} else {
				zos.putNextEntry(new ZipEntry(fileName + "/"));
				zos.closeEntry();
			}
			File[] children = file.listFiles();
			for (File child : children) {
				addFilesToZip(child, fileName + "/" + child.getName(), zos);
			}
		} else {
			try(FileInputStream fis = new FileInputStream(file);){
			ZipEntry zipEntry = new ZipEntry(fileName);
			zos.putNextEntry(zipEntry);
			byte[] bytes = new byte[1024];
			int length;
			while ((length = fis.read(bytes)) >= 0) {
				zos.write(bytes, 0, length);
			}
			zos.closeEntry();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}
