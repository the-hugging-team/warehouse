package com.the.hugging.team.utils;

import javafx.scene.control.TableView;

import java.util.ArrayList;

public class TableResizer {

    public static void setResizer(TableView<Object> table) {
        int columnsCount = table.getColumns().size();
        table.widthProperty().addListener(
                (observableValue, oldTableWidth, newTableWidth) ->
                {
                    double newColumnWidth = newTableWidth.doubleValue() / columnsCount;

                    for (int i = 0; i < columnsCount; i++) {
                        table.getColumns().get(i).setPrefWidth(newColumnWidth);
                    }
                });
    }

    public static void setCustomColumns(TableView<Object> table, ArrayList<Integer> columnIndex, ArrayList<Integer> columnWidth) {
        int totalColumnWidth = columnWidth.stream().mapToInt(a -> a).sum();
        for (int i = 0; i < columnIndex.size(); i++) {
            table.getColumns().get(columnIndex.get(i)).setPrefWidth(columnWidth.get(i));
        }
        int columnsCount = table.getColumns().size() - columnIndex.size();
        table.widthProperty().addListener(
                (observableValue, oldTableWidth, newTableWidth) ->
                {
                    double newColumnWidth = (newTableWidth.doubleValue() - totalColumnWidth) / columnsCount;
                    for (int i = 0; i < table.getColumns().size(); i++) {
                        if (columnIndex.contains(i)) continue;
                        table.getColumns().get(i).setPrefWidth(newColumnWidth);
                    }
                });
    }
}
