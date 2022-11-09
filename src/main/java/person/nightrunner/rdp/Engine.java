package person.nightrunner.rdp;

import java.util.List;

public interface Engine extends Worker {
    Engine setReader(Reader reader);

    Engine setWriter(Writer writer);

    Engine setContext(Context context);

    Engine addProcessor(RowDataProcessor processor);

    List<RowDataProcessor> getProcessors();

    void go();
}
