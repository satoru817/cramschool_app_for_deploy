function hideScore(){
    const scores = document.querySelectorAll('.score');
    scores.forEach(score=>{
        score.classList.toggle('d-none');
    });
}