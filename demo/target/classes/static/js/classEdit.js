const orders = document.querySelectorAll(".order");
orders.forEach((order) => {
    if (order.textContent === "-1") {
        order.textContent = "";
    }
});
