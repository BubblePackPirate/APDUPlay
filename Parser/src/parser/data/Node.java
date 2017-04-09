/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser.data;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Andrej
 */
public class Node {
    private static int nextIdentifier = 0;
    
    private byte[] data;
    private Node parent;
    private final Map<Byte, Node> childNodes;
    private int count;
    
    public final int identifier;
    
    /**
     * Creates new instance of Node with specified byte stream
     * 
     * @param data byte stream
     */
    public Node(byte[] data) {
        this.data = data;
        childNodes = new HashMap<>();
        identifier = nextIdentifier++;
        count = 1;
    }
    
    /**
     * Increments count of the occurence
     */
    public void incrementCount() {
        count++;
    }
    
    /**
     * Sets occurence count
     * 
     * @param count occurence count
     */
    public void setCount(int count) {
        this.count = count;
    }
    
    /**
     * Gets ocurrence count
     * @return occurence count
     */
    public int getCount() {
        return count;
    }
    
    /**
     * Sets byte stream
     * 
     * @param data byte stream
     */
    public void setData(byte[] data) {
        this.data = data;
    }
    
    /**
     * Gets byte stream
     * 
     * @return byte stream
     */
    public byte[] getData() {
        return data;
    }
    
    /**
     * Splits node into 2 nodes from specified data index
     * 
     * @param index index to divide data stream from
     */
    public void divide(int index) {
        // index cannot be higher than the length of data array
        // every node need to have value
        if (index >= data.length && index < 1) {
            throw new IllegalArgumentException("Index out of range of data array");
        }
        
        byte[] subArray = Arrays.copyOfRange(data, 0, index);
        Node node = new Node(subArray);
        
        parent.addChild(node);
        data = Arrays.copyOfRange(data, index, data.length);
        node.addChild(this);
    }
    
    /**
     * Adds child node to this node
     * 
     * @param node child node to be added
     */
    public void addChild(Node node) {
        node.parent = this;
        childNodes.put(node.getData()[0], node);
    }
    
    /**
     * Adds child nodes to this node
     * 
     * @param nodes child nodes to be added
     */
    public void addChildren(Collection<Node> nodes) {
        nodes.forEach((node) -> {
            addChild(node);
        });
    }
    
    /**
     * Sets child nodes
     * 
     * @param nodes childs to be set
     */
    public void setChildren(Collection<Node> nodes) {
        childNodes.clear();
        addChildren(nodes);
    }
    
    /**
     * Informs about existence of any child nodes
     * 
     * @return true if any child node exists, false otherwise
     */
    public boolean hasChildNodes() {
        return !childNodes.isEmpty();
    }
    
    /**
     * Gets child node based on first byte of childs node data stream
     * 
     * @param item  first byte of data stream
     * @return      child node if any, otherwise null
     */
    public Node findChildNode(byte item) {
        return childNodes.get(item);
    }
    
    /**
     * Gets all child nodes
     * 
     * @return child nodes
     */
    public Collection<Node> getChildNodes() {
        return childNodes.values();
    }
    
    /**
     * Gets parent node
     * 
     * @return parent node
     */
    public Node getParentNode() {
        return parent;
    }
}
