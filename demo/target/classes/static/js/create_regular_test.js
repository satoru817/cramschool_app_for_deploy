const fullScoreBtn = document.getElementById("fullScoreBtn");
const averageScoreBtn = document.getElementById("averageScoreBtn");

async function saveFullScore(regularTestId) {
    console.log("saveFullScoreは呼び出されています。");
    const form = document.getElementById("fullScoreForm");
    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute("content");
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute("content");

    if (!form) {
        console.error("フォームが見つかりません");
        alert("フォームが見つかりません");
        return; // 早期リターン
    }

    const formData = new FormData(form);
    const data = Object.fromEntries(formData.entries());

    try {
        const response = await fetch(`/updateRegularTest/${regularTestId}`, {
            method: "POST",
            headers: {
                [csrfHeader]: csrfToken,
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            alert("定期テストの満点の情報が正常に保存されました");
            location.reload(); // ページ全体をリロード
        } else {
            alert("保存に失敗しました");
        }
    } catch (error) {
        console.error("Error:", error);
        alert("エラーが発生しました");
    }
}

async function saveAverageScore(regularTestId) {
    console.log("saveAverageScoreは呼び出されています。");
    const form = document.getElementById("averageScoreForm");
    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute("content");
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute("content");

    if (!form) {
        console.error("フォームが見つかりません");
        alert("フォームが見つかりません");
        return; // 早期リターン
    }

    const formData = new FormData(form);
    const data = Object.fromEntries(formData.entries());

    try {
        const response = await fetch(`/updateRegularTest/average/${regularTestId}`, {
            method: "POST",
            headers: {
                [csrfHeader]: csrfToken,
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            alert("定期テストの平均点の情報が正常に保存されました");
            location.reload(); // ページ全体をリロード
        } else {
            alert("保存に失敗しました");
        }
    } catch (error) {
        console.error("Error:", error);
        alert("エラーが発生しました");
    }
}
