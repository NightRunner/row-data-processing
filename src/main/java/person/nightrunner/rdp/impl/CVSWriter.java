package person.nightrunner.rdp.impl;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import person.nightrunner.rdp.Column;
import person.nightrunner.rdp.Context;
import person.nightrunner.rdp.Writer;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CVSWriter implements Writer {

    private final File file;

    @Override
    public void write(Context context) {
        try (PrintWriter printWriter = new PrintWriter(file);) {
            Map<String, Map<Column, String>> rows = context.getTitles();
            for (Map.Entry<String, Map<Column, String>> rowEntry : rows.entrySet()) {
                printWriter.println(String.join(",", rowEntry.getValue().values()));
            }
            rows = context.getOutputRows();
            for (Map.Entry<String, Map<Column, String>> rowEntry : rows.entrySet()) {
                printWriter.println(String.join(",", rowEntry.getValue().values()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public CVSWriter(File file) {
        this.file = file;
    }

}
