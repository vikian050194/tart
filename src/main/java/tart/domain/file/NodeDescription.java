package tart.domain.file;

import java.util.List;

public interface NodeDescription {

    public String getName();

    public List<String> getDirs();

    public List<String> getFullName();

}
