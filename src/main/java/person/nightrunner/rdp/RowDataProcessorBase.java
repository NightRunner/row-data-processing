package person.nightrunner.rdp;

public abstract class RowDataProcessorBase implements RowDataProcessor {

    protected ColumnFactory columnFactory;

    @Override
    public void setColumnFactory(ColumnFactory columnFactory) {
        this.columnFactory = columnFactory;
    }
}
