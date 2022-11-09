package person.nightrunner.rdp;

public interface Column {
    String getLabel();

    String getInternalKey();

    Type getType();

    Integer getIndex();

    enum Type {
        Date, String
    }
}
