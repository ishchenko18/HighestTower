package com.kpi.course.work.model.writer;

import com.kpi.course.work.model.figures.Parallelepiped;
import com.kpi.course.work.utils.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class ExcelWriter {

    private static String[] columns = {"Назва", "Висота", "Довжина", "Ширина"};

    private CommonUtils commonUtils;

    public ExcelWriter() throws Exception {
        commonUtils = new CommonUtils();
    }

    public void writeDataToExcel(List<Parallelepiped> inputList, List<Parallelepiped> outputList) {
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFFont headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        fillSheet(inputList, workbook.createSheet(commonUtils.getProperty("sheet.name.input")), headerCellStyle);
        fillSheet(outputList, workbook.createSheet(commonUtils.getProperty("sheet.name.output")), headerCellStyle);

        writeDataToFile(workbook);
    }

    private void fillSheet(List<Parallelepiped> data, XSSFSheet sheet, CellStyle cellStyle) {
        int rowNum = 0;

        Row header = sheet.createRow(rowNum++);

        for (int i = 0; i < columns.length; i++) {
            Cell cell = header.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(cellStyle);
        }

        for (Parallelepiped p : data) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(p.getName());
            row.createCell(1).setCellValue(p.getHeight());
            row.createCell(2).setCellValue(p.getBase().getLength());
            row.createCell(3).setCellValue(p.getBase().getWidth());
        }

        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private void writeDataToFile(Workbook workbook) {

        String fileName = commonUtils.getProperty("output.filename");

        try (OutputStream outputStream = new FileOutputStream(new File(fileName))) {
            workbook.write(outputStream);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}