

document.addEventListener('DOMContentLoaded', function() {
    const ctx = document.getElementById('schoolRecordChart').getContext('2d');

    // データの整形
    const labels = schoolRecordResults.map(result => result.title);
    const datasets = [
        {
            label: '換算内申',
            data: schoolRecordResults.map(result => result.projectedSchoolScore),
            borderColor: 'rgb(153, 51, 255)',
            tension: 0.1,
            fill: false,
            borderWidth: 3,
            yAxisID: 'y2'  // 右軸
        },
        {
            label: '5教科合計',
            data: schoolRecordResults.map(result => result.total5),
            borderColor: 'rgb(75, 192, 192)',
            tension: 0.1,
            fill: false,
            yAxisID: 'y2'  // 右軸
        },
        {
            label: '9教科合計',
            data: schoolRecordResults.map(result => result.total9),
            borderColor: 'rgb(255, 99, 132)',
            tension: 0.1,
            fill: false,
            yAxisID: 'y2'  // 右軸
        },
        {
            label: '国語',
            data: schoolRecordResults.map(result => result.japanese),
            borderColor: 'rgb(255, 159, 64)',
            tension: 0.1,
            fill: false,
            hidden: true,
            yAxisID: 'y1'  // 左軸
        },
        {
            label: '数学',
            data: schoolRecordResults.map(result => result.math),
            borderColor: 'rgb(54, 162, 235)',
            tension: 0.1,
            fill: false,
            hidden: true,
            yAxisID: 'y1'  // 左軸
        },
        {
            label: '英語',
            data: schoolRecordResults.map(result => result.english),
            borderColor: 'rgb(153, 102, 255)',
            tension: 0.1,
            fill: false,
            hidden: true,
            yAxisID: 'y1'  // 左軸
        },
        {
            label: '理科',
            data: schoolRecordResults.map(result => result.science),
            borderColor: 'rgb(75, 192, 192)',
            tension: 0.1,
            fill: false,
            hidden: true,
            yAxisID: 'y1'  // 左軸
        },
        {
            label: '社会',
            data: schoolRecordResults.map(result => result.social),
            borderColor: 'rgb(255, 99, 132)',
            tension: 0.1,
            fill: false,
            hidden: true,
            yAxisID: 'y1'  // 左軸
        }
    ];

    // チャートの設定
    new Chart(ctx, {
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
                    text: '内申点の推移'
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
                    min: 1,
                    max: 5,
                    ticks: {
                        stepSize: 1
                    },
                    title: {
                        display: true,
                        text: '各教科の評定（1-5）'
                    }
                },
                y2: {
                    type: 'linear',
                    display: true,
                    position: 'right',
                    min: 0,
                    max: 300,
                    ticks: {
                        stepSize: 20
                    },
                    title: {
                        display: true,
                        text: '合計点・換算内申'
                    },
                    grid: {
                        drawOnChartArea: false // 右軸のグリッドは表示しない
                    }
                }
            }
        }
    });
});