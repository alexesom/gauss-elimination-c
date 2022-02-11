package pl.edu.pw.ee;

import pl.edu.pw.ee.TreeMap.TreeMap;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Huffman {
    public static int huffman(String pathToRootDir, boolean compress) throws IOException {
        int result = -1;

        if (!Files.exists(Path.of(pathToRootDir)))
            throw new FileNotFoundException();

        File folder = new File(pathToRootDir);
        File[] fileList = folder.listFiles();

        if (compress) {
            if (fileList != null)
                for (File file : fileList) {
                    if (file.isFile()
                            && file.getName().lastIndexOf(".huf") == -1) {
                        result = huffmanCompress(file);
                    }
                }
            else if (folder.isFile()
                    && folder.getName().lastIndexOf(".huf") == -1) {
                result = huffmanCompress(folder);
            }
        } else {

            if (fileList != null)
                for (File file : fileList) {
                    if (file.isFile() && file.getName().lastIndexOf(".huf") != -1) {
                        result = huffmanDecompress(file);
                    }
                }
            else if (folder.isFile() && folder.getName().lastIndexOf(".huf") != -1) {
                result = huffmanDecompress(folder);
            }
        }

        return result;
    }

    private static int huffmanCompress(File inputFile) throws IOException {
        String textToCompress = new String(Files.readAllBytes(Paths.get(inputFile.getName())));
        List<Character> keyList = new ArrayList<>();
        TreeMap<Character, Integer> frequencies = countFrequency(textToCompress, keyList);
        ArrayList<TreeNode> treeListNodes = getTreeNodeArray(frequencies, keyList);
        TreeNode fullTreeRoot = getHuffmanTree(treeListNodes);

        TreeMap<Character, String> codesMap = new TreeMap<>();
        for (Character c : keyList) {
            codesMap.setValue(c, fullTreeRoot.getCodeForCharacter(c, ""));
        }

        StringBuilder encoded = new StringBuilder();
        if (keyList.size() == 1) {
            encoded.append("0".repeat(textToCompress.length()));
        } else {
            for (int i = 0; i < textToCompress.length(); i++) {
                encoded.append(codesMap.getValue(textToCompress.charAt(i)));
            }
        }

        File outputFile = new File(inputFile.getName().replaceAll("[.][a-zA-Z0-9]+", "") + "_compressed.huf");
        saveToFileHuffmanEncoded(outputFile, frequencies, keyList, encoded.toString());
        return encoded.length();
    }

    private static int huffmanDecompress(File file) throws IOException {
        TreeMap<Character, Integer> frequencies = new TreeMap<>();
        List<Character> keySet = new ArrayList<>();
        StringBuilder bits = new StringBuilder();

        loadFromFileHuffmanEncoded(file, frequencies, keySet, bits);

        ArrayList<TreeNode> treeListNodes = getTreeNodeArray(frequencies, keySet);
        TreeNode fullTreeRoot = getHuffmanTree(treeListNodes);
        String decodedString = huffmanDecode(bits, fullTreeRoot);

        File outputFile = new File(file.getName().replace("compressed.huf", "decompressed.txt"));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFile));
        bufferedWriter.write(decodedString);

        bufferedWriter.flush();
        bufferedWriter.close();
        return decodedString.length();
    }

    private static String huffmanDecode(StringBuilder encoded, TreeNode tree) {
        StringBuilder decode = new StringBuilder();
        TreeNode node = tree;

        if (tree.left == null && tree.right == null) {
            decode.append(String.valueOf(node.getCharacter()).repeat(Math.max(0, encoded.length())));
        } else {
            for (int i = 0; i < encoded.length(); i++) {
                node = encoded.charAt(i) == '0' ? node.left : node.right;

                if (node.getCharacter() != null) {
                    decode.append(node.getCharacter());
                    node = tree;
                }
            }
        }
        return decode.toString();
    }

    private static TreeMap<Character, Integer> countFrequency(String text, List<Character> keyList) {
        TreeMap<Character, Integer> treeMap = new TreeMap<>();

        for (int i = 0; i < text.length(); i++) {
            Character c = text.charAt(i);
            Integer count = treeMap.getValue(c);
            if (count != null) {
                treeMap.setValue(c, count + 1);
            } else {
                treeMap.setValue(c, 1);
                keyList.add(c);
            }
        }
        return treeMap;
    }

    private static ArrayList<TreeNode> getTreeNodeArray(TreeMap<Character, Integer> frequencies, List<Character> keyList) {
        ArrayList<TreeNode> treeListNodes = new ArrayList<>();
        for (Character c : keyList) {
            treeListNodes.add(new TreeNode(c, frequencies.getValue(c)));
        }
        return treeListNodes;
    }

    private static TreeNode getHuffmanTree(ArrayList<TreeNode> treeNodes) {
        if (treeNodes.size() == 0) throw new IllegalArgumentException();

        while (treeNodes.size() > 1) {
            Collections.sort(treeNodes);
            TreeNode left = treeNodes.remove(treeNodes.size() - 1);
            TreeNode right = treeNodes.remove(treeNodes.size() - 1);

            TreeNode parent = new TreeNode(null, right.weight + left.weight, left, right);
            treeNodes.add(parent);
        }
        return treeNodes.get(0);
    }

    private static void saveToFileHuffmanEncoded(File output, TreeMap<Character, Integer> frequencies, List<Character> keySet, String bits)
            throws IOException {
        DataOutputStream os = new DataOutputStream(new FileOutputStream(output));
        os.writeInt(keySet.size());
        for (Character character : keySet) {
            os.writeChar(character);
            os.writeInt(frequencies.getValue(character));
        }
        int compressedBitsSize = bits.length();
        BitArray bitArray = new BitArray(compressedBitsSize);
        for (int i = 0; i < bits.length(); i++) {
            bitArray.set(i, bits.charAt(i) == '1' ? 1 : 0);
        }

        os.writeInt(compressedBitsSize);
        os.write(bitArray.bytes, 0, bitArray.getSizeInBytes());
        os.flush();
        os.close();
    }

    private static void loadFromFileHuffmanEncoded(File input, TreeMap<Character, Integer> frequencies, List<Character> keySet, StringBuilder bits)
            throws IOException {
        DataInputStream os = new DataInputStream(new FileInputStream(input));

        int frequencyTableSize = os.readInt();
        for (int i = 0; i < frequencyTableSize; i++) {
            Character tempCharacter = os.readChar();
            keySet.add(tempCharacter);
            frequencies.setValue(tempCharacter, os.readInt());
        }

        int dataSizeBits = os.readInt();
        BitArray bitArray = new BitArray(dataSizeBits);
        os.read(bitArray.bytes, 0, bitArray.getSizeInBytes());
        os.close();

        for (int i = 0; i < bitArray.size; i++) {
            bits.append(bitArray.get(i) == 1 ? "1" : "0");
        }
    }
}
