package setassociativecache;

import java.io.*;
import java.util.*;

public class Solution {
    public static void main(String[] args) throws IOException {
        SetAssociativeCacheRunner.parseInput(System.in);
    }

    /**
     * Parses Test Case input to instantiate and invoke a SetAssociativeCache
     *
     * NOTE: You can typically ignore anything in here. Feel free to collapse...
     */
    static class SetAssociativeCacheRunner {
        public static void parseInput(InputStream inputStream) throws IOException {
            InputStreamReader inputReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputReader);

            String line;
            int lineCount = 0;
            SetAssociativeCache<String, String> cache = null;

            while (!isNullOrEmpty(line = reader.readLine())) {
                lineCount++;
                OutParam<String> replacementAlgoName = new OutParam<>();

                if (lineCount == 1) {

                    cache = createCache(line, replacementAlgoName);

                } else {

                    // All remaining lines invoke instance methods on the SetAssociativeCache
                    Object retValue = SetAssociativeCacheFactory.InvokeCacheMethod(line, cache);

                    // Write the method's return value (if any) to stdout
                    if (retValue != null) {
                        System.out.println(retValue);
                    }
                }
            }
        }
    }

    private static SetAssociativeCache<String, String> createCache(String inputLine, OutParam<String> replacementAlgoName) {
        String[] cacheParams = Arrays.stream(inputLine.split(",")).map(s -> s.trim()).toArray(n -> new String[n]);
        int setCount = Integer.parseInt(cacheParams[0]);
        int setSize = Integer.parseInt(cacheParams[1]);
        replacementAlgoName.value = cacheParams[2];
        return SetAssociativeCacheFactory.CreateStringCache(setCount, setSize, replacementAlgoName.value);
    }


    // ############################ BEGIN Solution Classes ############################

    /**
     * NOTE: You are free to modify anything below, except for class names and generic interface.
     * Other public interface changes may require updating one or more of the helper classes above
     * for test cases to run and pass.
     * <p>
     * A Set-Associative Cache data structure with fixed capacity.
     * <p>
     * - Data is structured into setCount # of setSize-sized sets.
     * - Every possible key is associated with exactly one set via a hashing algorithm
     * - If more items are added to a set than it has capacity for (i.e. > setSize items),
     *      a replacement victim is chosen from that set using an LRU algorithm.
     * <p>
     * NOTE: Part of the exercise is to allow for different kinds of replacement algorithms...
     */
    public static class SetAssociativeCache<TKey, TValue> {
        int Capacity;
        int SetSize;
        int SetCount;
        CacheSet<TKey, TValue>[] Sets;
        IReplacementAlgo replacementAlgo;

        public SetAssociativeCache(int setCount, int setSize, String replacementAlgo) {
            this.replacementAlgo = new ReplacementAlgoFactory().createReplacementAlgo(replacementAlgo);
            this.SetCount = setCount;
            this.SetSize = setSize;
            this.Capacity = this.SetCount * this.SetSize;

            // Initialize the sets
            this.Sets = new CacheSet[this.SetCount];
            for (int i = 0; i < this.SetCount; i++) {
                Sets[i] = new CacheSet<>(setSize, this.replacementAlgo);
            }
        }

        /** Gets the value associated with `key`. Throws if key not found. */
        public TValue get(TKey key) {
            int setIndex = this.getSetIndex(key);
            CacheSet<TKey, TValue> set = this.Sets[setIndex];
            return set.get(key);
        }

        /**
         * Adds the `key` to the cache with the associated value, or overwrites the existing key.
         * If adding would exceed capacity, an existing key is chosen to replace using an LRU algorithm
         * (NOTE: It is part of this exercise to allow for more replacement algos)
         */
        public void set(TKey key, TValue value) {
            int setIndex = this.getSetIndex(key);
            CacheSet<TKey, TValue> set = this.Sets[setIndex];
            set.set(key, value);
        }

        /** Returns the count of items in the cache */
        public int getCount() {
            int count = 0;
            for (int i = 0; i < this.Sets.length; i++) {
                count += this.Sets[i].StoreMap.size();
            }
            return count;
        }

        /** Returns `true` if the given `key` is present in the set; otherwise, `false`. */
        public boolean containsKey(TKey key) {
            int setIndex = this.getSetIndex(key);
            CacheSet<TKey, TValue> set = this.Sets[setIndex];
            return set.containsKey(key);
        }

        /** Maps a key to a set */
        private int getSetIndex(TKey key) {
            int c = Integer.MAX_VALUE;
            int s = -1;
            for (int i = 0; i < this.Sets.length; i++) {
                if (this.Sets[i].containsKey(key)) {
                    return i;
                }
                if (this.Sets[i].StoreMap.size() < c) {
                    c = this.Sets[i].StoreMap.size();
                    s = i;
                }
            }
            return s;
        }
    }

    static class Node<TKey,TValue> {
        TKey key;
        TValue value;
        Node left;
        Node right;
    }

    /**
     * An internal data structure representing one set in a N-Way Set-Associative Cache
     */
    static class CacheSet<TKey, TValue> {
        int Capacity;
        Map<TKey,Node> StoreMap;
        Node start, end;
        IReplacementAlgo<TKey,TValue> replacementAlgo;

        public CacheSet(int capacity, IReplacementAlgo<TKey,TValue> replacementAlgo) {
            this.Capacity = capacity;
            this.StoreMap = new HashMap<>(capacity);
            this.replacementAlgo = replacementAlgo;
        }

        /** Gets the value associated with `key`. Throws if key not found. */
        private synchronized TValue get(TKey key) {
            // If the key is present, update the usage tracker
            if (this.StoreMap.containsKey(key)) {
                Node node = this.StoreMap.get(key);
                replacementAlgo.replace(this, node);
                return (TValue) node.value;
            } else {
                throw new RuntimeException(String.format("The key '%s' was not found", key));
            }
        }

        /**
         * Adds the `key` to the cache with the associated value, or overwrites the existing key.
         * If adding would exceed capacity, an existing key is chosen to replace using an LRU algorithm
         * (NOTE: It is part of this exercise to allow for more replacement algos)
         */
        private synchronized void set(TKey key, TValue value) {
            if( this.StoreMap.containsKey(key)) {
                Node node = this.StoreMap.get(key);
                node.value = value;
                replacementAlgo.replace(this, node);
            } else {
                Node node = new Node();
                node.left = null;
                node.right = null;
                node.key = key;
                node.value = value;
                replacementAlgo.replacePut(this, node);
            }
        }

        /** Returns `true` if the given `key` is present in the set; otherwise, `false`. */
        private synchronized boolean containsKey(TKey key) {
            return this.StoreMap.containsKey(key);
        }
    }

    public final static String LruAlgorithm = "LRUReplacementAlgo";
    public final static String MruAlgorithm = "MRUReplacementAlgo";

    /**
     * A common interface for replacement algos, which decide which item in a CacheSet to evict
     */
    interface IReplacementAlgo<K,V> {
        void replace(CacheSet<K,V> cacheSet, Node current);

        void replacePut(CacheSet<K, V> cacheSet, Node node);
    }

    static class LRUReplacementAlgo<TKey,TValue> implements IReplacementAlgo<TKey,TValue> {

        @Override
        public void replace(CacheSet<TKey,TValue> cacheSet, Node node) {
            removeNode(cacheSet, node);
            addAtTop(cacheSet, node);
        }

        @Override
        public void replacePut(CacheSet<TKey, TValue> cacheSet, Node node) {
            if (cacheSet.StoreMap.size() >= cacheSet.Capacity) {
                cacheSet.StoreMap.remove(cacheSet.end.key);
                removeNode(cacheSet, node);
                addAtTop(cacheSet, node);
            } else {
                addAtTop(cacheSet, node);
            }
            cacheSet.StoreMap.put((TKey) node.key, node);
        }

        private void removeNode(CacheSet<TKey,TValue> cacheSet, Node node) {
            if (node.left != null) {
                node.left.right = node.right;
            } else {
                cacheSet.start = node.right;
            }

            if (node.right != null) {
                node.right.left = node.left;
            } else {
                cacheSet.end = node.left;
            }
        }

        private void addAtTop(CacheSet<TKey,TValue> cacheSet, Node node) {
            node.right = cacheSet.start;
            node.left = null;
            if (cacheSet.start != null)
                cacheSet.start.left = node;
            cacheSet.start = node;
            if (cacheSet.end == null)
                cacheSet.end = cacheSet.start;
        }
    }

    static class MRUReplacementAlgo<TKey,TValue> implements IReplacementAlgo<TKey,TValue> {
        @Override
        public void replace(CacheSet<TKey, TValue> cacheSet, Node current) {

        }

        @Override
        public void replacePut(CacheSet<TKey, TValue> cacheSet, Node node) {

        }
    }

    // ############################ BEGIN Helper Classes ############################
    // NOTE: Your code in the classes below will not be evaluated as part of the exericse.
    // They are just used by the stub code in the header to help run HackerRank test cases.
    // You may need to make small modifications to these classes, depending on your interface design,
    // for tests to run and pass, but it is not a core part of the exercise
    //
    static class OutParam<T> {
        public T value;
    }

    private static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public static class SetAssociativeCacheFactory {
        /// NOTE: replacementAlgoName is provided in case you need it here. Whether you do will depend on your interface design.
        public static SetAssociativeCache<String, String> CreateStringCache(int setCount, int setSize, String replacementAlgoName) {
            return new SetAssociativeCache<>(setCount, setSize, replacementAlgoName);
        }

        /// NOTE: Modify only if you change the main interface of SetAssociativeCache
        public static Object InvokeCacheMethod(String inputLine, SetAssociativeCache<String, String> cacheInstance) {
            String[] callArgs = Arrays.stream(inputLine.split(",", -1)).map(a -> a.trim()).toArray(n -> new String[n]);

            String methodName = callArgs[0].toLowerCase();
            //String[] callParams = Arrays.copyOfRange(callArgs, 1, callArgs.length - 1); // TODO: This is unused

            switch (methodName) {
                case "get":
                    return cacheInstance.get(callArgs[1]);
                case "set":
                    cacheInstance.set(callArgs[1], callArgs[2]);
                    return null;
                case "containskey":
                    return cacheInstance.containsKey(callArgs[1]);
                case "getcount":
                    return cacheInstance.getCount();

                // TODO: If you want to add and test other public methods to SetAssociativeCache,
                //  add them to the switch statement here... (this is not common)

                default:
                    throw new RuntimeException(String.format("Unknown method name '{%s}'", methodName));
            }
        }
    }

    // TODO: Consider making use of this in the `SetAssociativeCacheFactory` above to map replacement algo name 
    // to a IReplacementAlgo instance for the interface you design
    public static class ReplacementAlgoFactory {
        IReplacementAlgo createReplacementAlgo(String replacementAlgoName) {
            switch (replacementAlgoName) {
                case LruAlgorithm:
                    return new LRUReplacementAlgo();
                case MruAlgorithm:
                    return new MRUReplacementAlgo();
                default:
                    // TODO: If you want to test other replacement algos, add them to the switch statement here...
                    throw new RuntimeException(String.format("Unknown replacement algo '%s'", replacementAlgoName));
            }
        }
    }

    // ^^ ######################### END Helper Classes ######################### ^^

}