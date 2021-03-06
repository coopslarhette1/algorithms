package huffman;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/*
 * Author: Cooper LaRhette
 */

/**
 * Huffman instances provide reusable Huffman Encoding Maps for
 * compressing and decompressing text corpi with comparable
 * distributions of characters.
 */
public class Huffman {

    // -----------------------------------------------
    // Construction
    // -----------------------------------------------

    private HuffNode trieRoot;
    protected Map<Character, String> encodingMap;

    /**
     * Creates the Huffman Trie and Encoding Map using the character
     * distributions in the given text corpus
     *
     * @param corpus A String representing a message / document corpus
     *               with distributions over characters that are implicitly used
     *               throughout the methods that follow. Note: this corpus ONLY
     *               establishes the Encoding Map; later compressed corpi may
     *               differ.
     */
    Huffman(String corpus) {
        HashMap<Character, Integer> charCounts = new HashMap<>();
        for (int i = 0; i < corpus.length(); i++) {
            Character currChar = corpus.charAt(i);
            charCounts.merge(currChar, 1, (a, b) -> a + b);
        }
        PriorityQueue<HuffNode> trieQ = new PriorityQueue<>(charCounts.size(), HuffNode::compareTo);
        for (Map.Entry<Character, Integer> entry : charCounts.entrySet()) {
            trieQ.add(new HuffNode(entry.getKey(), entry.getValue()));
        }
        while (trieQ.size() > 1) {
            HuffNode left = trieQ.poll();
            HuffNode right = trieQ.poll();
            HuffNode newNode = new HuffNode('\0', left.count + right.count);
            newNode.left = left;
            newNode.right = right;
            trieQ.add(newNode);
        }
        trieRoot = trieQ.poll();
        encodingMap = new HashMap<>(charCounts.size());
        encodeChar(trieRoot, "");
    }

    /**
     * Fills encodingMap used to compress by traversing the Huffman Trie.
     *
     * @param root      Root of the trie.
     * @param bitString bitString that will start out as an empty String and be used for each binary encoding for each character.
     */
    private void encodeChar(HuffNode root, String bitString) {
        if (root.left == null && root.right == null) {
            encodingMap.put(root.character, bitString);
        } else {
            encodeChar(root.left, bitString + "0");
            encodeChar(root.right, bitString + "1");
        }
    }

    // -----------------------------------------------
    // Compression
    // -----------------------------------------------

    /**
     * Compresses the given String message / text corpus into its Huffman coded
     * bitstring, as represented by an array of bytes. Uses the encodingMap
     * field generated during construction for this purpose.
     *
     * @param message String representing the corpus to compress.
     * @return {@code byte[]} representing the compressed corpus with the
     * Huffman coded bytecode. Formatted as 3 components: (1) the
     * first byte contains the number of characters in the message,
     * (2) the bitstring containing the message itself, (3) possible
     * 0-padding on the final byte.
     */
    public byte[] compress(String message) {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        StringBuilder tempBitString = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            tempBitString.append(encodingMap.get(message.charAt(i)));
        }
        result.write(message.length());
        if (tempBitString.length() % 8 != 0) {
            for (int i = 0; i < tempBitString.length() % 8; i++) {
                tempBitString.append("0");
            }
        }
        for (int i = 0; i < tempBitString.length(); i += 8) {
            result.write(Integer.parseInt(tempBitString.substring(i, i + 8), 2));
        }
        return result.toByteArray();
    }


    // -----------------------------------------------
    // Decompression
    // -----------------------------------------------

    /**
     * Decompresses the given compressed array of bytes into their original,
     * String representation. Uses the trieRoot field (the Huffman Trie) that
     * generated the compressed message during decoding.
     *
     * @param compressedMsg {@code byte[]} representing the compressed corpus with the
     *                      Huffman coded bytecode. Formatted as 3 components: (1) the
     *                      first byte contains the number of characters in the message,
     *                      (2) the bitstring containing the message itself, (3) possible
     *                      0-padding on the final byte.
     * @return Decompressed String representation of the compressed bytecode message.
     */
    public String decompress(byte[] compressedMsg) {
        String bitString = buildBitString(compressedMsg);
        StringBuilder result = new StringBuilder();
        HuffNode currNode = trieRoot;
        for (int i = 0; i < bitString.length() && result.length() < compressedMsg[0]; i++) {
            if (currNode.left == null && currNode.right == null) {
                result.append(currNode.character);
                currNode = trieRoot;
            }
            if (bitString.charAt(i) == '0') {
                currNode = currNode.left;
            } else {
                currNode = currNode.right;
            }
        }
        return result.toString();
    }

    /**
     * Builds bitString from compressed byte array.
     *
     * @param compressedMsg Byte array that is built through compressing the original message.
     * @return The bitString used for traversing the trie for purpose of decompression.
     */
    private String buildBitString(byte[] compressedMsg) {
        StringBuilder tempByteCode = new StringBuilder();
        for (int i = 1; i < compressedMsg.length; i++) {
            tempByteCode.append(String.format("%8s", Integer.toBinaryString(compressedMsg[i] & 0xFF)).replace(' ', '0'));
        }
        return tempByteCode.toString();
    }

    // -----------------------------------------------
    // Huffman Trie
    // -----------------------------------------------

    /**
     * Huffman Trie Node class used in construction of the Huffman Trie.
     * Each node is a binary (having at most a left and right child), contains
     * a character field that it represents (in the case of a leaf, otherwise
     * the null character \0), and a count field that holds the number of times
     * the node's character (or those in its subtrees) appear in the corpus.
     */
    private static class HuffNode implements Comparable<HuffNode> {

        HuffNode left, right;
        char character;
        int count;

        HuffNode(char character, int count) {
            this.count = count;
            this.character = character;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        public int compareTo(HuffNode other) {
            return this.count - other.count;
        }

    }

}
