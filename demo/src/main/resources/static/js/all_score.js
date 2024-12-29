const tdElements = document.querySelectorAll('td');

tdElements.forEach(elem => {
    changeColor(elem);
});

function changeColor(elem) {
    // textContent を使用して要素のテキスト内容を取得
    const content = elem.textContent;

    // includes() を使用して文字列検索
    if (content.includes('-')) {
        elem.classList.add('alert');
        elem.classList.add('alert-danger');
    } else if (content.includes('+')) {
        elem.classList.add('alert');
        elem.classList.add('alert-success');
    }
}




document.addEventListener('DOMContentLoaded', function() {
    const exportButton = document.getElementById('exportExcel');
    if (exportButton) {
        exportButton.addEventListener('click', exportToExcel);
    }
});

async function exportToExcel() {
    const workbook = new ExcelJS.Workbook();
    const sheet = workbook.addWorksheet('成績一覧');
    const studentName = document.querySelector('h1').textContent.replace('さんの成績一覧', '');
    let currentRow = 1;

    // ヘッダースタイル設定関数
    const setHeaderStyle = (row) => {
        row.font = { bold: true };
        row.fill = {
            type: 'pattern',
            pattern: 'solid',
            fgColor: { argb: 'FFE0E0E0' }
        };
    };

    // セクションヘッダー設定関数
    const setSectionHeader = (title, columnCount) => {
        sheet.mergeCells(currentRow, 1, currentRow, columnCount);
        sheet.getCell(currentRow, 1).value = title;
        sheet.getCell(currentRow, 1).font = { bold: true, size: 14 };
        sheet.getCell(currentRow, 1).fill = {
            type: 'pattern',
            pattern: 'solid',
            fgColor: { argb: 'FFD0D0D0' }
        };
        currentRow++;
    };

    // 定期テスト成績
    setSectionHeader('定期テスト成績', 12);

    const regularTestHeader = sheet.addRow([
        'タイトル (()内は平均との差)', '国語', '数学', '英語', '理科', '社会',
        '音楽', '美術', '技術', '体育', '5科目', '9科目'
    ]);
    setHeaderStyle(regularTestHeader);

    const regularTestTable = document.querySelector('.card:nth-child(1) table');
    const regularTestRows = Array.from(regularTestTable.querySelectorAll('tbody tr'));
    regularTestRows.forEach(row => {
        const rowData = Array.from(row.querySelectorAll('td')).map(td => td.textContent);
        sheet.addRow(rowData);
        currentRow++;
    });
    currentRow += 2;

    // その他のテスト成績
    setSectionHeader('その他のテスト成績', 12);

    const otherTestHeader = sheet.addRow([
        'タイトル', '国語', '数学', '英語', '理科', '社会',
        '音楽', '美術', '技術', '体育', '5科目', '9科目'
    ]);
    setHeaderStyle(otherTestHeader);

    const otherTestTable = document.querySelector('.card:nth-child(2) table');
    const otherTestRows = Array.from(otherTestTable.querySelectorAll('tbody tr'));
    otherTestRows.forEach(row => {
        const rowData = Array.from(row.querySelectorAll('td')).map(td => td.textContent);
        sheet.addRow(rowData);
        currentRow++;
    });
    currentRow += 2;

    // 内申成績
    setSectionHeader('内申成績', 14);

    const schoolRecordHeader = sheet.addRow([
        'タイトル', '国語', '数学', '英語', '理科', '社会',
        '音楽', '美術', '技術', '体育', '5科目', '9科目', '換算内申', '入試点'
    ]);
    setHeaderStyle(schoolRecordHeader);

    const schoolRecordTable = document.querySelector('.card:nth-child(3) table');
    const schoolRecordRows = Array.from(schoolRecordTable.querySelectorAll('tbody tr'));
    schoolRecordRows.forEach(row => {
        const rowData = Array.from(row.querySelectorAll('td')).map(td => td.textContent);
        sheet.addRow(rowData);
        currentRow++;
    });
    currentRow += 2;

    // 模試成績
    setSectionHeader('模試成績', 29);

    const mockTestHeader = sheet.addRow([
        '模試名', '実施日',
        '国語', '国語偏差値', '数学', '数学偏差値', '英語', '英語偏差値',
        '理科', '理科偏差値', '社会', '社会偏差値',
        '3科目', '3科目偏差値', '5科目', '5科目偏差値', '700点換算',
        '志望校1', '可能性1', '志望校2', '可能性2', '志望校3', '可能性3',
        '志望校4', '可能性4', '志望校5', '可能性5', '志望校6', '可能性6'
    ]);
    setHeaderStyle(mockTestHeader);

    const mockTestTable = document.querySelector('.table-mocktest');
    const mockTestRows = Array.from(mockTestTable.querySelectorAll('tbody tr'));
    mockTestRows.forEach(row => {
        const rowData = Array.from(row.querySelectorAll('td')).map(td => td.textContent);
        sheet.addRow(rowData);
    });

    // カラム幅の自動調整
    sheet.columns.forEach((column, index) => {
        if (index === 0) {
            column.width = 25; // タイトル列を広めに
        } else {
            column.width = 15;
        }
    });

    // Excelファイルとして保存
    const buffer = await workbook.xlsx.writeBuffer();
    const blob = new Blob([buffer], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = `${studentName}_成績一覧.xlsx`;
    a.click();
    window.URL.revokeObjectURL(url);
}