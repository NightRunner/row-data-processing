package person.nightrunner.rdp;

import java.util.List;
import java.util.Map;

public interface Context  {

    void setTitleRowCount(int titleRowCount);

    void setOriginData(List<Map<Column, String>> data);

    Map<String, Map<Column, String>> getRows();

    Map<String, Map<Column, String>> getOutputRows();

    List<Column> getColumns();

    void setColumns(List<Column> columns);

    Map<Column, String> getCurrentRow();

    void setCurrentRowValue(Column column, String value);

    String getCurrentRowColumnValue(Column column);

    void setCurrentRowKey(String currentRowKey);

    Map<String, Map<Column, String>> getTitles();

    void appendCurrentRowData(Column column, String content);

    void addAppendColumn(Column column);

    List<Column> getOutputColumns();
}
