package pl.edu.pw.ee;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import java.io.*;
import java.lang.annotation.Target;


public class HuffmanTest {
    static int[] result = {0};
    static Exception exception = null;
    static String pathToDir = "./";
    static String pathToBigFile = "./10mb.txt";
    static File wrongFile = new File("./wrong_file.huf");


    @AfterClass
    public static void cleanUp() {
        deleteTestDummyFiles(pathToDir);
    }

    @Test
    public void should_CorrectlyCompressAllFilesInDirectory_WhenPathIsRight() {
        try {
            throwExceptionCompress(result, pathToDir);
        } catch (IOException ex) {
            exception = ex;
        }

        assert (exception == null);
        assert (result[0] != -1);
    }

    @Test(expected = FileNotFoundException.class)
    public void should_ThrowException_WhenPathToDirectoryIsWrong() throws IOException {
        throwExceptionCompress(result, pathToDir + "$sfsdfsdf");
    }

    @Test(expected = IOException.class)
    public void should_ThrowExceptionWhileDecoding_WhenEncodingIsWrong() throws IOException {
        BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(wrongFile));
        bufferWriter.write("Mniej mam i mniemam że nie mam ja mienia.\n" +
                "Mnie nie omamia mania mania mniemania.\n" +
                "Ja mam imię, a nie mienienie się mianem.\n" +
                "Ja manie mam na „nie”, a me imię – Niemanie.\n");
        bufferWriter.flush();
        bufferWriter.close();

        Huffman.huffman(wrongFile.getName(), false);
    }

    @Test
    public void should_CorrectlyCompressAndDecompressFile_WhenPathToFileIsRight() {
        try {
            throwExceptionCompress(result, pathToBigFile);
        } catch (IOException ex) {
            exception = ex;
        }

        assert (exception == null);
        assert (result[0] != -1);

        try {
            String newPath = pathToBigFile.replaceAll("[.][a-zA-Z0-9]+", "") + "_compressed.huf";
            throwExceptionDecompress(result, newPath);
        } catch (IOException ex) {
            exception = ex;
        }

        assert (exception == null);
        assert (result[0] != -1);
    }

    @Test
    public void should_CorrectlyCompressAndDecompressAllFiles_WhenPathToDirectoryIsRight() {
        deleteTestDummyFiles(pathToDir);

        try {
            throwExceptionCompress(result, pathToDir);
        } catch (IOException ex) {
            exception = ex;
        }

        assert (exception == null);
        assert (result[0] != -1);

        try {
            throwExceptionDecompress(result, pathToDir);
        } catch (IOException ex) {
            exception = ex;
        }

        assert (exception == null);
        assert (result[0] != -1);
    }

    private void throwExceptionCompress(int[] result, String pathToDir) throws IOException {
        result[0] = Huffman.huffman(pathToDir, true);
    }

    private void throwExceptionDecompress(int[] result, String pathToDir) throws IOException {
        result[0] = Huffman.huffman(pathToDir, false);
    }

    private static void deleteTestDummyFiles(String pathToDir) {
        File folder = new File(pathToDir);
        File[] fileList = folder.listFiles();

        if (fileList != null)
            for (File file : fileList) {
                if (file.isFile()
                        && (file.getName().lastIndexOf("_compressed") != -1
                        || file.getName().lastIndexOf("_decompressed") != -1)
                        || file.getName().lastIndexOf(".huf") != -1) {
                    file.delete();
                }
            }
        else if (folder.isFile()
                && (folder.getName().lastIndexOf("_compressed") != -1
                || folder.getName().lastIndexOf("_decompressed") != -1)
                || folder.getName().lastIndexOf(".huf") != -1) {
            folder.delete();
        }
    }
}
