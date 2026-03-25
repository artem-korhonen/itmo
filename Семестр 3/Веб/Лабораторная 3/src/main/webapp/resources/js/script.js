const changeXSelect = document.querySelector(".change-x-select");
const changeYInput = document.getElementById("changeY");
const changeRCheckboxes = document.querySelectorAll(".change-r");
const sendButton = document.getElementById("sendButton");
const updateButton = document.getElementById("updateButton");
const graphSvg = document.getElementById("graph-svg");
const resultsTable = document.getElementById("results");

const errorX = document.getElementById("error-x");
const errorY = document.getElementById("error-y");
const errorR = document.getElementById("error-r");
const errorMain = document.getElementById("error-main");
const errorYLabel = document.getElementById("error-y-label");
const errorMainLabel = document.getElementById("error-main-label");

const xLinks = document.querySelectorAll(".change-x .button");

let x;
let y;
let r;
let hit;
const graphCenter = { x: 200, y: 200 };
const pixelPerR = 32;

let renderedPoints = new Set();


function afterSend(data) {
    if (data.status === "success") {
        const hitValue = document.getElementById("hitValue").value;
        makePoint(x, y, r, hitValue === "true" ? "green" : "red");
        console.log(x, y, r, hitValue);
    }
}


function afterUpdate(data) {
    if (data.status === "success") {
        redrawPoints();
    }
}


function afterSelectR() {
    r = document.getElementById("changeR").value;
    slideGraph();
}


function makePoint(x, y, r, color) {
    const svgX = graphCenter.x + x * pixelPerR;
    const svgY = graphCenter.y - y * pixelPerR;
    drawPoint(svgX, svgY, color);
}


