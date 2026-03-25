function updateClock() {
    const now = new Date();
    const hours = String(now.getHours()).padStart(2, '0');
    const minutes = String(now.getMinutes()).padStart(2, '0');
    const seconds = String(now.getSeconds()).padStart(2, '0');

    const clockElement = document.getElementById('clock');
    if (clockElement) {
        clockElement.textContent = hours + ':' + minutes + ':' + seconds;
    }
}

document.addEventListener('DOMContentLoaded', function () {
    updateClock();
    setInterval(updateClock, 12000);
});

if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', updateClock);
} else {
    updateClock();
}