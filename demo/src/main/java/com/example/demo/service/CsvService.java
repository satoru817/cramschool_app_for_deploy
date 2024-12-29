package com.example.demo.service;

import com.example.demo.entity.OtherTestResult;
import com.example.demo.entity.RegularTestResult;
import com.example.demo.entity.SchoolRecordResult;
import com.example.demo.entity.TestResult;
import com.example.demo.repository.RegularTestResultRepository;
import com.example.demo.repository.RegularTestSetRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

// CSVダウンロード用のユーティリティクラスを作成

@Slf4j
@RequiredArgsConstructor
@Service
public class CsvService {
    private final RegularTestResultRepository regularTestResultRepository;
    private final RegularTestSetRepository regularTestSetRepository;

    private static final byte[] BOM = new byte[]{(byte)0xEF, (byte)0xBB, (byte)0xBF};
    private static final String CSV_HEADER = "生徒名,生徒番号,国語,数学,英語,理科,社会,音楽,美術,技術,体育";


    public void generateOtherTestExcel(List<OtherTestResult> results,String fileName, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String encodedFileName = URLEncoder.encode(fileName + ".xlsx", StandardCharsets.UTF_8.name());
        response.setHeader("Content-Disposition", "attachment;filename*=UTF-8''" + encodedFileName);

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("その他のテスト成績");

            CellStyle dataStyle = createDataStyle(workbook);

            CellStyle headerStyle = createHeaderStyle(workbook);



            // ヘッダー行の作成（"定期テスト成績"列を追加）
            String[] headers = {"その他のテスト成績", "生徒番号", "生徒名", "国語", "数学", "英語", "理科", "社会", "音楽", "美術", "技術", "体育"};
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
                sheet.setColumnWidth(i, 256 * 12);  // 列幅の設定
            }

            // データ行の作成
            setRow(results,sheet,dataStyle);


            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void generateRegularTestExcel(List<RegularTestResult> results, String fileName, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String encodedFileName = URLEncoder.encode(fileName + ".xlsx", StandardCharsets.UTF_8.name());
        response.setHeader("Content-Disposition", "attachment;filename*=UTF-8''" + encodedFileName);

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("定期テスト成績");

            CellStyle dataStyle = createDataStyle(workbook);

            CellStyle headerStyle = createHeaderStyle(workbook);



            // ヘッダー行の作成（"定期テスト成績"列を追加）
            String[] headers = {"定期テスト成績", "生徒番号", "生徒名", "国語", "数学", "英語", "理科", "社会", "音楽", "美術", "技術", "体育"};
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
                sheet.setColumnWidth(i, 256 * 12);  // 列幅の設定
            }

            // データ行の作成
            setRow(results,sheet,dataStyle);


