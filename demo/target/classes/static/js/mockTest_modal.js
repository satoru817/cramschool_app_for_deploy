$(document).ready(function () {
    $(".result-row").click(function () {
        // データを取得
        var studentName = $(this).find(".student-name").text();
        var japanese = $(this).find(".japanese").text();
        var japaneseSs = $(this).find(".japanese-ss").text();
        var math = $(this).find(".math").text();
        var mathSs = $(this).find(".math-ss").text();
        var english = $(this).find(".english").text();
        var englishSs = $(this).find(".english-ss").text();
        var social = $(this).find(".social").text();
        var socialSs = $(this).find(".social-ss").text();
        var science = $(this).find(".science").text();
        var scienceSs = $(this).find(".science-ss").text();
        var total3 = $(this).find(".total-3").text();
        var total5 = $(this).find(".total-5").text();
        var total3Ss = $(this).find(".total3-ss").text();
        var total5Ss = $(this).find(".total5-ss").text();
        var dreamSchool1 = $(this).find(".dream-school1").text();
        var dreamSchool1Prob = $(this).find(".probability1").text();
        var dreamSchool2 = $(this).find(".dream-school2").text();
        var dreamSchool2Prob = $(this).find(".probability2").text();
        var dreamSchool3 = $(this).find(".dream-school3").text();
        var dreamSchool3Prob = $(this).find(".probability3").text();
        var dreamSchool4 = $(this).find(".dream-school4").text();
        var dreamSchool4Prob = $(this).find(".probability4").text();
        var dreamSchool5 = $(this).find(".dream-school5").text();
        var dreamSchool5Prob = $(this).find(".probability5").text();
        var dreamSchool6 = $(this).find(".dream-school6").text();
        var dreamSchool6Prob = $(this).find(".probability6").text();
        // モーダルにデータをセット
        $("#modalStudentName").text(studentName);
        $("#modalJapanese").text(japanese);
        $("#modalJapaneseSs").text(japaneseSs);
        $("#modalMath").text(math);
        $("#modalMathSs").text(mathSs);
        $("#modalEnglish").text(english);
        $("#modalEnglishSs").text(englishSs);
        $("#modalScience").text(science);
        $("#modalScienceSs").text(scienceSs);
        $("#modalSocial").text(social);
        $("#modalSocialSs").text(socialSs);
        $("#modalJmeSs").text(total3Ss);
        $("#modalJmessSs").text(total5Ss);
        $("#modalDreamSchool1").text(dreamSchool1);
        $("#modalDreamSchool1Probability").text(dreamSchool1Prob);
        $("#modalDreamSchool2").text(dreamSchool2);
        $("#modalDreamSchool2Probability").text(dreamSchool2Prob);
        $("#modalDreamSchool3").text(dreamSchool3);
        $("#modalDreamSchool3Probability").text(dreamSchool3Prob);
        $("#modalDreamSchool4").text(dreamSchool4);
        $("#modalDreamSchool4Probability").text(dreamSchool4Prob);
        $("#modalDreamSchool5").text(dreamSchool5);
        $("#modalDreamSchool5Probability").text(dreamSchool5Prob);
        $("#modalDreamSchool6").text(dreamSchool6);
        $("#modalDreamSchool6Probability").text(dreamSchool6Prob);

        // モーダルを表示
        $("#studentDetailModal").modal("show");
    });

    // カーソルをポインターに変更するスタイルを追加
    $(".result-row").css("cursor", "pointer");
});
