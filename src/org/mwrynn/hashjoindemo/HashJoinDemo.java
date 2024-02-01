package org.mwrynn.hashjoindemo;

import java.util.ArrayList;
import java.util.HashMap;

public class HashJoinDemo {
    ArrayList<LargeTableRow> largeTableRows;
    ArrayList<SmallTableRow> smallTableRows;

    public static void main(String[] args) {
        HashJoinDemo hashJoinDemo = new HashJoinDemo();
        hashJoinDemo.loadData(Integer.valueOf(args[0]), Integer.valueOf(args[1]));

        long beforeNestedLoopJoin = System.currentTimeMillis();
        hashJoinDemo.runNestedLoopJoin();
        long afterNestedLoopJoin = System.currentTimeMillis();
        System.out.println("Nested Loop Join took (ms): " + (afterNestedLoopJoin - beforeNestedLoopJoin));

        long beforeHashJoin = System.currentTimeMillis();
        hashJoinDemo.runHashJoin();
        long afterHashJoin = System.currentTimeMillis();

        System.out.println("Hash Join took (ms): " + (afterHashJoin - beforeHashJoin));
    }

    public void loadData(int nSmallTableRows, int nLargeTableRows) {
        largeTableRows = loadLargeTableRows(nLargeTableRows, nSmallTableRows);

        System.out.println("loaded largeTable with " + largeTableRows.size() + " items");

        smallTableRows = loadSmallTableRows(nSmallTableRows);

        System.out.println("loaded smallTable with " + smallTableRows.size() + " items");

    }

    public void runNestedLoopJoin() {
        for (LargeTableRow largeTableRow : largeTableRows) {
            for (SmallTableRow smallTableRow : smallTableRows) {
                if (largeTableRow.smallTableRowId == smallTableRow.id) { // match of join condition
                    ; // pretend this is joining the two rows together
                }
            }
        }
    }

    public void runHashJoin() {
        // set up HashMap of the smaller set of rows, which happens to be SmallTableRow (hardcoded here for simplicity)
        HashMap<Long, SmallTableRow> tagRowHashMap = new HashMap<>();
        for (SmallTableRow smallTableRow : smallTableRows) {
            tagRowHashMap.put(smallTableRow.id, smallTableRow);
        }

        // loop over the larger set of rows, checking for matching rows in the HashMap
        for (LargeTableRow largeTableRow : largeTableRows) {
            SmallTableRow matchingSmallTableRow = tagRowHashMap.get(largeTableRow.smallTableRowId);

            if (matchingSmallTableRow != null) { // i.e. a match was found
                ; // pretend this is joining of two rows together
            }
        }
    }

    public ArrayList<SmallTableRow> loadSmallTableRows(int n) {
        ArrayList<SmallTableRow> smallTableRows = new ArrayList<>();

        for (int i=0; i < n; i++) {
            smallTableRows.add(new SmallTableRow(i, (long)(Math.random()*10000)));
        }

        return smallTableRows;
    }

    public ArrayList<LargeTableRow> loadLargeTableRows(int n, int smallTableIdRange) {
        ArrayList<LargeTableRow> largeTableRows = new ArrayList<>();

        for (int i=0; i < n; i++) {
            largeTableRows.add(new LargeTableRow(i, n % smallTableIdRange, (long)(Math.random()*10000)));
        }

        return largeTableRows;
    }
}
