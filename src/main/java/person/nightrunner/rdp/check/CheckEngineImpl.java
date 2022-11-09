package person.nightrunner.rdp.check;

import person.nightrunner.rdp.ColumnFactory;
import person.nightrunner.rdp.RowDataProcessor;
import person.nightrunner.rdp.check.impl.StatusAndErrorMessageAppender;
import person.nightrunner.rdp.impl.EngineImpl;

import java.util.List;

public class CheckEngineImpl extends EngineImpl {
    @Override
    public List<RowDataProcessor> getProcessors() {
        List<RowDataProcessor> processors = super.getProcessors();
        processors.add(new StatusAndErrorMessageAppender(columnFactory));
        return processors;
    }

    public CheckEngineImpl(ColumnFactory columnFactory) {
        super(columnFactory);
    }
}
