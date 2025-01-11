package com.pdrw.pdrw.pinskdrevby.service.impl;

import com.pdrw.pdrw.pinskdrevby.model.PinskdrevBy;
import com.pdrw.pdrw.pinskdrevby.service.ExcelPinskdrevByService;
import com.pdrw.pdrw.pinskdrevby.service.PinskdrevByService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ExcelPinskdrevByServiceImpl implements ExcelPinskdrevByService {

    private final PinskdrevByService pinskdrevByService;

    public ExcelPinskdrevByServiceImpl(PinskdrevByService pinskdrevByService) {
        this.pinskdrevByService = pinskdrevByService;
    }


    @Override
    public Path getExcelFile() {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("PinskdrevRu");
        Row header = sheet.createRow(0);
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("Article");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Name");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue("Image");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(3);
        headerCell.setCellValue("Price new");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(4);
        headerCell.setCellValue("Price old");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(5);
        headerCell.setCellValue("Discount");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(6);
        headerCell.setCellValue("Date create");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(7);
        headerCell.setCellValue("Date update");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(8);
        headerCell.setCellValue("Type");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(9);
        headerCell.setCellValue("Length");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(10);
        headerCell.setCellValue("Width");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(11);
        headerCell.setCellValue("Height");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(12);
        headerCell.setCellValue("Weight");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(13);
        headerCell.setCellValue("Volume");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(14);
        headerCell.setCellValue("Actual");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(15);
        headerCell.setCellValue("Link");
        headerCell.setCellStyle(headerStyle);

        List<PinskdrevBy> pinskdrevByList = pinskdrevByService.findAll();

        CellStyle cellStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        cellStyle.setWrapText(false);
        CellStyle cellStyleDate = workbook.createCellStyle();
        cellStyleDate.setWrapText(false);
        cellStyleDate.setDataFormat(createHelper.createDataFormat().getFormat("dd-mm-yyyy h:mm"));


        for (int i = 0; i < pinskdrevByList.size(); i++) {
            Row rowCon = sheet.createRow(i + 1);
            Cell cell = rowCon.createCell(0);
            cell.setCellValue(pinskdrevByList.get(i).getArticle());
            cell.setCellStyle(cellStyle);

            cell = rowCon.createCell(1);
            cell.setCellValue(pinskdrevByList.get(i).getName());
            cell.setCellStyle(cellStyle);

            cell = rowCon.createCell(2);
            cell.setCellValue(pinskdrevByList.get(i).getImage());
            cell.setCellStyle(cellStyle);

            cell = rowCon.createCell(3);
            cell.setCellValue(pinskdrevByList.get(i).getPriceNew().doubleValue());
            cell.setCellStyle(cellStyle);

            cell = rowCon.createCell(4);
            cell.setCellValue(pinskdrevByList.get(i).getPriceOld().doubleValue());
            cell.setCellStyle(cellStyle);

            cell = rowCon.createCell(5);
            cell.setCellValue(pinskdrevByList.get(i).getDiscount().doubleValue());
            cell.setCellStyle(cellStyle);

            cell = rowCon.createCell(6);
            cell.setCellValue(pinskdrevByList.get(i).getCreateDate());
            cell.setCellStyle(cellStyleDate);

            cell = rowCon.createCell(7);
            cell.setCellValue(pinskdrevByList.get(i).getDateUpdate());
            cell.setCellStyle(cellStyleDate);

            cell = rowCon.createCell(8);
            cell.setCellValue(pinskdrevByList.get(i).getType());
            cell.setCellStyle(cellStyle);

            cell = rowCon.createCell(9);
            cell.setCellValue(pinskdrevByList.get(i).getLength());
            cell.setCellStyle(cellStyle);

            cell = rowCon.createCell(10);
            cell.setCellValue(pinskdrevByList.get(i).getWidth());
            cell.setCellStyle(cellStyle);

            cell = rowCon.createCell(11);
            cell.setCellValue(pinskdrevByList.get(i).getHeight());
            cell.setCellStyle(cellStyle);

            cell = rowCon.createCell(12);
            cell.setCellValue(pinskdrevByList.get(i).getWeight());
            cell.setCellStyle(cellStyle);

            cell = rowCon.createCell(13);
            cell.setCellValue(pinskdrevByList.get(i).getVolume());
            cell.setCellStyle(cellStyle);

            cell = rowCon.createCell(14);
            cell.setCellValue(pinskdrevByList.get(i).getActual());
            cell.setCellStyle(cellStyle);

            cell = rowCon.createCell(15);
            cell.setCellValue(pinskdrevByList.get(i).getLink());
            cell.setCellStyle(cellStyle);
        }

        File currentDir = new File(".");
        String path = currentDir.getAbsolutePath();
        String fileLocation = path.substring(0, path.length() - 1) + "Pinskdrev-ru.xlsx";

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileLocation);
            workbook.write(fileOutputStream);
            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Paths.get(fileLocation);

    }
}
