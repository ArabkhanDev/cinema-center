package group.aist.cinema.exporter;

import java.util.List;

public interface Exporter<T> {

    void export(List<T> data, String filePath, String fileName);
}