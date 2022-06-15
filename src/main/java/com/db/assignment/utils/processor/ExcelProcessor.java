package com.db.assignment.utils.processor;

import com.db.assignment.domain.Nomenclature;
import com.db.assignment.exception.AssignmentException;
import com.sun.istack.NotNull;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelProcessor {
    public static List<Nomenclature> excelToList(@NotNull InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            List<Nomenclature> dataList = new ArrayList<>();
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Nomenclature nomenclature = new Nomenclature();
                XSSFCell currentCell;
                for(int i=0; i<currentRow.getLastCellNum(); i++) {
                    currentCell = (XSSFCell) currentRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    switch (i) {
                        case 0:
                            nomenclature.setOrderId((long) currentCell.getNumericCellValue());
                            break;
                        case 1:
                            nomenclature.setLevel((int)currentCell.getNumericCellValue());
                            break;
                        case 2:
                            nomenclature.setCode(getValue(currentCell));
                            break;
                        case 3:
                            nomenclature.setParent(getValue(currentCell));
                            break;
                        case 4:
                            nomenclature.setDescription(getValue(currentCell));
                            break;
                        case 5:
                            nomenclature.setInclusion(getValue(currentCell));
                            break;
                        case 6:
                            nomenclature.setSecondaryInclusion(getValue(currentCell));
                            break;
                        case 7:
                            nomenclature.setRulings(getValue(currentCell));
                            break;
                        case 8:
                            nomenclature.setExclusion(getValue(currentCell));
                            break;
                        case 9:
                            nomenclature.setReference(getValue(currentCell));
                            break;
                        default:
                            break;
                    }
                }
                dataList.add(nomenclature);
            }
            workbook.close();
            return dataList;
        } catch (IOException e) {
            throw new AssignmentException("fail to parse Excel file: " + e.getMessage());
        }
    }

    private static String getValue(Cell currentCell) {
        if(CellType.STRING == currentCell.getCellType()){
            return currentCell.getStringCellValue();
        }else if(CellType.NUMERIC == currentCell.getCellType()) {
            return String.valueOf(currentCell.getNumericCellValue());
        } else
        return null;
    }
}
