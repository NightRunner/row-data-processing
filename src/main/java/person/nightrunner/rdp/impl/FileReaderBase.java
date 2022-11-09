package person.nightrunner.rdp.impl;

import person.nightrunner.rdp.Column;
import person.nightrunner.rdp.ColumnFactory;
import person.nightrunner.rdp.Reader;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class FileReaderBase extends ReaderBase implements Reader {

    public FileReaderBase(ColumnFactory columnFactory) {
        super(columnFactory);
    }

    protected File file;

    protected int titleRowCount = 1;

    protected List<Column> columns = new ArrayList<>();

    protected Charset fileCharset = StandardCharsets.UTF_8;

    public FileReaderBase(ColumnFactory columnFactory, File file) {
        super(columnFactory);
        this.file = file;
    }

    public FileReaderBase(ColumnFactory columnFactory, File file, int titleRowCount) {
        this(columnFactory, file);
        this.titleRowCount = titleRowCount;
    }

    public FileReaderBase(ColumnFactory columnFactory, File file, int titleRowCount, List<Column> columns) {
        this(columnFactory, file, titleRowCount);
        this.columns = columns;
    }

    public FileReaderBase(ColumnFactory columnFactory, File file, int titleRowCount, Column... columns) {
        this(columnFactory, file, titleRowCount, Arrays.asList(columns));
    }

    @Override
    public List<Column> getColumns() {
        return columns;
    }

    @Override
    public int getTitleRowCount() {
        return titleRowCount;
    }
}
