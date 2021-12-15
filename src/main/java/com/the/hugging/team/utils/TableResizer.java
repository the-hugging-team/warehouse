package com.the.hugging.team.utils;

import javafx.scene.control.TableView;

import java.util.List;

public class TableResizer {

    public static void setDefault(TableView<?> table) {
        int columnsCount = table.getColumns().size();
        table.widthProperty().addListener(
                (observableValue, oldTableWidth, newTableWidth) ->
                {
                    double newColumnWidth = newTableWidth.doubleValue() / columnsCount;

                    for (int i = 0; i < columnsCount; i++) {
                        table.getColumns().get(i).setPrefWidth(newColumnWidth);
                        table.getColumns().get(i).setReorderable(false);
                        table.getColumns().get(i).setResizable(false);
                    }
                });
    }

    public static void setCustomColumns(TableView<?> table, List<Integer> columnIndex, List<Integer> columnWidth) {
        int totalColumnWidth = columnWidth.stream().mapToInt(a -> a).sum();
        for (int i = 0; i < columnIndex.size(); i++) {
            table.getColumns().get(columnIndex.get(i)).setPrefWidth(columnWidth.get(i));
            table.getColumns().get(i).setReorderable(false);
        }
        int columnsCount = table.getColumns().size() - columnIndex.size();
        table.widthProperty().addListener(
                (observableValue, oldTableWidth, newTableWidth) ->
                {
                    double newColumnWidth = (newTableWidth.doubleValue() - totalColumnWidth) / columnsCount;
                    for (int i = 0; i < table.getColumns().size(); i++) {
                        if (columnIndex.contains(i)) continue;
                        table.getColumns().get(i).setPrefWidth(newColumnWidth);
                        table.getColumns().get(i).setReorderable(false);
                        table.getColumns().get(i).setResizable(false);
                    }
                });
    }
}