function redrawPoints() {
    const points = document.querySelectorAll(".dynamic-point");
    points.forEach(point => point.remove());

    const table = document.querySelector('table[data-group="results"]');
    if (table) {
        const rows = table.querySelectorAll('tbody tr');

        rows.forEach((row) => {
            const cells = [...row.querySelectorAll('td')].map(td => td.textContent.trim());
            if (!(cells[0] === "")) {
                makePoint(cells[0], cells[1], cells[2], (cells[3] == "Попал") ? "green" : "red");
            }
        });
    }
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


// function slideGraph(r) {
//     const circle = document.getElementById("circle");
//     const rect = document.getElementById("rect");
//     const triangle = document.getElementById("triangle");

//     circle.setAttribute("r", `${r*32}`);
//     rect.setAttribute("points", `200,${200 - r*32} ${200 + r*32},${200 - r*32} ${200 + r*32},200 200,200`);
//     triangle.setAttribute("points", `200,200 ${200 - r*16},200 200,${200 + r*16}`);
// }

function slideGraph() {
    const rValue = parseFloat(r);
    const Rpx = rValue * pixelPerR;

    const center = 200;

    const circle = document.getElementById("circle");
    const rect = document.getElementById("rect");
    const triangle = document.getElementById("triangle");

    circle.setAttribute("r", Rpx);
    rect.setAttribute(
        "points",
        `${center},${center - Rpx} ${center + Rpx},${center - Rpx} ${center + Rpx},${center} ${center},${center}`
    );
    triangle.setAttribute(
        "points",
        `${center},${center} ${center - Rpx},${center} ${center},${center - Rpx}`
    );

    document.getElementById("tick-x-r").setAttribute("x1", center + Rpx);
    document.getElementById("tick-x-r").setAttribute("x2", center + Rpx);

    document.getElementById("tick-x-r2").setAttribute("x1", center + Rpx/2);
    document.getElementById("tick-x-r2").setAttribute("x2", center + Rpx/2);

    document.getElementById("tick-x-mr2").setAttribute("x1", center - Rpx/2);
    document.getElementById("tick-x-mr2").setAttribute("x2", center - Rpx/2);

    document.getElementById("tick-x-mr").setAttribute("x1", center - Rpx);
    document.getElementById("tick-x-mr").setAttribute("x2", center - Rpx);

    document.getElementById("tick-y-r").setAttribute("y1", center - Rpx);
    document.getElementById("tick-y-r").setAttribute("y2", center - Rpx);

    document.getElementById("tick-y-r2").setAttribute("y1", center - Rpx/2);
    document.getElementById("tick-y-r2").setAttribute("y2", center - Rpx/2);

    document.getElementById("tick-y-mr2").setAttribute("y1", center + Rpx/2);
    document.getElementById("tick-y-mr2").setAttribute("y2", center + Rpx/2);

    document.getElementById("tick-y-mr").setAttribute("y1", center + Rpx);
    document.getElementById("tick-y-mr").setAttribute("y2", center + Rpx);

    document.getElementById("label-x-r").setAttribute("x", center + Rpx);
    document.getElementById("label-x-r2").setAttribute("x", center + Rpx/2);
    document.getElementById("label-x-mr2").setAttribute("x", center - Rpx/2);
    document.getElementById("label-x-mr").setAttribute("x", center - Rpx);

    document.getElementById("label-y-r").setAttribute("y", center - Rpx);
    document.getElementById("label-y-r2").setAttribute("y", center - Rpx/2);
    document.getElementById("label-y-mr2").setAttribute("y", center + Rpx/2);
    document.getElementById("label-y-mr").setAttribute("y", center + Rpx);
}


function validateForm() {
    let test = true;
    y = changeYInput.value;

    console.log(x, y, r);
    if (x === undefined || x === null) {
        errorX.classList.remove("hidden");
        test = false;
    }

    if (y === undefined || y === null || y === "") {
        errorY.classList.remove("hidden")
        test = false;
    }

    return test;
}


graphSvg.addEventListener("click", (event) => {
    console.log("Graph clicked");
    errorR.classList.add("hidden");

    r = document.getElementById("changeR").value;
    const rect = graphSvg.getBoundingClientRect();

    const rawX = (event.clientX - rect.left - graphCenter.x) / pixelPerR;
    const rawY = (graphCenter.y - (event.clientY - rect.top)) / pixelPerR;

    const roundedX = Math.max(-4, Math.min(4, Math.round(rawX)));
    const roundedY = Math.max(-4.999, Math.min(2.999, parseFloat(rawY.toFixed(3))));

    x = roundedX;
    y = roundedY;

    try {
        const changeXTable = document.getElementById('changeX');
        const changeY = document.getElementById('changeY');

        for (const changeXLink of changeXTable.children) {
            console.log(`Checking X link with value: ${changeXLink.dataset.x}`);
            if (changeXLink.dataset.x == String(roundedX)) {
                changeXLink.click();
                break;
            }
        }

        if (changeY) changeY.value = String(roundedY);

        if (sendButton) sendButton.click();
    } catch (e) {
        console.error('Failed to pass coords to JSF form', e);
    }
});


xLinks.forEach(link => {
    link.addEventListener("click", () => {
        errorX.classList.add("hidden");
        xLinks.forEach(l => l.style.backgroundColor = "#0ff");
        link.style.backgroundColor = "#0d0";
        x = link.getAttribute("data-x");
    });
});


// sendButton.addEventListener("click", (e) => {
//     let test = true;

//     if (x === undefined || x === null) {
//         errorX.classList.remove("hidden");
//         test = false;
//     }

//     if (y === undefined || y === null || y === "") {
//         errorY.classList.remove("hidden");
//         test = false;
//     }

//     if (!test) {
//         e.preventDefault();
//     }
// });


function testY(value) {
    value = value.replace(",", ".");

    if (value === "" || value === "-" || value === "+") return true;

    const allowed = /^[+-]?\d*\.?\d{0,10}$/;
    if (!allowed.test(value)) return false;

    const numValue = parseFloat(value);

    if (isNaN(numValue)) return false;
    if (!(numValue > -5 && numValue < 3)) return false;

    return true;
}


changeYInput.addEventListener("beforeinput", () => {
    oldYValue = changeYInput.value;
});


changeYInput.addEventListener("input", () => {
    errorY.classList.add("hidden");
    changeYInput.value = changeYInput.value.replace(",", ".");
    const rawValue = changeYInput.value;

    if (!testY(rawValue)) {
        changeYInput.value = oldYValue;
    } else {
        oldYValue = rawValue;
        y = rawValue === "" ? null : parseFloat(rawValue);
    }
});


document.addEventListener("DOMContentLoaded", () => {
    console.log("DOM fully loaded and parsed");

    const table = document.querySelector('table[data-group="results"]');
    console.log(table);
    if (table) {
        const rows = table.querySelectorAll('tbody tr');

        rows.forEach((row) => {
            const cells = [...row.querySelectorAll('td')].map(td => td.textContent.trim());
            if (!(cells[0] === "")) {
                makePoint(cells[0], cells[1], cells[2], (cells[3] == "Попал") ? "green" : "red");
            }
        });
    }

    document.getElementById("changeR").value = 1;
    r = 1;
    slideGraph();

    setInterval(() => {updateButton.click();}, 5000);
});

