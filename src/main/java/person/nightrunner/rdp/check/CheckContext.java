package person.nightrunner.rdp.check;

import person.nightrunner.rdp.Context;

import java.util.List;

public interface CheckContext extends Context {
    void addErrorMessage(String message);
    List<String> getErrorMessages();
}
