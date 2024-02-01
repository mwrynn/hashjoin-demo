package org.mwrynn.hashjoindemo;

public class LargeTableRow {
    public long id;
    public long smallTableRowId;
    public long dummy;

    public LargeTableRow(long id, long smallTableRowId, long dummy) {
        this.id = id;
        this.smallTableRowId = smallTableRowId;
        this.dummy = dummy;
    }

}
