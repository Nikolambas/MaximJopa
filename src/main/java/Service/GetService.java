package Service;


import Clases.Bank;
import lombok.Data;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;


@Data
public class GetService {

    private static final String FILE_NAME = "Кредиторы.xlsx";
    public FileInputStream fileInputStream;
    public XSSFWorkbook hssfWorkbook;

    public FileOutputStream fileOutputStream;
    public Map<Integer, Bank> bankList;

    public final Path path = Path.of("C:").resolve(".idea").resolve("DirToGet").resolve(FILE_NAME);


    public void init() {
        try {
            this.fileInputStream = new FileInputStream(path.toFile());
            this.hssfWorkbook = new XSSFWorkbook(fileInputStream);
            initList();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void initList() {
        bankList = new HashMap<>();
        Bank bank1 = new Bank("ПАО «СБЕРБАНК»", "117312, г. Москва, ул. Вавилова, д. 19. ",
                "7707083893", "1027700132195");
        Bank bank2 = new Bank("ПАО «СОВКОМБАНК»", "156000, Костромская область, город Кострома, пр-кт Текстильщиков, д. 46",
                "4401116480", "1144400000425");
        Bank bank3 = new Bank("ООО «ХКФ БАНК»","125124, г. Москва, ул. Правды, д. 8, к. 1",
                "7735057951","1027700280937");
        Bank bank4 = new Bank("АО «ПОЧТА БАНК»", "107061, г. Москва, Преображенская пл., д. 8",
                "3232005484","1023200000010");
        Bank bank5 = new Bank("ПАО  «ЛЕВОБЕРЕЖНЫЙ БАНК»","630102, г. Новосибирск, ул. Кирова, д. 48.",
                "5404154492","1025400000010");

        bankList.put(bank1.hashCode(), bank1);
        bankList.put(bank2.hashCode(), bank2);
        bankList.put(bank3.hashCode(), bank3);
        bankList.put(bank4.hashCode(), bank4);
        bankList.put(bank5.hashCode(), bank5);


    }

    public GetService() {
        init();
    }


    public void getCreditorBank() {
        List<Bank> getBank = new ArrayList<>();
        XSSFSheet sheetRead = hssfWorkbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheetRead.iterator();
        rowIterator.next();
        while (rowIterator.hasNext()) {
            Iterator<Cell> cellIterator = rowIterator.next().cellIterator();
            cellIterator.next();
            Cell cell = cellIterator.next();
            Bank b = new Bank(cell.getStringCellValue());
            if (bankList.containsValue(b)&&!getBank.contains(b)){
                getBank.add(bankList.get(b.hashCode()));
            }
        }

        try {
            fileInputStream.close();
            Path pathOut = Path.of("C:").resolve(".idea").resolve("DirToPut").resolve("Банки.xlsx");
            fileOutputStream = new FileOutputStream(pathOut.toFile().toString(), true);
            hssfWorkbook = new XSSFWorkbook();
            XSSFSheet sheet = hssfWorkbook.createSheet();
            Cell cell;
            int rownum = 0;
            Row row = sheet.createRow(rownum);

            cell = row.createCell(0, 1);
            cell.setCellValue("Банк");
            cell = row.createCell(1, 1);
            cell.setCellValue("Адрес");
            cell = row.createCell(2, 1);
            cell.setCellValue("ИНН");
            cell = row.createCell(3, 1);
            cell.setCellValue("ОГРН");

            for (Bank bank : getBank) {
                rownum++;
                row = sheet.createRow(rownum);
                cell = row.createCell(0,1);
                cell.setCellValue(bank.getName());
                cell = row.createCell(1, 1);
                cell.setCellValue(bank.getAddress());
                cell = row.createCell(2, 1);
                cell.setCellValue(bank.getINN());
                cell = row.createCell(3, 1);
                cell.setCellValue(bank.getOGRN());

            }
            hssfWorkbook.write(fileOutputStream);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
