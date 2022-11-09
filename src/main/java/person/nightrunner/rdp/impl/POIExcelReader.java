package person.nightrunner.rdp.impl;

import com.alibaba.excel.util.StringUtils;
import person.nightrunner.rdp.Column;
import person.nightrunner.rdp.ColumnFactory;
import person.nightrunner.rdp.util.ExcelUtils;
import person.nightrunner.rdp.Reader;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.util.*;

public class POIExcelReader extends ReaderBase implements Reader {

    private final String sheetName;
    private final int titleRowCount;
    /**
     * 文件路径
     */
    private File file;

    private List<Column> columns;

    @Override
    public int getTitleRowCount() {
        return titleRowCount;
    }

    @Override
    public List<Column> getColumns() {
        return columns;
    }

    public POIExcelReader(ColumnFactory columnFactory, File file, String sheetName, int titleRowCount, Column... columns) {
        super(columnFactory);
        this.file = file;
        this.sheetName = sheetName;
        this.titleRowCount = titleRowCount;
        this.columns = Arrays.asList(columns);
    }

    @Override
    public List<Map<Column, String>> read() {

        List<Map<Column, String>> data = new ArrayList<>();

        Workbook workbook = ExcelUtils.checkFileFormat(file);

        Sheet sheet = workbook.getSheet(sheetName);

        //初始化columns
        if (columns == null) {
            List<Column> tmpColumns = new ArrayList<>();
            Row row = sheet.getRow(titleRowCount - 1);
            Iterator<Cell> cellIterator = row.cellIterator();
            int i = 0;
            while (cellIterator.hasNext()) {
                Cell next = cellIterator.next();
                tmpColumns.add(columnFactory.getByLabel(next.getStringCellValue()));
            }

            if (columns.size() > 0) {
                columns = tmpColumns;
            }
        }

        Iterator<Row> rowIterator = sheet.rowIterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Map<Column, String> columnMap = new LinkedHashMap<>();

            for (Column column : columns) {
                Cell cell = row.getCell(column.getIndex());
                if (cell != null) {
                    CellType cellType = cell.getCellType();

                    if (CellType.NUMERIC == cellType) {
                        double numericCellValue = cell.getNumericCellValue();
                        columnMap.put(column, Double.toString(numericCellValue));
                    } else {
                        String stringCellValue = cell.getStringCellValue();
                        if (stringCellValue != null) {
                            columnMap.put(column, stringCellValue.trim());
                        } else {
                            columnMap.put(column, null);
                        }
                    }

                } else {
                    columnMap.put(column, null);
                }
            }

            if (columnMap.size() == 0 || isOnlyOneBlank(columnMap)) {
                continue;
            }
            data.add(columnMap);
        }

        return data;
    }

    private boolean isOnlyOneBlank(Map<Column, String> columnMap) {
        int i = 0;
        for (Map.Entry<Column, String> columnStringEntry : columnMap.entrySet()) {
            if (i > 1) {
                return false;
            }

            String value = columnStringEntry.getValue();
            if (StringUtils.isBlank(value) && columnMap.size() == 1) {
                return true;
            }
            i++;
        }

        return false;
    }

}
