package com.the.hugging.team.utils;

import javafx.scene.control.TableView;

public class TableResizer {

    public static void setResizer(TableView<Object> table)
    {
        int columnsCount = table.getColumns().size();
        table.widthProperty().addListener(
                (observableValue, oldTableWidth, newTableWidth) ->
                {
                    for (int i = 0; i < columnsCount; i++)
                    {
                        table.getColumns().get(i).setPrefWidth(newTableWidth.doubleValue()/columnsCount);
                    }
                });
    }



}
