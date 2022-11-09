package person.nightrunner.rdp.impl;

import person.nightrunner.rdp.Column;
import person.nightrunner.rdp.ColumnFactory;
import person.nightrunner.rdp.util.ExcelUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 列的享元工厂
 */
public class ColumnFlyweightFactory implements ColumnFactory {

    private static final Map<Integer, Column> indexMap = new HashMap<>();
    private static final Map<String, Column> labelMap = new HashMap<>();

    private static AtomicInteger nextIndex = new AtomicInteger(0);

    @Override
    public Column getByIndex(Integer columnIndex) {
        if (indexMap.containsKey(columnIndex)) {
            return indexMap.get(columnIndex);
        } else {
            Column column = new ColumnImpl(columnIndex);
            indexMap.put(column.getIndex(), column);
            if (nextIndex.get() < column.getIndex()) {
                nextIndex.compareAndSet(nextIndex.get(), columnIndex);
            }
            return column;
        }
    }

    @Override
    public Column[] getByLabel(String... labels) {
        if (labels == null || labels.length < 1) {
            return null;
        }

        Column[] columns = new Column[labels.length];
        for (int i = 0; i < labels.length; i++) {
            columns[i] = getByLabel(labels[i]);
        }
        return columns;
    }

    @Override
    public Column getByLetter(String letter) {
        // TODO: 2022/11/9 校验是否为字母
        return getByIndex(ExcelUtils.getCellIndex(letter));
    }

    @Override
    public Column getByIndexAndLabel(Integer columnIndex, String label) {
        Column column = null;
        if (indexMap.containsKey(columnIndex)) {
            return indexMap.get(columnIndex);
        } else if (labelMap.containsKey(label)) {
            return labelMap.get(label);
        } else {
            column = new ColumnImpl(label, columnIndex);
            indexMap.put(columnIndex, column);
            labelMap.put(label, column);
            nextIndex.set(columnIndex + 1);
        }
        return column;
    }

    @Override
    public Column getByLabel(String label) {
        Column column = null;
        if (labelMap.containsKey(label)) {
            return labelMap.get(label);
        } else {
            column = new ColumnImpl(label, nextIndex.get());
            indexMap.put(nextIndex.get(), column);
            labelMap.put(label, column);
            nextIndex.getAndIncrement();
        }
        return column;
    }

    private static class ColumnImpl implements Column {
        private static final String INTERNAL_KEY = "%internal_key%_";

        private ColumnImpl(int index) {
            this.index = index;
            this.label = "第" + (index + 1) + "列";
            this.type = Type.String;
            this.internalKey = INTERNAL_KEY + index;
        }

        private ColumnImpl(String name, int index, Column.Type type) {
            this.index = index;
            this.label = name;
            this.type = type;
            this.internalKey = INTERNAL_KEY + index;
        }

        private ColumnImpl(String label, Integer index) {
            this.index = index;
            this.label = label;
            this.type = Type.String;
            this.internalKey = INTERNAL_KEY + index;
        }

        private ColumnImpl(String letter, Column.Type type) {
            this(ExcelUtils.getCellIndex(letter));
            this.type = type;
        }

        private String label;
        private final Integer index;
        private final String internalKey;
        private Type type;

        @Override
        public Integer getIndex() {
            return index;
        }

        @Override
        public String getLabel() {
            return label;
        }

        @Override
        public String getInternalKey() {
            return internalKey;
        }

        @Override
        public Type getType() {
            return type;
        }

        @Override
        public String toString() {
            return internalKey;
        }

        @Override
        public int hashCode() {
            return internalKey.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof ColumnFlyweightFactory.ColumnImpl) {
                return this.internalKey.equals(((ColumnFlyweightFactory.ColumnImpl) obj).internalKey);
            }
            return super.equals(obj);
        }

    }

}
