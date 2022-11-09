package person.nightrunner.rdp.impl;

import person.nightrunner.rdp.Column;
import person.nightrunner.rdp.Context;
import person.nightrunner.rdp.Writer;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class POIExcelWriter implements Writer {

    private final File file;

    private final String sheetName;

    private List<Column> columns;

    @Override
    public void write(Context context) {
        if (columns == null) {
            this.columns = context.getOutputColumns();
        }

        Map<String, Map<Column, String>> rows = context.getOutputRows();

        //创建一个工作簿
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        //创建一个工作表
        HSSFSheet sheet = hssfWorkbook.createSheet(sheetName);

        //创建行,第一行表头
        HSSFRow titleRow = sheet.createRow(0);
        for (int i = 0; i < columns.size(); i++) {
            HSSFCell cell = titleRow.createCell(i);
            cell.setCellValue(columns.get(i).getLabel());
        }

        int rowIndex = 0;
        for (Map.Entry<String, Map<Column, String>> entry : rows.entrySet()) {
            HSSFRow row = sheet.createRow((rowIndex++) + 1);
            Map<Column, String> rowData = entry.getValue();

            for (Column column : columns) {
                row.createCell(column.getIndex()).setCellValue(rowData.get(column));
            }
        }

        //把数据输出到硬盘中
        OutputStream outputStream = null;
        try {
            outputStream = Files.newOutputStream(file.toPath());
            hssfWorkbook.write(outputStream);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public POIExcelWriter(File file, String sheetName, Column... columns) {
        this.file = file;
        this.sheetName = sheetName;
        this.columns = Arrays.asList(columns);
    }

    public POIExcelWriter(File file, String sheetName) {
        this.file = file;
        this.sheetName = sheetName;
    }

}
