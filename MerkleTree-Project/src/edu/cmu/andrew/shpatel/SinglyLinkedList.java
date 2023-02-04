/**
 * @Author: Shivam Patel
 * @Andrew_ID: shpatel
 * @Course: 95-771 Data Structures and Algorithms for Information Processing
 * @Assignment_Number: Project 1 - Part 3
 */

package edu.cmu.andrew.shpatel;
import edu.colorado.nodes.ObjectNode;

public class SinglyLinkedList {

    private ObjectNode head;
    private ObjectNode tail;
    private ObjectNode currentNode;
    private int countNodes = 0;
    private SinglyLinkedList nextList;
    // stores the location of the next SinglyLinkedList for storing the values in next level of the Merkle Tree

    public SinglyLinkedList()
    {
        head = null;
        tail = null;
        currentNode = null;
    }

    /**
     * Add a node containing the Object c to the head of the linked list.
     * @param c
     *   object to be added to the head of the linked list
     * @postcondition
     *   Object c is added to the front of the list
     */
    // Big-θ Runtime = θ(1)
    public void addAtFrontNode(java.lang.Object c)
    {
        ObjectNode newNode = new ObjectNode(c, head);
        if (head == null && tail == null)
        {
            head = newNode;
            tail = newNode;
        }
        else
        {
            head = newNode;
        }
        countNodes = countNodes();
    }

    /**
     * Add a node containing the Object c to the end of the linked list.
     * @param c
     *   object to be added to the end of the linked list
     * @postcondition
     *   Object c is added to the end of the list
     */
    // Big-θ Runtime = θ(1)
    public void addAtEndNode(java.lang.Object c)
    {
        ObjectNode newNode = new ObjectNode(c, null);
        if (head == null && tail == null)
        {
            head = newNode;
        }
        else
        {
            tail.setLink(newNode);
        }
        tail = newNode;
        countNodes = countNodes();
    }

    /**
     * Counts the number of nodes in the list
     * @return
     *   count of the number of nodes in the list
     */
    // Big-θ Runtime = θ(N)
    public int countNodes()
    {
        int nodeCount = 0;

        currentNode = head;

        while (currentNode != tail)
        {
            nodeCount++;
            currentNode = currentNode.getLink();
        }
        nodeCount++;
        return nodeCount;
    }

    /**
     * Returns the data in the tail of the list
     * @return
     *   data in the tail of the list
     */
    // Big-θ Runtime = θ(1)
    public java.lang.Object getLast()
    {
        if (head == null && tail == null)
        {
            return null;
        }
        return tail.getData();
    }

    /**
     * Returns a reference (0 based) to the object with list index i.
     * @precondition
     *   The list is not empty and has the length of at least i+1
     * @return
     *   reference (0 based) to the object with list index i
     **/
    // Big-θ Runtime = θ(N)
    public java.lang.Object getObjectAt(int i)
    {
        currentNode = head;

        for (int j = 0; j < i; j++)
        {
            currentNode = currentNode.getLink();
        }

        return currentNode.getData();
    }

    /**
     * reset the iterator to the beginning of the list That is, set a reference to the head of the list.
     */
    // Big-θ Runtime = θ(1)
    public void reset()
    {
        currentNode = head;
    }

    /**
     * return the Object pointed to by the iterator and increment the iterator to the next node in the list.
     * @precondition
     *   head is not empty
     * @return
     *   Object pointed to by the iterator
     */
    // Big-θ Runtime = θ(1)
    public java.lang.Object next()
    {
        Object nodeData = currentNode.getData();
        if (currentNode.getLink() != null) {
            currentNode = currentNode.getLink();
        }
        else {
            currentNode = null;
        }
        return nodeData;
    }

    /**
     * to check if there is another node in the iterator
     * @return
     *   true if the iterator is not null, else false
     */
    // Big-θ Runtime = θ(1)
    public boolean hasNext()
    {
        if (currentNode != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Convert the elements in the list to a combined string
     * @return
     *   a string with the combined elements of the list
     **/
    // Big-θ Runtime = θ(N)
    public java.lang.String toString()
    {
        String list_as_string = "";

        for (currentNode = head; currentNode != tail; currentNode = currentNode.getLink())
        {
            list_as_string = list_as_string + currentNode.getData();
        }
        list_as_string = list_as_string + currentNode.getData();

        return list_as_string;
    }

    /**
     * Get the value of countNodes
     * @return
     *   Counts the number of nodes in the list
     */
    // Big-θ Runtime = θ(1)
    public int getCountNodes() {
        return countNodes;
    }

    /**
     * Sets the nextList of a SinglyLinkedList to point to the next level in the MerkleTree
     * @param list
     *   list to store the next level in the MerkleTree
     */
    public void setNextList(SinglyLinkedList list) {
        nextList = list;
    }
}
