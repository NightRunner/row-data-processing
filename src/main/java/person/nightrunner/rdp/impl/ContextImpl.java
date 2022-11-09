package person.nightrunner.rdp.impl;

import person.nightrunner.rdp.Column;
import person.nightrunner.rdp.Context;

import java.util.*;

public class ContextImpl implements Context {

    @Override
    public void setCurrentRowValue(Column column, String value) {
        getCurrentRow().put(column, value);
    }

    private List<Map<Column, String>> originData;

    private List<Column> columns;

    private final List<Column> appendColumns = new ArrayList<>();

    private final Map<String, Map<Column, String>> appendRows = new HashMap<>();

    private Map<String, Map<Column, String>> rows;

    private Map<String, Map<Column, String>> titles;

    private Integer titleRowCount;

    protected String currentRowKey;

    private Map<Column, String> currentRow;

    public ContextImpl(List<Map<Column, String>> originData, int titleRowCount, List<Column> columns) {
        this.columns = columns;
        setOriginData(originData);
        setTitleRowCount(titleRowCount);
    }

    public ContextImpl(int titleRowCount) {
        this.titleRowCount = titleRowCount;
    }

    public ContextImpl() {
    }

    @Override
    public void setColumns(List<Column> columns) {
        this.columns = columns;
        init();
    }

    @Override
    public Map<String, Map<Column, String>> getTitles() {
        return titles;
    }

    @Override
    public void appendCurrentRowData(Column column, String data) {
        Map<Column, String> appendMap = appendRows.getOrDefault(currentRowKey, new LinkedHashMap<>());
        String appendInfo = appendMap.getOrDefault(column, "");
        appendInfo += data;
        appendMap.put(column, appendInfo);
        appendRows.put(currentRowKey, appendMap);
        if (!appendColumns.contains(column)) {
            appendColumns.add(column);
        }
    }

    @Override
    public void setOriginData(List<Map<Column, String>> data) {
        this.originData = data;
        init();
    }

    @Override
    public void setCurrentRowKey(String currentRowKey) {
        this.currentRowKey = currentRowKey;
    }

    @Override
    public void setTitleRowCount(int titleRowCount) {
        this.titleRowCount = titleRowCount;
        init();
    }

    private void init() {

        if (titleRowCount == null || (originData == null || originData.size() == 0) || columns == null) {
            return;
        }

        //初始化数据
        rows = new LinkedHashMap<>();
        titles = new LinkedHashMap<>();
        for (int i = 0; i < originData.size(); i++) {
            if (i < titleRowCount) {
                titles.put(Integer.toString(i), originData.get(i));
            } else {
                rows.put(Integer.toString(i), originData.get(i));
            }
        }

    }

    @Override
    public Map<String, Map<Column, String>> getRows() {
        return rows;
    }

    @Override
    public Map<String, Map<Column, String>> getOutputRows() {
        Map<String, Map<Column, String>> result = new HashMap<>();
        for (Map.Entry<String, Map<Column, String>> entry : rows.entrySet()) {
            Map<Column, String> value = entry.getValue();
            String key = entry.getKey();

            if (appendRows.get(key) != null) {
                Map<Column, String> append = appendRows.get(key);
                value.putAll(append);
            }

            result.put(key, value);
        }
        return result;
    }

    @Override
    public List<Column> getOutputColumns() {
        List<Column> result = new ArrayList<>(columns);
        result.addAll(appendColumns);
        return result;
    }

    @Override
    public List<Column> getColumns() {
        return columns;
    }


    private void checkCurrent() {
        if (getCurrentRow() == null) {
            throw new UnsupportedOperationException("未设置当前处理的行");
        }
    }

    @Override
    public Map<Column, String> getCurrentRow() {
        if (currentRowKey == null) {
            throw new UnsupportedOperationException("请先设置当前行");
        }
        this.currentRow = this.originData.get(Integer.parseInt(currentRowKey));
        return currentRow;
    }

    @Override
    public String getCurrentRowColumnValue(Column column) {
        Map<Column, String> row = getCurrentRow();
        return row.get(column);
    }

    @Override
    public void addAppendColumn(Column column) {
        if (!columns.contains(column)) {
            this.appendColumns.add(column);
        }
    }
}
