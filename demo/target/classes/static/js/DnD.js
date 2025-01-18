function dragOverHandler(event) {
  event.preventDefault();
  event.currentTarget.classList.add('drag-over');
}

function dragLeaveHandler(event) {
  event.preventDefault();
  event.currentTarget.classList.remove('drag-over');
}

function dropHandler(event) {
  event.preventDefault();
  event.currentTarget.classList.remove('drag-over');

  const files = event.dataTransfer.files;
  if (files.length > 0) {
    const file = files[0];
    if (file.type === "text/csv" || file.name.endsWith('.csv')) {
      handleFile(file);
    } else {
      alert('CSVファイルのみアップロード可能です。');
    }
  }
}

function handleFile(file) {
  const fileInput = document.getElementById('csvFile');
  const dt = new DataTransfer();
  dt.items.add(file);
  fileInput.files = dt.files;

  // ファイル情報の表示
  const fileInfo = document.getElementById('file-info');
  const fileName = document.getElementById('file-name');
  fileName.textContent = file.name;
  fileInfo.classList.remove('d-none');
}

// ファイル選択時の処理
document.getElementById('csvFile').addEventListener('change', function(event) {
  const file = event.target.files[0];
  if (file) {
    handleFile(file);
  }
});

// クリックでもファイル選択できるように
document.getElementById('drop-zone').addEventListener('click', function() {
  document.getElementById('csvFile').click();
});