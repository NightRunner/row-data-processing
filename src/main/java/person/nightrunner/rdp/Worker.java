package person.nightrunner.rdp;

import person.nightrunner.rdp.impl.ColumnFlyweightFactory;

public interface Worker {
    void setColumnFactory(ColumnFactory columnFactory);
}
