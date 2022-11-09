package person.nightrunner.rdp.impl;

import person.nightrunner.rdp.ColumnFactory;
import person.nightrunner.rdp.Reader;

public abstract class ReaderBase implements Reader {

    public ReaderBase(ColumnFactory columnFactory) {
        this.columnFactory = columnFactory;
    }

    protected ColumnFactory columnFactory;

    @Override
    public void setColumnFactory(ColumnFactory columnFactory) {
        this.columnFactory = columnFactory;
    }
}
