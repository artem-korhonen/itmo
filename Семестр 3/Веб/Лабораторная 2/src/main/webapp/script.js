const changeXSelect = document.querySelector(".change-x-select");
const changeYInput = document.getElementById("y");
const changeRCheckboxes = document.querySelectorAll(".change-r");
const sendButton = document.getElementById("send");
const graphSvg = document.getElementById("graph-svg");
const resultsTable = document.getElementById("results");

const errorX = document.getElementById("error-x");
const errorY = document.getElementById("error-y");
const errorR = document.getElementById("error-r");
const errorMain = document.getElementById("error-main");
const errorYLabel = document.getElementById("error-y-label");
const errorMainLabel = document.getElementById("error-main-label");

let oldYValue;
let allPoints = [];

let x = -4;
let y = null;


async function updateTable() {
    const container = document.getElementById("results-container");

    const response = await fetch("table.jsp");
    if (!response.ok) {
        console.error("Ошибка при получении table.jsp:", response.status);
        return;
    }

    const html = await response.text();
    container.innerHTML = html;
}


function redrawPoints() {
    const selectedRValues = getSelectedRValues();
    if (selectedRValues.length === 0) return;

    const r = Math.max(...selectedRValues);

    const oldPoints = graphSvg.querySelectorAll(".dynamic-point");
    oldPoints.forEach(p => p.remove());

    allPoints.forEach(point => {
        makePoint(point.x, point.y, r, point.test ? "green" : "red");
    });
}


function makePoint(x, y, r, color) {
    const graphCenter = { x: 200, y: 200 };
    const pixelPerR = 160 / r;
    const svgX = graphCenter.x + x * pixelPerR;
    const svgY = graphCenter.y - y * pixelPerR;
    drawPoint(svgX, svgY, color)
}


function drawPoint(svgX, svgY, color) {
    const point = document.createElementNS("http://www.w3.org/2000/svg", "circle");
    point.setAttribute("cx", svgX);
    point.setAttribute("cy", svgY);
    point.setAttribute("r", 4);
    point.setAttribute("fill", color);
    point.setAttribute("stroke", "black");
    point.classList.add("dynamic-point");
    graphSvg.appendChild(point);
}


function getSelectedRValues() {
    return Array.from(changeRCheckboxes)
        .filter(cb => cb.checked)
        .map(cb => Number(cb.dataset.r));
}


async function sendPointInfo(points) {
    console.log("Отправка данных:", points);

    try {
        const response = await fetch("controller", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(points)
        });

        if (!response.ok) {
            const text = await response.text();
            console.error("Ошибка сервера:", response.status, text);
            alert(`Ошибка сервера: ${response.status}\n${text}`);
            return null;
        }

        const jsonResponse = await response.json();
        allPoints.push(...jsonResponse.points);
        redrawPoints();
        updateTable();
    } catch (err) {
        console.error("Ошибка при запросе:", err);
        alert("Ошибка при запросе: " + err);
        return null;
    }
}


changeXSelect.addEventListener("change", () => {
    const selectedOption = changeXSelect.selectedOptions[0];
    if (selectedOption) {
        x = Number(selectedOption.dataset.x);
        console.log("X установлен:", x);
        errorX.classList.add("hidden");
    } else {
        x = null;
    }
});


function testY(value) {
    value = value.replace(",", ".");

    if (value === "" || value === "-" || value === "+") return true;

    const allowed = /^[+-]?\d*\.?\d{0,10}$/;
    if (!allowed.test(value)) return false;

    const numValue = parseFloat(value);

    if (isNaN(numValue)) return false;
    if (!(numValue > -3 && numValue < 3)) return false;

    return true;
}


changeYInput.addEventListener("beforeinput", () => {
    oldYValue = changeYInput.value;
});


changeYInput.addEventListener("input", () => {
    changeYInput.value = changeYInput.value.replace(",", ".");
    const rawValue = changeYInput.value;

    if (!testY(rawValue)) {
        changeYInput.value = oldYValue;
        const prevNum = parseFloat(oldYValue);
        y = isNaN(prevNum) ? null : prevNum;
    } else {
        oldYValue = rawValue;
        const num = parseFloat(rawValue);
        y = isNaN(num) ? null : num;
    }
});


changeRCheckboxes.forEach(cb => {
    cb.addEventListener("change", () => {
        errorR.classList.add("hidden");
        redrawPoints();
    });
});


sendButton.addEventListener("click", async () => {
    errorMain.classList.add("hidden");
    errorX.classList.add("hidden");
    errorY.classList.add("hidden");
    errorR.classList.add("hidden");

    const selectedRValues = getSelectedRValues();

    if (x == null) errorX.classList.remove("hidden");
    if (y == null) errorY.classList.remove("hidden");
    if (selectedRValues.length === 0) errorR.classList.remove("hidden");

    if (x == null || y == null || selectedRValues.length === 0) {
        console.warn("Некорректные данные:", { x, y, selectedRValues });
        return;
    }

    let points = []

    for (const rValue of selectedRValues) {
        points.push({ x: x, y: y, r: rValue });
    }

    await sendPointInfo(points);
});


graphSvg.addEventListener("click", async (event) => {
    errorR.classList.add("hidden");
    const selectedRValues = getSelectedRValues();

    if (selectedRValues.length === 0) {
        errorR.classList.remove("hidden");
        return;
    }

    const r = Math.max(...selectedRValues);
    const rect = graphSvg.getBoundingClientRect();
    const graphCenter = { x: 200, y: 200 };
    const pixelPerR = 160 / r;

    const rawX = (event.clientX - rect.left - graphCenter.x) / pixelPerR;
    const rawY = (graphCenter.y - (event.clientY - rect.top)) / pixelPerR;

    const roundedX = Math.max(-4, Math.min(4, Math.round(rawX)));
    const roundedY = Math.max(-2.99, Math.min(2.99, parseFloat(rawY.toFixed(2))));

    x = roundedX;
    y = roundedY;
    changeXSelect.value = String(roundedX);
    changeYInput.value = roundedY;

    await sendPointInfo([{ x: roundedX, y: roundedY, r }]);
});


document.addEventListener("DOMContentLoaded", async () => {
    try {
        const response = await fetch("controller");
        if (!response.ok) {
            console.error("Ошибка при получении истории:", response.status);
            return;
        }
        const data = await response.json();
        allPoints.push(...data.points);
        redrawPoints();
    } catch (err) {
        console.error("Ошибка загрузки истории:", err);
    }
});
