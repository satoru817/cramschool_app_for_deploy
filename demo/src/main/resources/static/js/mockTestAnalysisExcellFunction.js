function exportToExcel() {
    const wb = XLSX.utils.book_new();
    const tables = document.getElementsByClassName('analysis-table');

    // データの配列を作成
    let allData = [];
    const COLUMNS_PER_SUBJECT = 4; // 教科ごとの列数（クラス名、担当教師、平均点、偏差値）

    // 各教科のデータを横に並べて配置
    Array.from(tables).forEach((table, tableIndex) => {
        const subject = table.closest('.card').querySelector('.card-title').textContent.trim();
        const startCol = tableIndex * (COLUMNS_PER_SUBJECT + 1); // 教科間に1列空ける

        // 教科名を配置
        if (!allData[0]) allData[0] = [];
        allData[0][startCol] = subject;

        // ヘッダー行を配置
        if (!allData[1]) allData[1] = [];
        allData[1][startCol] = 'クラス';
        allData[1][startCol + 1] = '担当教師';
        allData[1][startCol + 2] = '平均点';
        allData[1][startCol + 3] = '偏差値';

        // データ行を配置
        const rows = table.getElementsByTagName('tr');
        Array.from(rows).forEach((row, rowIndex) => {
            if (rowIndex === 0) return; // ヘッダー行をスキップ

            if (!allData[rowIndex + 1]) allData[rowIndex + 1] = [];

            const cells = row.getElementsByTagName('td');
            for (let i = 0; i < cells.length; i++) {
                // 数値データの場合は数値として変換
                const cellValue = cells[i].textContent.trim();
                const numValue = parseFloat(cellValue);
                allData[rowIndex + 1][startCol + i] = isNaN(numValue) ? cellValue : numValue;
            }
        });
    });

    // 空の要素を空文字列で埋める
    const totalCols = Array.from(tables).length * (COLUMNS_PER_SUBJECT + 1);
    allData.forEach(row => {
        for (let i = 0; i < totalCols; i++) {
            if (!row[i]) row[i] = '';
        }
    });

    // ワークシートを作成
    const ws = XLSX.utils.aoa_to_sheet(allData);

    // スタイル設定
    ws['!merges'] = [];
    // 各教科名のセルを結合
    Array.from(tables).forEach((_, index) => {
        const startCol = index * (COLUMNS_PER_SUBJECT + 1);
        ws['!merges'].push({
            s: {r: 0, c: startCol},
            e: {r: 0, c: startCol + COLUMNS_PER_SUBJECT - 1}
        });
    });

    // 列幅の設定
    const colWidths = [];
    Array.from(tables).forEach(() => {
        colWidths.push(
            { wch: 8 },  // クラス
            { wch: 12 }, // 担当教師
            { wch: 8 },  // 平均点
            { wch: 8 },  // 偏差値
            { wch: 2 }   // 空白列
        );
    });
    ws['!cols'] = colWidths;

    // セルスタイルの設定
    const range = XLSX.utils.decode_range(ws['!ref']);
    for (let R = range.s.r; R <= range.e.r; R++) {
        for (let C = range.s.c; C <= range.e.c; C++) {
            const cellRef = XLSX.utils.encode_cell({r: R, c: C});
            if (!ws[cellRef]) continue;

            // セルのスタイル設定
            if (!ws[cellRef].s) ws[cellRef].s = {};

            // ヘッダー行（教科名と列名）のスタイル
            if (R <= 1) {
                ws[cellRef].s = {
                    font: { bold: true },
                    alignment: { horizontal: 'center', vertical: 'center' },
                    fill: { fgColor: { rgb: "EEEEEE" } }
                };
            }
            // データ行のスタイル
            else {
                const colIndex = C % (COLUMNS_PER_SUBJECT + 1);
                // 数値データ（平均点と偏差値）は右揃え
                if (colIndex === 2 || colIndex === 3) {
                    ws[cellRef].s.alignment = { horizontal: 'right' };
                }
            }
        }
    }

    // シートをブックに追加
    XLSX.utils.book_append_sheet(wb, ws, "模試クラス別平均点");

    // ファイル名を設定
    const testName = document.getElementById('testName').textContent;
    const date = new Date();
    const fileName = `${testName}_${date.getFullYear()}${(date.getMonth()+1).toString().padStart(2, '0')}${date.getDate().toString().padStart(2, '0')}.xlsx`;

    // エクセルファイルとしてダウンロード
    XLSX.writeFile(wb, fileName);
}