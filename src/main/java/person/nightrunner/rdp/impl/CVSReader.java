package person.nightrunner.rdp.impl;

import person.nightrunner.rdp.Column;
import person.nightrunner.rdp.ColumnFactory;
import person.nightrunner.rdp.Reader;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

public class CVSReader extends FileReaderBase implements Reader {

    public static final String SEPARATOR = ",";

    public CVSReader(ColumnFactory columnFactory, File file) {
        super(columnFactory, file);
    }

    public CVSReader(ColumnFactory columnFactory, File file, int titleRowCount) {
        super(columnFactory, file, titleRowCount);
    }

    public CVSReader(ColumnFactory columnFactory, File file, int titleRowCount, List<Column> columns) {
        super(columnFactory, file, titleRowCount, columns);
    }

    public CVSReader(ColumnFactory columnFactory, File file, int titleRowCount, Column... columns) {
        super(columnFactory, file, titleRowCount, columns);
    }

    @Override
    public List<Map<Column, String>> read() {

        List<Map<Column, String>> result = new ArrayList<>();

        List<String> lines = readLines();

        //初始化columns
        if (columns == null || columns.size() == 0) {
            String title = lines.get(titleRowCount - 1);
            String[] split = title.split(SEPARATOR);
            columns = new ArrayList<>();

            for (int i = 0; i < split.length; i++) {
                columns.add(columnFactory.getByLabel(split[i]));
            }
        }

        for (String line : lines) {

            Map<Column, String> row = new HashMap<>();

            String[] split = line.split(SEPARATOR);
            for (Column column : columns) {
                if (column.getIndex() > split.length) {
                    row.put(column, null);
                } else {
                    row.put(column, split[column.getIndex()]);
                }
            }

            result.add(row);
        }

        return result;
    }

    private List<String> readLines() {
        List<String> result = new ArrayList<>();
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(Files.newInputStream(file.toPath()), fileCharset))) {
            String line = null;
            while ((line = fileReader.readLine()) != null) {
                result.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}