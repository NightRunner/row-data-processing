package person.nightrunner.rdp;

public interface RowDataProcessor extends Worker {
    void process();

    <T extends Context> void setContext(T context);
}
