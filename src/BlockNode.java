public class BlockNode {
        String blockId;
        String blockData; //blockData
        String blockType; //blockType
        BlockNode prev;
        BlockNode next;

        BlockNode(String id, String data, String type) {
            this.blockId = id;
            this.blockData = data;
            this.blockType = type;
        }
    }