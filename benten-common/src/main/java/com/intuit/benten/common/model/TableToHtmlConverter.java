package com.intuit.benten.common.model;

import java.util.List;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public class TableToHtmlConverter {

    public static String convertTableToHtmlTable(Table table){

        String htmlTable = "<h3 style=\"color:blue\">" + table.getTitle() + "</h3>";
        htmlTable += "<table \"style=border-spacing: 10px;\">";
        for (int counter=0; counter < table.getRowHeaders().size();counter++) {
            boolean isFirst = (counter==0) ;
            htmlTable+=rowHeader(table.getRowHeaders().get(counter),isFirst);
        }

        for(int counter=0 ; counter< table.getRows().size();counter++){
            List<String> currentRow = table.getRows().get(counter);
            htmlTable+="<tr>";

            for(int cellCounter =0; cellCounter<currentRow.size();cellCounter++){
                boolean isFirst = (cellCounter==0) ;
                htmlTable+=rowElement(currentRow.get(cellCounter),counter,isFirst);
            }
        }
        htmlTable+="</table>";
        return htmlTable;
    }

    private static String rowHeader(String text,boolean isFirst){
        String style = "\"border: 1px solid #000000; padding: 8px;font-family: Trebuchet MS, Arial, Helvetica, sans-serif;";
        style += isFirst ? "\"" : "border-left: none;\"";
        String rowHeader = "<th style="
                +style + ">"
                + text + "</th>";
        return rowHeader;
    }

    private static String rowElement(String text,int index,boolean isFirst){
        String style = "\"border: 1px solid #000000; padding: 8px;border-top: none;";
        style += isFirst ? "" : "border-left: none;";
        style += (index%2 == 0) ? "background-color: #f2f2f2;\"" : "\"";
        String rowElement = "<td style="
                +style + ">"
                + text + "</td>";
        return rowElement;
    }
}
