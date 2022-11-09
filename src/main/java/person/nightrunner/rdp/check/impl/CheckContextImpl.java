package person.nightrunner.rdp.check.impl;

import person.nightrunner.rdp.check.CheckContext;
import person.nightrunner.rdp.impl.ContextImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckContextImpl extends ContextImpl implements CheckContext {

    private final Map<String, List<String>> map = new HashMap<>();

    @Override
    public List<String> getErrorMessages() {
        return map.get(currentRowKey);
    }

    @Override
    public void addErrorMessage(String message) {
        List<String> list = map.getOrDefault(currentRowKey, new ArrayList<>());
        list.add(message);
        map.put(currentRowKey, list);
    }

}
