package com.pdrw.pdrw.bestmebelru.service.impl;

import com.pdrw.pdrw.bestmebelru.model.BestmebelRu;
import com.pdrw.pdrw.bestmebelru.service.BestmebelRuService;
import com.pdrw.pdrw.bestmebelru.service.ExcelBestmebelRuService;
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
public class ExcelBestsellerRuServiceImpl implements ExcelBestmebelRuService {

    private final BestmebelRuService bestmebelRuService;

    public ExcelBestsellerRuServiceImpl(BestmebelRuService bestmebelRuService) {
        this.bestmebelRuService = bestmebelRuService;
    }


    @Override
    public Path getExcelFile() {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("BestmebelRu");
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

        List<BestmebelRu> bestmebelRuList = bestmebelRuService.findAll();

        CellStyle cellStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        cellStyle.setWrapText(false);
        CellStyle cellStyleDate = workbook.createCellStyle();
        cellStyleDate.setWrapText(false);
        cellStyleDate.setDataFormat(createHelper.createDataFormat().getFormat("dd-mm-yyyy h:mm"));


        for (int i = 0; i < bestmebelRuList.size(); i++) {
            Row rowCon = sheet.createRow(i + 1);
            Cell cell = rowCon.createCell(0);
            cell.setCellValue(bestmebelRuList.get(i).getArticle());
            cell.setCellStyle(cellStyle);

            cell = rowCon.createCell(1);
            cell.setCellValue(bestmebelRuList.get(i).getName());
            cell.setCellStyle(cellStyle);

            cell = rowCon.createCell(2);
            cell.setCellValue(bestmebelRuList.get(i).getImage());
            cell.setCellStyle(cellStyle);

            cell = rowCon.createCell(3);
            cell.setCellValue(bestmebelRuList.get(i).getPriceNew().doubleValue());
            cell.setCellStyle(cellStyle);

            cell = rowCon.createCell(4);
            cell.setCellValue(bestmebelRuList.get(i).getPriceOld().doubleValue());
            cell.setCellStyle(cellStyle);

            cell = rowCon.createCell(5);
            cell.setCellValue(bestmebelRuList.get(i).getDiscount().doubleValue());
            cell.setCellStyle(cellStyle);

            cell = rowCon.createCell(6);
            cell.setCellValue(bestmebelRuList.get(i).getCreateDate());
            cell.setCellStyle(cellStyleDate);

            cell = rowCon.createCell(7);
            cell.setCellValue(bestmebelRuList.get(i).getDateUpdate());
            cell.setCellStyle(cellStyleDate);

//            cell = rowCon.createCell(8);
//            cell.setCellValue(bestmebelRuList.get(i).getType());
//            cell.setCellStyle(cellStyle);
//
//            cell = rowCon.createCell(9);
//            cell.setCellValue(bestmebelRuList.get(i).getLength());
//            cell.setCellStyle(cellStyle);
//
//            cell = rowCon.createCell(10);
//            cell.setCellValue(bestmebelRuList.get(i).getWidth());
//            cell.setCellStyle(cellStyle);
//
//            cell = rowCon.createCell(11);
//            cell.setCellValue(bestmebelRuList.get(i).getHeight());
//            cell.setCellStyle(cellStyle);
//
//            cell = rowCon.createCell(12);
//            cell.setCellValue(bestmebelRuList.get(i).getWeight());
//            cell.setCellStyle(cellStyle);
//
//            cell = rowCon.createCell(13);
//            cell.setCellValue(bestmebelRuList.get(i).getVolume());
//            cell.setCellStyle(cellStyle);

            cell = rowCon.createCell(14);
            cell.setCellValue(bestmebelRuList.get(i).getActual());
            cell.setCellStyle(cellStyle);

            cell = rowCon.createCell(15);
            cell.setCellValue(bestmebelRuList.get(i).getLink());
            cell.setCellStyle(cellStyle);
        }

        File currentDir = new File(".");
        String path = currentDir.getAbsolutePath();
        String fileLocation = path.substring(0, path.length() - 1) + "Bestmebel-ru.xlsx";

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
