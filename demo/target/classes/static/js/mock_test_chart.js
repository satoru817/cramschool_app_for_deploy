document.addEventListener('DOMContentLoaded', function() {
    if (!mockTestResults || !Array.isArray(mockTestResults)) {
        console.error('mockTestResults is not properly initialized');
        return;
    }

    const ctx = document.getElementById('mockTestChart').getContext('2d');

    // データの種類ごとに色を定義
    const dataTypeColors = {
        total5: '#FF6384',     // 赤系: 5科
        total3: '#4BC0C0',     // 緑系: 3科
        subjects: {
            japanese: '#36A2EB',  // 青系: 国語
            math: '#FFCE56',      // 黄系: 数学
            english: '#9966FF',   // 紫系: 英語
            science: '#FF9F40',   // オレンジ系: 理科
            social: '#4BC0C0'     // 緑系: 社会
        }
    };

    // 科目名の日本語マッピング
    const subjectNames = {
        total5: '5科目合計',
        total3: '3科目合計',
        japanese: '国語',
        math: '数学',
        english: '英語',
        science: '理科',
        social: '社会'
    };

    // グラフデータの作成
    const datasets = [
        {
            label: '5科目合計',
            data: mockTestResults.map(result => result.total5Ss || 0),
            borderColor: dataTypeColors.total5,
            borderWidth: 2,
            tension: 0.1,
            fill: false
        },
        {
            label: '3科目合計',
            data: mockTestResults.map(result => result.total3Ss || 0),
            borderColor: dataTypeColors.total3,
            borderWidth: 2,
            tension: 0.1,
            fill: false
        },
        {
            label: '国語',
            data: mockTestResults.map(result => result.japaneseSs || 0),
            borderColor: dataTypeColors.subjects.japanese,
            borderWidth: 2,
            tension: 0.1,
            fill: false,
            hidden: true
        },
        {
            label: '数学',
            data: mockTestResults.map(result => result.mathSs || 0),
            borderColor: dataTypeColors.subjects.math,
            borderWidth: 2,
            tension: 0.1,
            fill: false,
            hidden: true
        },
        {
            label: '英語',
            data: mockTestResults.map(result => result.englishSs || 0),
            borderColor: dataTypeColors.subjects.english,
            borderWidth: 2,
            tension: 0.1,
            fill: false,
            hidden: true
        },
        {
            label: '理科',
            data: mockTestResults.map(result => result.scienceSs || 0),
            borderColor: dataTypeColors.subjects.science,
            borderWidth: 2,
            tension: 0.1,
            fill: false,
            hidden: true
        },
        {
            label: '社会',
            data: mockTestResults.map(result => result.socialSs || 0),
            borderColor: dataTypeColors.subjects.social,
            borderWidth: 2,
            tension: 0.1,
            fill: false,
            hidden: true
        }
    ];

    // チャートの設定
    const chart = new Chart(ctx, {
        type: 'line',
        data: {
            // DTOの新しい構造に合わせてラベルを設定
            labels: mockTestResults.map(result => result.date),
            datasets: datasets
        },
        options: {
            responsive: true,
            maintainAspectRatio: true,
            aspectRatio: 2,
            plugins: {
                title: {
                    display: true,
                    text: '模試偏差値の推移'
                },
                legend: {
                    position: 'bottom',
                    labels: {
                        usePointStyle: true,
                        padding: 20,
                        boxWidth: 10
                    }
                }
            },
            scales: {
                y: {
                    min: 30,
                    max: 80,
                    title: {
                        display: true,
                        text: '偏差値'
                    }
                },
                x: {
                    ticks: {
                        maxRotation: 45,
                        minRotation: 45
                    }
                }
            }
        }
    });

    // 表示切替ボタンのイベント設定
    const totalBtn = document.querySelector('[data-subject="total"]');
    const individualBtn = document.querySelector('[data-subject="individual"]');
    const subjectToggles = document.querySelector('.subject-toggles');

    if (totalBtn && individualBtn) {
        totalBtn.addEventListener('click', function() {
            // 合計表示モード
            datasets.forEach(dataset => {
                dataset.hidden = !['5科目合計', '3科目合計'].includes(dataset.label);
            });
            chart.update();
        });

        individualBtn.addEventListener('click', function() {
            // 個別科目表示モード
            datasets.forEach(dataset => {
                dataset.hidden = ['5科目合計', '3科目合計'].includes(dataset.label);
            });
            chart.update();
        });
    }

    // 個別科目のトグルボタン
    const subjectButtons = document.querySelectorAll('.subject-toggles .btn');
    subjectButtons.forEach(button => {
        button.addEventListener('click', function() {
            const subject = button.dataset.subject;
            const dataset = datasets.find(ds => ds.label === subjectNames[subject]);
            if (dataset) {
                dataset.hidden = !button.classList.contains('active');
                chart.update();
            }
        });
    });

    // ウィンドウリサイズ時のグラフ更新
    window.addEventListener('resize', function() {
        chart.update();
    });
});