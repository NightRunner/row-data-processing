//package person.nightrunner.rdp.impl;
//
//import person.nightrunner.rdp.Column;
//import person.nightrunner.rdp.util.ExcelUtils;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class ColumnImpl implements Column {
//    private static final String INTERNAL_KEY = "%internal_key%_";
//
//    public ColumnImpl(int index) {
//        this.index = index;
//        this.label = "第" + (index + 1) + "列";
//        this.type = Type.String;
//        this.internalKey = INTERNAL_KEY + index;
//    }
//
//    public ColumnImpl(String name, int index, Column.Type type) {
//        this.index = index;
//        this.label = name;
//        this.type = type;
//        this.internalKey = INTERNAL_KEY + index;
//    }
//
//    public ColumnImpl(String label, Integer index) {
//        this.index = index;
//        this.label = label;
//        this.type = Type.String;
//        this.internalKey = INTERNAL_KEY + index;
//    }
//
//    public ColumnImpl(String columnExcelName) {
//        this(ExcelUtils.getCellIndex(columnExcelName));
//    }
//
//    public ColumnImpl(String label, String columnExcelName) {
//        this(ExcelUtils.getCellIndex(columnExcelName));
//        this.label = label;
//    }
//
//    public ColumnImpl(String columnExcelName, Column.Type type) {
//        this(ExcelUtils.getCellIndex(columnExcelName));
//        this.type = type;
//    }
//
//    private String label;
//    private Integer index;
//    private String internalKey;
//    private Type type;
//
//    public static Column[] create(String... labels) {
//        return ColumnFlyweight.createByLabel(labels);
//    }
//
//    @Override
//    public Integer getIndex() {
//        return index;
//    }
//
//    @Override
//    public String getLabel() {
//        return label;
//    }
//
//    @Override
//    public String getInternalKey() {
//        return internalKey;
//    }
//
//    @Override
//    public Type getType() {
//        return type;
//    }
//
//    @Override
//    public String toString() {
//        return internalKey;
//    }
//
//    @Override
//    public int hashCode() {
//        return internalKey.hashCode();
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (obj instanceof ColumnImpl) {
//            return this.internalKey.equals(((ColumnImpl) obj).internalKey);
//        }
//        return super.equals(obj);
//    }
//
//}
//
