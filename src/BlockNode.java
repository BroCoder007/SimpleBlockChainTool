public class BlockNode {
        String bId;
        String bData; //blockData
        String bType; //blockType
        BlockNode prev;
        BlockNode next;

        BlockNode(String id, String data, String type) {
            this.bId = id;
            this.bData = data;
            this.bType = type;
        }
    }