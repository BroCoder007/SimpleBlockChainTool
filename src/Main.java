import java.util.Scanner;

/**
 * Solution for Simple Blockchain
 *
 * --- LOGIC AND DESIGN ---
 * This program simulates a simplified blockchain using a custom-built Doubly
 * Linked List (DLL) as its core data structure. This version operates without a
 * `latestBlock` (tail) pointer, relying solely on the `genesis` (head) pointer
 * to manage the chain.
 *
 * The structure is divided into:
 * 1.  BlockNode: The node class for the DLL.
 * 2.  BlockchainLedger: The DLL implementation managing the chain with only a head pointer.
 * 3.  Main: The main class that drives the program, as expected by the autograder.
 *
 * --- TIME COMPLEXITY ---
 * - Add Block ('A'): O(n)
 * - Delete/Find Block ('D'/'F'): O(n)
 * - Print ('P'): O(n)
 * - Reverse Print ('R'): O(n)
 * - Sort by Type ('S'): O(n)
 */
public class Main {

    /**
     * Represents one block in the chain. This is our "Node".
     */
    static class BlockNode {
        String blockId;
        String blockData;
        String blockType;
        BlockNode prev;
        BlockNode next;

        BlockNode(String id, String data, String type) {
            this.blockId = id;
            this.blockData = data;
            this.blockType = type;
        }
    }

    /**
     * The Doubly Linked List ADT for managing the blockchain using only a head pointer.
     */
    static class BlockchainLedger {
        private BlockNode genesis = null; // Head of the list

        /**
         * Adds a new block to the end of the chain. This is an O(n) operation.
         */
        public void addBlock(String id, String data, String type) {
            BlockNode newBlock = new BlockNode(id, data, type);
            if (genesis == null) {
                genesis = newBlock;
            } else {
                BlockNode last = genesis;
                while (last.next != null) {
                    last = last.next;
                }
                last.next = newBlock;
                newBlock.prev = last;
            }
            System.out.printf("Block %s (%s - %s) added.\n", id, data, type);
        }

        /**
         * Finds and deletes a block by its ID. Requires O(n) traversal.
         */
        public void deleteBlock(String id) {
            BlockNode current = genesis;
            while (current != null && !current.blockId.equals(id)) {
                current = current.next;
            }

            if (current == null) {
                System.out.printf("Block %s not found.\n", id);
                return;
            }

            if (current.prev != null) {
                current.prev.next = current.next;
            } else {
                genesis = current.next;
            }

            if (current.next != null) {
                current.next.prev = current.prev;
            }
            System.out.printf("Block %s removed.\n", id);
        }

        /**
         * Finds and prints a block by its ID. Requires O(n) traversal.
         */
        public void findBlock(String id) {
            BlockNode current = genesis;
            while (current != null && !current.blockId.equals(id)) {
                current = current.next;
            }

            if (current != null) {
                System.out.printf("%s %s %s\n", current.blockId, current.blockData, current.blockType);
            } else {
                System.out.printf("Block %s not found.\n", id);
            }
        }

        /**
         * Sorts the chain by partitioning it into two sub-lists and merging them.
         */
        public void sortByType(String type) {
            if (genesis == null) {
                 System.out.printf("Blockchain sorted with %s blocks first.\n", type);
                return;
            }

            BlockNode sortedHead = null, sortedTail = null;
            BlockNode otherHead = null, otherTail = null;

            BlockNode current = genesis;
            while (current != null) {
                BlockNode nextNode = current.next;
                current.next = null;
                current.prev = null;

                if (current.blockType.equals(type)) {
                    if (sortedHead == null) {
                        sortedHead = sortedTail = current;
                    } else {
                        sortedTail.next = current;
                        current.prev = sortedTail;
                        sortedTail = current;
                    }
                } else {
                    if (otherHead == null) {
                        otherHead = otherTail = current;
                    } else {
                        otherTail.next = current;
                        current.prev = otherTail;
                        otherTail = current;
                    }
                }
                current = nextNode;
            }

            if (sortedHead == null) {
                this.genesis = otherHead;
            } else {
                this.genesis = sortedHead;
                if (otherHead != null) {
                    sortedTail.next = otherHead;
                    otherHead.prev = sortedTail;
                }
            }
            System.out.printf("Blockchain sorted with %s blocks first.\n", type);
        }

        /**
         * Prints the chain from genesis to the end.
         */
        public void printChain() {
            if (genesis == null) {
                System.out.println("Blockchain is empty");
                return;
            }
            BlockNode current = genesis;
            while (current != null) {
                System.out.printf("%s %s %s\n", current.blockId, current.blockData, current.blockType);
                current = current.next;
            }
        }

        /**
         * Prints the chain in reverse. Requires finding the end first.
         */
        public void printInReverse() {
            if (genesis == null) {
                System.out.println("Blockchain is empty");
                return;
            }
            BlockNode last = genesis;
            while (last.next != null) {
                last = last.next;
            }

            BlockNode current = last;
            while (current != null) {
                System.out.printf("%s %s %s\n", current.blockId, current.blockData, current.blockType);
                current = current.prev;
            }
        }
    }

    /**
     * Main driver method for the blockchain simulation, compatible with the autograder.
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BlockchainLedger chain = new BlockchainLedger();

        // Check if there is input to avoid errors on empty input
        if (!sc.hasNextLine()) {
            sc.close();
            return;
        }
        
        int n = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < n; i++) {
            // Check if there are more lines to read
            if (!sc.hasNextLine()) {
                break;
            }
            String[] parts = sc.nextLine().trim().split(" ");
            String op = parts[0];

            switch (op) {
                case "A":
                    if (parts.length == 4) {
                        chain.addBlock(parts[1], parts[2], parts[3]);
                    } else {
                        System.out.println("Invalid input for Add.");
                    }
                    break;
                case "D":
                    if (parts.length == 2) {
                        chain.deleteBlock(parts[1]);
                    } else {
                        System.out.println("Invalid input for Delete.");
                    }
                    break;
                case "F":
                    if (parts.length == 2) {
                        chain.findBlock(parts[1]);
                    } else {
                        System.out.println("Invalid input for Find.");
                    }
                    break;
                case "P":
                    chain.printChain();
                    break;
                case "R":
                    chain.printInReverse();
                    break;
                case "S":
                    if (parts.length == 2) {
                        chain.sortByType(parts[1]);
                    } else {
                        System.out.println("Invalid input for Sort.");
                    }
                    break;
                default:
                    System.out.println("Invalid operation.");
            }
        }
        sc.close();
    }
}
