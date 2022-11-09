package person.nightrunner.rdp.check.impl;

import person.nightrunner.rdp.*;
import person.nightrunner.rdp.check.CheckContext;

import java.util.ArrayList;
import java.util.List;

public class RequiredFieldsChecker extends RowDataProcessorBase {

    CheckContext context;

    @Override
    public <T extends Context> void setContext(T context) {
        this.context = (CheckContext) context;
    }

    private List<Column> needCheckColumns;

    public RequiredFieldsChecker(List<Column> needCheckColumns) {
        this.needCheckColumns = needCheckColumns;
    }

    public RequiredFieldsChecker(Column column) {
        List<Column> columns = new ArrayList<>();
        columns.add(column);
        this.needCheckColumns = columns;
    }

    @Override
    public void process() {
        if (needCheckColumns == null || needCheckColumns.size() == 0) {
            return;
        }

        for (Column column : needCheckColumns) {
            String originValue = context.getCurrentRowColumnValue(column);
            if (originValue == null || "".equals(originValue)) {
                context.addErrorMessage(String.format("%s不能为空", column.getLabel()));
            }
        }
    }


}
