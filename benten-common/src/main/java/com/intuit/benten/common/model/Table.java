package com.intuit.benten.common.model;

import com.intuit.benten.common.exceptions.TableDimensionsException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public class Table {

    private String title;
    private List<String> rowHeaders;
    private List<List<String>> rows;
    private List<String> columnHeaders;
    private int rowLength;
    private int columnLength;

    public Table(int rowLength,int columnLength){
        this.rowLength = rowLength;
        this.columnLength = columnLength;
        rowHeaders  = new ArrayList<String>();
        columnHeaders = new ArrayList<String>();
        rows = new ArrayList<List<String>>();
    }

    public List<String> getRowHeaders() {
        return rowHeaders;
    }

    public void setRowHeaders(List<String> rowHeaders) throws TableDimensionsException {
        if(rowHeaders==null || rowHeaders.size() != this.columnLength)
            throw new TableDimensionsException("Length of row header cannot be greater than the table column length length"+this.columnLength);
        this.rowHeaders = rowHeaders;
    }

    public void addRow(ArrayList<String> row) throws TableDimensionsException {
        if(row ==null || row.size() != this.columnLength)
            throw new TableDimensionsException("Length of row cannot be greater than the table row length"+this.rowLength);
        this.rows.add(row);
    }

    public List<String> getColumnHeaders() {
        return columnHeaders;
    }

    public void setColumnHeaders(List<String> columnHeaders) throws TableDimensionsException {
        if(columnHeaders ==null || columnHeaders.size()!=this.rowLength)
            throw new TableDimensionsException("Length of column header row cannot be greater than the table row length"+this.rowLength);
        this.columnHeaders = columnHeaders;
    }


    public List<List<String>> getRows() {
        return rows;
    }

    public void setRows(List<List<String>> rows) {
        this.rows = rows;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
