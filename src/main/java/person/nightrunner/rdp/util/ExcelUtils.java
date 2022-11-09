package person.nightrunner.rdp.util;

import com.alibaba.excel.util.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.nio.file.Files;
import java.util.Date;

public class ExcelUtils {
    public static int getCellIndex(String cellName) {

        char[] cellStrArray = cellName.toUpperCase().toCharArray();

        int len = cellStrArray.length;

        int n = 0;

        for (int i = 0; i < len; i++) {
            n += (((int) cellStrArray[i]) - 65 + 1) * Math.pow(26, len - i - 1);
        }

        return n - 1;
    }

    public static Workbook checkFileFormat(File file) {
        Workbook workbook = null;
        boolean xlsx = file.getName().toLowerCase().endsWith(".xlsx");
        try {
            workbook = xlsx ? new XSSFWorkbook(Files.newInputStream(file.toPath())) : new HSSFWorkbook(Files.newInputStream(file.toPath()));
        } catch (Exception e) {
            String suffix = "xls";
            if (xlsx) {
                suffix = "xlsx";
            }
            throw new IllegalArgumentException(String.format("文件格式错误！不是%s格式！（只支持xls,xlsx格式）", suffix));
        }
        return workbook;
    }

    public static String getCellStrValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        CellType cellType = cell.getCellType();
        if (CellType.STRING == cellType) {
            return cell.getStringCellValue().trim();
        } else if (CellType.NUMERIC == cellType) {
            return String.valueOf(cell.getNumericCellValue()).trim();
        } else {
            return "";
        }
    }

    public static Date getCellDateValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        try {
            CellType cellType = cell.getCellType();
            if (CellType.STRING == cellType) {
                return DateUtils.parseDate(cell.getStringCellValue().trim(), "yyyy-MM-dd");
            } else if (DateUtil.isCellDateFormatted(cell)) {
                return cell.getDateCellValue();
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("excel转换Date失败:");
            e.printStackTrace();
        }
        return null;
    }
}
