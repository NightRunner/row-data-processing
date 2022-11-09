package person.nightrunner.rdp.check.impl;

import person.nightrunner.rdp.Column;
import person.nightrunner.rdp.ColumnFactory;
import person.nightrunner.rdp.Context;
import person.nightrunner.rdp.RowDataProcessorBase;
import person.nightrunner.rdp.check.CheckContext;

public class StatusAndErrorMessageAppender extends RowDataProcessorBase {

    CheckContext context;

    @Override
    public void process() {
        initColumn();
        if (context.getErrorMessages() == null || context.getErrorMessages().size() == 0) {
            context.appendCurrentRowData(statusColumn, "成功");
            return;
        }

        context.appendCurrentRowData(statusColumn, "失败");
        context.appendCurrentRowData(errorMessageColumn, String.join(",", context.getErrorMessages()));
    }

    @Override
    public <T extends Context> void setContext(T context) {
        this.context = (CheckContext) context;
    }

    private Column statusColumn;
    private Column errorMessageColumn;

    public StatusAndErrorMessageAppender(ColumnFactory columnFactory) {
        setColumnFactory(columnFactory);
    }

    public StatusAndErrorMessageAppender(ColumnFactory columnFactory, Column statusColumn, Column errorColumn) {
        setColumnFactory(columnFactory);
        this.statusColumn = statusColumn;
        this.errorMessageColumn = errorColumn;
    }

    private static final String COLUMN_STATUS = "状态";
    private static final String COLUMN_ERROR_MESSAGE = "错误信息";

    private void initColumn() {
        //如果没有设置这两个,则附加在最后
        if (statusColumn == null) {
            statusColumn = columnFactory.getByLabel(COLUMN_STATUS);
            context.addAppendColumn(statusColumn);
        }
        if (errorMessageColumn == null) {
            errorMessageColumn = columnFactory.getByLabel(COLUMN_ERROR_MESSAGE);
            context.addAppendColumn(errorMessageColumn);
        }
    }
}
