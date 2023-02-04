/**
 * @Author: Shivam Patel
 * @Andrew_ID: shpatel
 * @Course: 95-771 Data Structures and Algorithms for Information Processing
 * @Assignment_Number: Project 1 - Part 3
 */

import edu.cmu.andrew.shpatel.SinglyLinkedList;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Main {

    // Function to hash strings
    public static String h(String text) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash =
                digest.digest(text.getBytes(StandardCharsets.UTF_8));
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i <= 31; i++) {
            byte b = hash[i];
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {

        int line_count = 0;
        // keeps the count of the number of lines in the file

        SinglyLinkedList lines_list = new SinglyLinkedList();
        // list to store the lines in the file (one line per node in the list)

        SinglyLinkedList initial_hash_list = new SinglyLinkedList();
        // list to store the initial hashes of the lines in the line_list list

        Scanner s = new Scanner(System.in);
        String user_file;
        // stores the location of the file that the user wishes to find the Merkle Tree for

        // Source to read files
        // https://www.w3schools.com/java/java_files_read.asp
        try {
            System.out.print("Enter location of the file of which you wish to find the root: ");
            user_file = s.nextLine(); // get the location of the file from the user

            File myObj = new File(user_file);
            // create and object for the user file

            Scanner myReader1 = new Scanner(myObj);

            // read each line from the user file and store them to the nodes in lines_list
            while (myReader1.hasNextLine()) {
                line_count++;
                String data = myReader1.nextLine();
                lines_list.addAtEndNode(data);
            }
            myReader1.close();
        } catch (FileNotFoundException e) { // Throws an exception if the file is not found (incorrect location)
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        // Print the total number of lines in the user file
        System.out.println("Total Lines = " + line_count);

        // iterate over the nodes in lines_list and create hashes for each of the
        // lines in the lines_list and store the hashes in the initial_hash_list
        lines_list.reset();
        while (lines_list.hasNext())
        {
            initial_hash_list.addAtEndNode(h((String) lines_list.next()));
        }

        // set the nextList of lines_list as initial_hash_list
        lines_list.setNextList(initial_hash_list);

        SinglyLinkedList parent_list; // iterator for forming the MerkleTree
        SinglyLinkedList child_list = initial_hash_list; // iterator for forming the MerkleTree, initially set to initial_hash_list
        // child_list points to the current level of the Merkle Tree
        // parent_list points to the next level (higher level) of the Merkle Tree

        boolean first_run = true;
        boolean merkleRootFound = false;

        // until the MerkleRoot is not found
        while (!merkleRootFound) {

            // create a new parent_list for the new level in MerkleTree
            parent_list = new SinglyLinkedList();

            // If it is not the initial run and if only one node in the level, Merkle Root found
            if (child_list.countNodes() == 1 && !first_run) {
                merkleRootFound = true;
                System.out.println("merkleRootFound = " + child_list.getLast());
            }
            else {
                // If we have odd number of nodes on one level, duplicate the last node and add it to the level
                if (child_list.countNodes() % 2 != 0) {
                    child_list.addAtEndNode(child_list.getLast());
                }

                // form the hashes of the next level of the Merkle Tree
                child_list.reset();
                while (child_list.hasNext())
                {
                    String s1 = (String) child_list.next();
                    String s2 = (String) child_list.next();
                    String s3 = s1 + s2; // concatenate the hashes
                    String hash = h(s3); // find hash of the concatenated hashes

                    parent_list.addAtEndNode(hash); // add the new hash to the parent_list
                }

                // set parent_list as the nextList of child_list
                child_list.setNextList(parent_list);

                // for the next iteration, set child_list to be the current parent_list
                child_list = parent_list;

                // flag to state that the first run is complete
                first_run = false;
            }
        }

        /* Results:
        1. smallFile.txt
           Merkle Root: A4E10610B30C40CA608058C521AD3D9EEC42C1892688903984580C56D3CF8A7D
        2. CrimeLatLonXY.csv
           Merkle Root: A5A74A770E0C3922362202DAD62A97655F8652064CCCBE7D3EA2B588C7E07B58
        3. CrimeLatLonXY1990_Size2.csv
           Merkle Root: DDD49991D04273A7300EF24CFAD21E2706C145001483D161D53937D90F76C001
        4. CrimeLatLonXY1990_Size3.csv
           Merkle Root: 313A2AD830ED85B5203C8C2A9895ADFA521CD4ABB74B83C25DA2C6A47AE08818

        Hence, the file CrimeLatLonXY.csv has the required Merkle Root, i.e.,
        A5A74A770E0C3922362202DAD62A97655F8652064CCCBE7D3EA2B588C7E07B58
         */
    }
}