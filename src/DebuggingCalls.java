
/**
 * Test cases for Hash Table with linear probing.
 */
public class DebuggingCalls
{
    public static void main(String[] args)
    {
        HashTable table = new HashTable(17);
        
        // insertions - no collisions
        table.put(92800393, "LINNIE GILMAN");
        table.put(86770985, "DUSTY CONFER");
        
        // collisions - 1 probe
        table.put(48235250, "KENNITH GRASSMYER");
        table.put(31850991, "WANETA DEWEES");
        
        // insertions - no collisions
        table.put(25428367, "DUSTY BANNON");
        table.put(24248685, "FRANCE COELLO");
        table.put(23331143, "JUSTIN ADKIN");
        
        // collisions - multiple probes
        table.put(68682774, "MALIK TULLER");
        table.put(59245514, "LESLEE PHIFER");
        
        // 2nd put for this key - replace FRANCE COELLO
        // simple overwrite, no collisions
        table.put(24248685, "ISAAC GENEY");
        // remove DUSTY BANNON, simple, no collisions
        table.remove(25428367);
        // remove MALIK TULLER, collisions & invalid entry
        table.remove(68682774);
        // collisions  & invalid entry
        table.put(54657809, "MARTY ENOCHS");
        // remove JUSTIN ADKIN, simple, no collisions
        table.remove(23331143);
        
        // 2nd put for this key - replace LESLEE PHIFER
        // collisions, overwrite invalid entry, &
        // invalidate original entry
        table.put(59245514, "GENARO QUIDER");

        System.out.println(table);
    }
}
