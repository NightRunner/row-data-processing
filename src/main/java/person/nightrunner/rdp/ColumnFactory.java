package person.nightrunner.rdp;

public interface ColumnFactory {
    Column[] getByLabel(String... labels);

    Column getByLabel(String label);

    Column getByLetter(String letter);

    Column getByIndexAndLabel(Integer columnIndex, String label);

    Column getByIndex(Integer columnIndex);
}
