package person.nightrunner.rdp.impl;

import person.nightrunner.rdp.*;

import java.util.*;

public class EngineImpl implements Engine {

    public EngineImpl(ColumnFactory columnFactory) {
        setColumnFactory(columnFactory);
    }

    protected ColumnFactory columnFactory;

    @Override
    public void setColumnFactory(ColumnFactory columnFactory) {
        this.columnFactory = columnFactory;
    }

    Reader reader;
    Writer writer;
    Context context;
    List<RowDataProcessor> processors = new ArrayList<>();

    @Override
    public List<RowDataProcessor> getProcessors() {
        return processors;
    }

    @Override
    public Engine setContext(Context context) {
        this.context = context;
        return this;
    }

    @Override
    public Engine setReader(Reader reader) {
        this.reader = reader;
        return this;
    }

    @Override
    public Engine setWriter(Writer writer) {
        this.writer = writer;
        return this;
    }

    @Override
    public final Engine addProcessor(RowDataProcessor processors) {
        if (processors != null) {
            Collections.addAll(this.processors, processors);
        }

        return this;
    }

    @Override
    public void go() {
        List<Map<Column, String>> read = reader.read();
        context.setOriginData(read);
        context.setTitleRowCount(reader.getTitleRowCount());
        context.setColumns(reader.getColumns());

        for (String rowKey : context.getRows().keySet()) {
            context.setCurrentRowKey(rowKey);
            for (RowDataProcessor processor : getProcessors()) {
                processor.setContext(context);
                processor.process();
            }
        }
        writer.write(context);
    }
}
