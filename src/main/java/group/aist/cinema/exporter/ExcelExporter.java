package group.aist.cinema.exporter;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Component
public class ExcelExporter<T> implements Exporter<T> {

    @Override
    public void export(List<T> data, String filePath, String fileName) {
        var dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm");
        var currentDateTime = LocalDateTime.now();
        final String fullPath = filePath + dateTimeFormatter.format(currentDateTime) + "_" + fileName;
        writeToExcel(data, fullPath);
        log.info("---------------");
        log.info("Data was successfully exported");
    }

    private void writeToExcel(List<T> data, String fullPath) {
        Path filePath = null;
        try {
            filePath = Files.createFile(Paths.get(fullPath));
        } catch (IOException e) {
            log.error("An error occured during creating file", e);
        }
        File file = Objects.requireNonNull(filePath).toFile();
        try (OutputStream fos = new FileOutputStream(file);
             XSSFWorkbook workbook = new XSSFWorkbook()) {
            String sheetName = data.get(0).getClass().getSimpleName();
            Sheet sheet = createOrRetrieveSheet(workbook, sheetName);
            List<String> fieldNames = getFieldNamesForClass(data.get(0).getClass());
            createColumnNames(sheet, fieldNames, true);
            fillData(fieldNames, data, sheet, true, null, null);
            workbook.write(fos);
            fos.flush();
        } catch (Exception e) {
            log.error("An error occured during exporting data to Excel", e);
        }
    }

    /**
     * Create a new Sheet
     *
     * @param workbook XSSFWorkbook
     * @param sheetName the name of sheet
     * @return Sheet
     */
    private static Sheet createOrRetrieveSheet(Workbook workbook, String sheetName) {
        if (workbook.getSheet(sheetName) != null) {
            return workbook.getSheet(sheetName);
        }
        return workbook.createSheet(WorkbookUtil.createSafeSheetName(sheetName));
    }

    /**
     * Create names for columns
     *
     * @param sheet {@link Sheet}
     * @param fieldNames List of field names
     */
    private static void createColumnNames(Sheet sheet, List<String> fieldNames, boolean isParent) {
        int columnCount = 0;
        Row row = sheet.createRow(0);
        Cell pkCell = row.createCell(columnCount++);
        pkCell.setCellValue("PK");
        if (!isParent) {
            Cell fkCell = row.createCell(columnCount++);
            fkCell.setCellValue("FK");
        }
        for (String fieldName : fieldNames) {
            Cell cell = row.createCell(columnCount++);
            cell.setCellValue(fieldName);
        }
    }

    /**
     * Fill sheet with data
     *
     * @param fieldNames List of field names
     * @param data List of data
     * @param sheet Sheet
     * @param isParent is sheet parent
     * @param parentUUID parent object UUID (FK). can't be null ifParent is true.
     * @param <T> generic type
     * @throws NoSuchMethodException {@link NoSuchMethodException}
     * @throws InvocationTargetException {@link InvocationTargetException}
     * @throws IllegalAccessException {@link IllegalAccessException}
     */
    @SuppressWarnings("unchecked")
    private static <T> void fillData(
            List<String> fieldNames,
            List<T> data,
            Sheet sheet,
            boolean isParent,
            String parentUUID,
            Sheet childSheet
    ) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        int columnCount;
        Row row;
        Class<? extends Object> clazz = data.get(0).getClass();
        for (T t : data) {
            columnCount = isParent ? 1 : 2;
            int lastRow= sheet.getLastRowNum();
            row = sheet.createRow(++lastRow);
            String uuid = UUID.randomUUID().toString();
            Cell pkCellValue = row.createCell(0);
            pkCellValue.setCellValue(uuid);
            if (!isParent) {
                Cell fkCellValue = row.createCell(1);
                fkCellValue.setCellValue(parentUUID);
            }
            for (String fieldName : fieldNames) {
                Cell cell = row.createCell(columnCount);
                Method method = null;
                try {
                    method = clazz.getMethod("get" + capitalize(fieldName));
                    System.out.println(method);
                } catch (NoSuchMethodException nme) {
                    method = clazz.getMethod("get" + fieldName);
                }
                Object value = method.invoke(t, (Object[]) null);
                if (value != null) {
                    if (value instanceof String) {
                        cell.setCellValue((String) value);
                    } else if (value instanceof Long) {
                        cell.setCellValue((Long) value);
                    } else if (value instanceof Integer) {
                        cell.setCellValue((Integer) value);
                    } else if (value instanceof Double) {
                        cell.setCellValue((Double) value);
                    } else if (value instanceof LocalDate) {
                        cell.setCellValue(((LocalDate) value).toString());
                    }else if (value instanceof Short) {
                        cell.setCellValue(((Short) value).toString());
                    }
                    else if (value instanceof Boolean) {
                        cell.setCellValue(value.equals(Boolean.FALSE) ? "Xeyr" : "Bəli");
                    } else if (value instanceof List) {
                        List<T> childData = (List<T>) value;
                        fillDataToCollection(uuid, cell, sheet, childSheet, childData);
                    } else if (value instanceof Set) {
                        Set<T> childDataSet = (Set<T>) value;
                        List<T> childData = new ArrayList<>(childDataSet);
                        fillDataToCollection(uuid, cell, sheet, childSheet, childData);
                    }
                }
                columnCount++;
            }
        }
    }

    /**
     * Field collection of data
     *
     * @param uuid
     * @param cell
     * @param sheet
     * @param childSheet
     * @param childData
     * @param <T>
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private static <T> void fillDataToCollection(
            String uuid,
            Cell cell,
            Sheet sheet,
            Sheet childSheet,
            List<T> childData
    ) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String sheetName = childData.get(0).getClass().getSimpleName();
        List<String> childFieldNames = getFieldNamesForClass(childData.get(0).getClass());
        if (childSheet == null) {
            cell.setCellValue(sheetName);
            Sheet anotherSheet = createOrRetrieveSheet(sheet.getWorkbook(), sheetName);
            createColumnNames(anotherSheet, childFieldNames, false);
            fillData(childFieldNames, childData, anotherSheet, false, uuid, anotherSheet);
        } else {
            fillData(childFieldNames, childData, childSheet, false, uuid, childSheet);
        }
    }

    /**
     * Get list of field names for specified class
     *
     * @param clazz the class to get the names for
     * @return List of field names
     */
    private static List<String> getFieldNamesForClass(Class<?> clazz) {
        List<String> fieldNames = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            fieldNames.add(field.getName());
        }
        return fieldNames;
    }

    /**
     * Capitalize field name
     *
     * @param fieldName
     * @return String
     */
    private static String capitalize(String fieldName) {
        if (fieldName.length() == 0) return fieldName;
        return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

}