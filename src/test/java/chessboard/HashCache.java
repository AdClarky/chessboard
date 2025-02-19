package chessboard;

import java.util.concurrent.ConcurrentHashMap;

public class HashCache {
    private final ConcurrentHashMap<Long, PerftEntry> table;

    public HashCache() {
        table = new ConcurrentHashMap<>();
    }

    public long lookup(long hash, int depth){
        PerftEntry val = table.get(hash);
        if(val == null)
            return -1;
        if(depth != val.depth())
            return -1;
        return val.result();
    }

    public void put(long hash, int depth, long result){
        table.compute(hash, (k, v) -> v == null ? new PerftEntry(depth, result) : v);
    }
}
