document.addEventListener('DOMContentLoaded', function() {
    if (!regularTestResults || !Array.isArray(regularTestResults)) {
        console.error('regularTestResults is not properly initialized');
        return;
    }

    const ctx = document.getElementById('regularTestChart').getContext('2d');

    // DTOから直接プロパティにアクセス
    const labels = regularTestResults.map(result =>
        result.testName || result.testPeriod || `Test ${result.regularTestResultId}`
    );

    // 5教科合計の平均点を計算する関数
    const calculateTotal5Average = (result) => {
        return (result.japaneseAverageScore || 0) +
               (result.mathAverageScore || 0) +
               (result.englishAverageScore || 0) +
               (result.scienceAverageScore || 0) +
               (result.socialAverageScore || 0);
    };

    // データの種類ごとに色を定義
    const dataTypeColors = {
        score: '#FF6384',      // 赤系: 自分の点数
        average: '#4BC0C0',    // 緑系: 平均点
        difference: '#36A2EB'  // 青系: 差分
    };

    // データセットを作成する関数
    const createDatasets = (subject) => {
        // 科目名の日本語マッピング
        const subjectNames = {
            'Total5': '5教科',
            'Japanese': '国語',
            'Math': '数学',
            'English': '英語',
            'Science': '理科',
            'Social': '社会'
        };

        const subjectName = subjectNames[subject];

        if (subject === 'Total5') {
            return [
                {
                    label: `${subjectName}合計点`,
                    data: regularTestResults.map(result => result.total5 || 0),
                    borderColor: dataTypeColors.score,
                    borderWidth: 2,
                    tension: 0.1,
                    fill: false,
                    yAxisID: 'y1'
                },
                {
                    label: `${subjectName}平均点`,
                    data: regularTestResults.map(result => calculateTotal5Average(result)),
                    borderColor: dataTypeColors.average,
                    borderWidth: 2,
                    tension: 0.1,
                    fill: false,
                    yAxisID: 'y1'
                },
                {
                    label: '平均点との差',
                    data: regularTestResults.map(result => {
                        const studentTotal = result.total5 || 0;
                        const averageTotal = calculateTotal5Average(result);
                        return studentTotal - averageTotal;
                    }),
                    borderColor: dataTypeColors.difference,
                    borderWidth: 2,
                    tension: 0.1,
                    fill: false,
                    yAxisID: 'y2'
                }
            ];
        } else {
            const subjectKey = subject.toLowerCase();
            const averageKey = `${subjectKey}AverageScore`;

            return [
                {
                    label: `${subjectName}の点数`,
                    data: regularTestResults.map(result => result[subjectKey] || 0),
                    borderColor: dataTypeColors.score,
                    borderWidth: 2,
                    tension: 0.1,
                    fill: false,
                    yAxisID: 'y1'
                },
                {
                    label: `${subjectName}の平均点`,
                    data: regularTestResults.map(result => result[averageKey] || 0),
                    borderColor: dataTypeColors.average,
                    borderWidth: 2,
                    tension: 0.1,
                    fill: false,
                    yAxisID: 'y1'
                },
                {
                    label: '平均点との差',
                    data: regularTestResults.map(result => {
                        const score = result[subjectKey] || 0;
                        const average = result[averageKey] || 0;
                        return score - average;
                    }),
                    borderColor: dataTypeColors.difference,
                    borderWidth: 2,
                    tension: 0.1,
                    fill: false,
                    yAxisID: 'y2'
                }
            ];
        }
    };

    // 初期表示する教科（5教科合計）
    const initialSubject = 'Total5';
    const datasets = createDatasets(initialSubject);

    // チャートの設定
    const chart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: labels,
            datasets: datasets
        },
        options: {
            responsive: true,
            interaction: {
                mode: 'index',
                intersect: false,
            },
            plugins: {
                title: {
                    display: true,
                    text: '定期テストの推移'
                },
                legend: {
                    position: 'bottom',
                    labels: {
                        usePointStyle: true,
                        padding: 20
                    }
                }
            },
            scales: {
                y1: {
                    type: 'linear',
                    display: true,
                    position: 'left',
                    min: 0,
                    max: 500,
                    title: {
                        display: true,
                        text: '点数'
                    }
                },
                y2: {
                    type: 'linear',
                    display: true,
                    position: 'right',
                    min: -100,
                    max: 100,
                    title: {
                        display: true,
                        text: '平均点との差'
                    },
                    grid: {
                        drawOnChartArea: false
                    }
                }
            }
        }
    });

    // 教科選択用のセレクトボックスを作成
    const subjects = [
        { value: 'Total5', text: '5教科合計' },
        { value: 'Japanese', text: '国語' },
        { value: 'Math', text: '数学' },
        { value: 'English', text: '英語' },
        { value: 'Science', text: '理科' },
        { value: 'Social', text: '社会' }
    ];
    const selectBox = document.createElement('select');
    selectBox.id = 'subjectSelector';
    selectBox.classList.add('form-select', 'mb-3');

    subjects.forEach(subject => {
        const option = document.createElement('option');
        option.value = subject.value;
        option.text = subject.text;
        selectBox.appendChild(option);
    });

    // セレクトボックスの変更イベント
    selectBox.addEventListener('change', function(e) {
        const newDatasets = createDatasets(e.target.value);
        chart.data.datasets = newDatasets;

        // スケールの調整
        if (e.target.value === 'Total5') {
            chart.options.scales.y1.max = 500;
            chart.options.scales.y2.min = -100;
            chart.options.scales.y2.max = 100;
        } else {
            chart.options.scales.y1.max = 100;
            chart.options.scales.y2.min = -30;
            chart.options.scales.y2.max = 30;
        }

        chart.update();
    });

    // チャートの前にセレクトボックスを挿入
    ctx.canvas.parentNode.insertBefore(selectBox, ctx.canvas);
});