package person.nightrunner.rdp;

import java.util.List;
import java.util.Map;

public interface Reader extends Worker {

    List<Map<Column, String>> read();

    List<Column> getColumns();

    default int getTitleRowCount() {
        return 1;
    }
}
