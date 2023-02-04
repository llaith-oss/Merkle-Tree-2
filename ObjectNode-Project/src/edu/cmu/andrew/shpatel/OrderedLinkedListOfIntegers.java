/**
 * @Author: Shivam Patel
 * @Andrew_ID: shpatel
 * @Course: 95-771 Data Structures and Algorithms for Information Processing
 * @Assignment_Number: Project 1 - Part 1
 */

package edu.cmu.andrew.shpatel;
import edu.colorado.nodes.ObjectNode;
import java.util.Random;

public class OrderedLinkedListOfIntegers {
    private ObjectNode head;
    private ObjectNode tail;
    private ObjectNode currentNode;
    private ObjectNode previousNode;

    public OrderedLinkedListOfIntegers()
    {
        head = null;
        tail = null;
        currentNode = null;
        previousNode = null;
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
     * Adds int c at the appropriate position in an OrderedLinkedListOfIntegers
     * such that after adding c, the OrderedLinkedListOfIntegers is still sorted
     * in ascending order
     * @precondition
     *   the list who called this function is in sorted order, and it is,
     *   not circular (its last node points to null)
     * @param c
     *   integer to be added
     * @postcondition
     *   the main list who called this function is still sorted in ascending order
     */
    // Big-θ Runtime = θ(N)
    public void sortedAdd(int c)
    {
        ObjectNode newNode = new ObjectNode(c, null);
        if (head == null && tail == null)
        {
            head = newNode;
            tail = newNode;
        }
        else
        {
            for (currentNode = head; previousNode != tail; currentNode = currentNode.getLink())
            {
                if (c <= (Integer) currentNode.getData() && currentNode == head)
                // Adding at first node
                {
                    newNode.setLink(currentNode);
                    head = newNode;
                    return;
                }
                else if (c <= (Integer) currentNode.getData() && currentNode != head)
                // Adding in middle node
                {
                    newNode.setLink(currentNode);
                    previousNode.setLink(newNode);
                    return;
                }
                previousNode = currentNode;
            }
            previousNode.setLink(newNode);
            tail = newNode;
        }
    }

    /**
     * Merges two OrderedLinkedListOfIntegers to form another OrderedLinkedListOfIntegers
     * @precondition
     *   OrderedLinkedListOfIntegers l1 and OrderedLinkedListOfIntegers l2 are
     *   in increasingly sorted order (if they are not empty).
     *   l1 and l2 are not circular linked lists
     * @postcondition
     *   The merged OrderedLinkedListOfIntegers is in increasingly sorted order
     **/
    // Big-θ Runtime = θ(m + n)
    public static OrderedLinkedListOfIntegers merge(OrderedLinkedListOfIntegers l1, OrderedLinkedListOfIntegers l2)
    {
        OrderedLinkedListOfIntegers mergedList = new OrderedLinkedListOfIntegers();

        // dummy
        mergedList.sortedAdd(-9999);

        while (true)
        {
            if (l1.head == null)
            {
                mergedList.tail.setLink(l2.head);
                mergedList.tail = l2.tail;
                break;
            }
            if (l2.head == null)
            {
                mergedList.tail.setLink(l1.head);
                mergedList.tail = l1.tail;
                break;
            }

            if ((Integer) l1.head.getData() <= (Integer) l2.head.getData())
            {
                mergedList.tail.setLink(l1.head);
                mergedList.tail = l1.head;
                l1.head = l1.head.getLink();
            }
            else
            {
                mergedList.tail.setLink(l2.head);
                mergedList.tail = l2.head;
                l2.head = l2.head.getLink();
            }
        }

        mergedList.head = mergedList.head.getLink();
        return mergedList;
    }

    public static void main(String[] args) {
        OrderedLinkedListOfIntegers o1 = new OrderedLinkedListOfIntegers();

        // Source for random number generation
        // https://www.educative.io/answers/how-to-generate-random-numbers-in-java
        Random rand = new Random();
        int random_int_upper_bound = 9999;
        // Note: In this example, I am adding only random integers to the list with an upper bound of 9999.
        // However, the code will work for any values.

        for (int i = 1; i <= 20; i++) {
            int int_random = rand.nextInt(random_int_upper_bound);
            o1.sortedAdd(int_random);
        }
        System.out.println("Created first OrderedLinkedListOfIntegers o1 with 20 random values.");

        OrderedLinkedListOfIntegers o2 = new OrderedLinkedListOfIntegers();

        for (int i = 1; i <= 20; i++) {
            int int_random = rand.nextInt(random_int_upper_bound);
            o2.sortedAdd(int_random);
        }
        System.out.println("Created second OrderedLinkedListOfIntegers o2 with 20 random values.");

        OrderedLinkedListOfIntegers m = merge(o1, o2);
        System.out.println("Merged o1 and o2 using merge(o1, o2).");

        System.out.println("\nTesting reset(), hasNext() and next() functions:");
        m.reset();
        while (m.hasNext())
        {
            System.out.println(m.next());
        }
    }
}