            workbook.write(response.getOutputStream());
        }
    }
    public CellStyle createDataStyle(Workbook workbook){


        CellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setBorderTop(BorderStyle.THIN);
        dataStyle.setBorderBottom(BorderStyle.THIN);
        dataStyle.setBorderLeft(BorderStyle.THIN);
        dataStyle.setBorderRight(BorderStyle.THIN);
        return dataStyle;
    }

    public CellStyle createHeaderStyle(Workbook workbook){
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        return headerStyle;
    }

    private void setRow(List<? extends TestResult> results, Sheet sheet, CellStyle dataStyle) {
        int rowNum = 1;
        for (val result : results) {
            Row row = sheet.createRow(rowNum++);
            // "定期テスト成績"列は空白
            setCellValue(row, 0, "", dataStyle);
            setCellValue(row, 1, result.getStudent().getCode(), dataStyle);
            setCellValue(row, 2, result.getStudent().getName(), dataStyle);
            setCellValue(row, 3, result.getJapanese(), dataStyle);
            setCellValue(row, 4, result.getMath(), dataStyle);
            setCellValue(row, 5, result.getEnglish(), dataStyle);
            setCellValue(row, 6, result.getScience(), dataStyle);
            setCellValue(row, 7, result.getSocial(), dataStyle);
            setCellValue(row, 8, result.getMusic(), dataStyle);
            setCellValue(row, 9, result.getArt(), dataStyle);
            setCellValue(row, 10, result.getTech(), dataStyle);
            setCellValue(row, 11, result.getPe(), dataStyle);
        }
    }



    public void generateSchoolRecordExcel(List<SchoolRecordResult> results, String fileName, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String encodedFileName = URLEncoder.encode(fileName + ".xlsx", StandardCharsets.UTF_8.name());
        response.setHeader("Content-Disposition", "attachment;filename*=UTF-8''" + encodedFileName);

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("内申成績");

            // スタイルの設定
            CellStyle dataStyle = createDataStyle(workbook);

            CellStyle headerStyle = createHeaderStyle(workbook);

            // ヘッダー行の作成（"内申成績登録"列を追加）
            String[] headers = {"内申成績", "生徒番号", "生徒名", "国語", "数学", "英語", "理科", "社会", "音楽", "美術", "技術", "体育"};
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
                sheet.setColumnWidth(i, 256 * 12);  // 列幅の設定
            }

            // データ行の作成
            setRow(results,sheet,dataStyle);


            workbook.write(response.getOutputStream());
        }
    }

    private void setCellValue(Row row, int column, Object value, CellStyle style) {
        Cell cell = row.createCell(column);
        cell.setCellStyle(style);

        if (value == null) {
            return;
        }

        if (value instanceof Integer) {
            cell.setCellValue(((Integer) value).doubleValue());
        } else if (value instanceof Long) {  // Long型の処理を追加
            cell.setCellValue(((Long) value).doubleValue());
        } else if (value instanceof String) {
            cell.setCellValue((String) value);
        }
    }


    private String formatCsvLine(RegularTestResult result) {
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
                escapeCSV(result.getStudent().getName()),
                result.getStudent().getCode(),
                formatValue(result.getJapanese()),
                formatValue(result.getMath()),
                formatValue(result.getEnglish()),
                formatValue(result.getScience()),
                formatValue(result.getSocial()),
                formatValue(result.getMusic()),
                formatValue(result.getArt()),
                formatValue(result.getTech()),
                formatValue(result.getPe())
        );
    }



    // CSVの値をエスケープする補助メソッド
    private String escapeCSV(String value) {
        if (value == null) {
            return "";
        }
        // カンマを含む場合はダブルクォートで囲む
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }

    // null値を処理する補助メソッド
    private String formatValue(Integer value) {
        return value != null ? value.toString() : "";
    }

    private static final Map<String, BiConsumer<RegularTestResult, Integer>> SUBJECT_SETTERS = Map.of(
            "国語", RegularTestResult::setJapanese,
            "数学", RegularTestResult::setMath,
            "英語", RegularTestResult::setEnglish,
            "社会", RegularTestResult::setSocial,
            "理科", RegularTestResult::setScience,
            "音楽", RegularTestResult::setMusic,
            "美術", RegularTestResult::setArt,
            "技術", RegularTestResult::setTech,
            "体育", RegularTestResult::setPe
    );

    public String processRegularTestCsvUpload(MultipartFile file, Integer id, boolean isSetLevel) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("CSVファイルが選択されていません。");
        }

        try {
            // 結果の取得
            List<RegularTestResult> regularTestResults = isSetLevel ?
                    regularTestResultRepository.findAllByRegularTestSetIdExcludingAverageMan(id):
                    regularTestSetRepository.findAllRegularTestResultByRegularTestId(id);

            // 生徒コードでマップ化
            Map<Long, RegularTestResult> resultMap = createResultMap(regularTestResults);

            // CSVの処理
            processCSVFile(file, resultMap);

            // 保存
            regularTestResultRepository.saveAll(regularTestResults);

            return isSetLevel ?
                    "redirect:/regularTestResultChunkEdit/" + id :
                    "redirect:/regularTestResultEdit/" + id;

        } catch (Exception e) {
            log.error("CSV処理中にエラーが発生しました", e);
            return getRedirectUrl(id, isSetLevel);
        }
    }

    private Map<Long, RegularTestResult> createResultMap(List<RegularTestResult> results) {
        return results.stream()
                .filter(result -> result.getStudent().getCode() != null)
                .collect(Collectors.toMap(
                        result -> result.getStudent().getCode(),
                        result -> result
                ));
    }

    private void processCSVFile(MultipartFile file, Map<Long, RegularTestResult> resultMap) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withDelimiter(',')
                    .withQuote('"')
                    .withFirstRecordAsHeader()
                    .parse(reader);

            for (CSVRecord record : records) {
                processRecord(record, resultMap);
            }
        }
    }

    private void processRecord(CSVRecord record, Map<Long, RegularTestResult> resultMap) {
        logRecordDetails(record);
        String studentCode = record.get("生徒番号");
        RegularTestResult existingRecord = resultMap.get(Long.valueOf(studentCode));

        if (existingRecord != null) {
            updateTestResults(existingRecord, record);
        }
    }

    private void updateTestResults(RegularTestResult result, CSVRecord record) {
        SUBJECT_SETTERS.forEach((subjectName, setter) -> {
            Integer value = parseOrNull(record.get(subjectName));
            setter.accept(result, value);
        });
    }

    private void logRecordDetails(CSVRecord record) {
        record.toMap().forEach((header, value) ->
                log.info("Header:{}, Value:{}, is生徒番号:{}",
                        header, value, header.trim().equals("生徒番号")));
    }

    private Integer parseOrNull(String value) {
        return (value == null || value.trim().isEmpty()) ? null : Integer.parseInt(value.trim());
    }

    private String getRedirectUrl(Integer id, boolean isSetLevel) {
        return isSetLevel ?
                "redirect:/regularTestResultChunkEdit/" + id :
                "redirect:/regularTestResultEdit/" + id;
    }
}