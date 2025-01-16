document.addEventListener("DOMContentLoaded", function () {
    const usernameInput = document.getElementById("username");
    const schoolSelect = document.getElementById("cramSchool");
    let debounceTimer;

    usernameInput.addEventListener("input", function () {
        clearTimeout(debounceTimer);
        const username = this.value.trim();

        debounceTimer = setTimeout(() => {
            if (username.length > 0) {
                fetchSchools(username);
            } else {
                clearSchoolOptions();
            }
        }, 300);
    });

    function fetchSchools(username) {
        fetch(`/api/schools-by-username?username=${encodeURIComponent(username)}`)
            .then((response) => response.json())
            .then((schools) => {
                updateSchoolOptions(schools);
            })
            .catch((error) => {
                console.error("Error fetching schools:", error);
                clearSchoolOptions();
            });
    }

    function updateSchoolOptions(schools) {
        schoolSelect.innerHTML = '<option value="">校舎を選択してください</option>';

        if (schools && schools.length > 0) {
            schools.forEach((school) => {
                const option = document.createElement("option");
                option.value = school.cramSchoolId;
                option.textContent = school.name;
                schoolSelect.appendChild(option);
            });
            schoolSelect.disabled = false;
        } else {
            schoolSelect.disabled = true;
        }
    }

    function clearSchoolOptions() {
        schoolSelect.innerHTML = '<option value="">校舎を選択してください</option>';
        schoolSelect.disabled = true;
    }
});
