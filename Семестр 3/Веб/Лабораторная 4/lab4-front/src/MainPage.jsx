import { useState, useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { useNavigate } from 'react-router-dom'
import { RadioButton } from 'primereact/radiobutton'
import { Slider } from 'primereact/slider'

import { getToken, removeToken } from './auth'

import { setX, setY, setR, setResults } from './store'

import "primereact/resources/themes/lara-light-cyan/theme.css";
import "primereact/resources/primereact.min.css";
import { jwtDecode } from 'jwt-decode'


function MainPage() {
    const navigate = useNavigate();

    const username = getToken() ? jwtDecode(getToken()).username : "";

    const x = useSelector(state => state.x);
    const y = useSelector(state => state.y);
    const r = useSelector(state => state.r);

    const results = useSelector(state => state.results);

    const BASE_RADIUS = 160 / 5;

    const [errorX, setErrorX] = useState();
    const [errorY, setErrorY] = useState();
    const [errorR, setErrorR] = useState();
    const [mainError, setMainError] = useState();

    const dispatch = useDispatch();

    const [page, setPage] = useState(1);
    const [maxPage, setMaxPage] = useState(1);
    const MAX_ITEMS_PER_PAGE = 10;

    const paginatedResults = [...results].reverse().slice(
        (page - 1) * MAX_ITEMS_PER_PAGE,
        page * MAX_ITEMS_PER_PAGE
    );

    const handleCheck = async (x, y, r) => {
        if (!validateForm(x, y, r)) return;

        try {
            const token = getToken();

            const response = await fetch(`/api/points`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": token
                },
                body: JSON.stringify({
                    x: Number(x),
                    y: Number(y),
                    r: Number(r),
                    token: token
                })
            });

            if (response.status === 401) {
                logout();
                return;
            }

            if (!response.ok) throw new Error("Ошибка сервера");

            const data = await response.json();

            dispatch(setResults(data.points));
        } catch (e) {
            setMainError("Ошибка отправки точки");
        }
    };

    const logout = () => {
        removeToken();
        setResults([]);
        navigate("/");
    }

    const validateForm = (x, y, r) => {
        let test = true;

        if (x === null) {
            setErrorX("Отсутствует x");
            test = false;
        }

        if (y === null) {
            setErrorY("Отсутствует y");
            test = false;
        }

        if (r === null) {
            setErrorR("Отсутствует r");
            test = false;
        }

        return test;
    }

    const handleSvgClick = async (event) => {
        if (r === null) {
            setErrorR("Отсутствует r");
            return;
        }

        const rect = event.currentTarget.getBoundingClientRect();

        const svgX = event.clientX - rect.left;
        const svgY = event.clientY - rect.top;

        let logicalX = Number(((svgX - 200) / BASE_RADIUS).toFixed(0));
        let logicalY = Number(((200 - svgY) / BASE_RADIUS).toFixed(2));

        if (logicalX >= 5) {
            logicalX = 5;
        } else if (logicalX <= -3) {
            logicalX = -3;
        }

        if (logicalY >= 5) {
            logicalY = 5;
        } else if (logicalY <= -3) {
            logicalY = -3;
        }

        dispatch(setX(logicalX));
        dispatch(setY(logicalY));
        setErrorX("");
        setErrorY("");

        handleCheck(logicalX, logicalY, r);
    };

    useEffect(() => {
        const fetchPoints = async () => {
            try {
                const token = getToken();
                const response = await fetch(`/api/points`, {
                    method: "GET",
                    headers: {
                        "Content-Type": "application/json",
                        "Authorization": token
                    }
                });

                if (response.status === 401) {
                    logout();
                    return;
                }

                if (!response.ok) throw new Error("Ошибка при загрузке точек");

                const data = await response.json();

                if (data && data.points) {
                    dispatch(setResults(data.points));
                }
            } catch (e) {
                console.error("Не удалось загрузить точки:", e);
                setMainError("Ошибка при получении истории точек");
            }
        };

        fetchPoints();

        const interval = setInterval(fetchPoints, 3000);

        return () => clearInterval(interval);
    }, []);

    useEffect(() => {
        const newMax = Math.ceil(results.length / MAX_ITEMS_PER_PAGE) || 1;
        setMaxPage(newMax);

        if (page > newMax) {
            setPage(newMax);
        }
    }, [results])

    return (
        <div className="min-h-screen bg-cover bg-center px-10 py-10 bg-[url('minecraft.jpg')] font-serif">
            <div className="max-w-5xl mx-auto bg-white p-5 border rounded-xl shadow-2xl shadow-black">
                {/* Заголовок */}
                <div className="
                    grid
                    grid-cols-2
                    tablet:grid-cols-3
                    desktop:grid-cols-6
                    mb-5
                    text-center
                    font-bold">
                    <div className="p-3 border bg-gray-300">Корхонен Артём Андреевич</div>
                    <div className="p-3 border bg-gray-300">466298</div>
                    <div className="p-3 border bg-gray-300">P3232</div>
                    <div className="p-3 border bg-gray-300">Лабораторная №4</div>
                    <div className="p-3 border bg-gray-300">Вариант №52511</div>
                    <div className="p-3 border bg-gray-300">Вы: {username}</div>
                </div>

                {/* График */}
                <div className="flex justify-center mb-5">
                    <svg width="400" height="400" viewBox="0 0 400 400" className="border border-black rounded-lg bg-white shadow-lg cursor-pointer" onClick={handleSvgClick}>
                        {/* <!-- Четверть круга --> */}
                        <circle id="circle" cx="200" cy="200" r={Math.abs(r * BASE_RADIUS)} fill="cyan" stroke="black" />
                        {(r >= 0) ? (
                            <>
                                <polygon points="0,0 0,200 400,200 400,0" fill="white" stroke="white" />
                                <polygon points="200,0 400,0 400,400 200,400" fill="white" stroke="white" />
                            </>
                        ) : (
                            <>
                                <polygon points="0,0 0,400 200,400 200,0" fill="white" stroke="white" />
                                <polygon points="0,200 400,200 400,400 0,400" fill="white" stroke="white" />
                            </>
                        )}


                        {/* <!-- Прямоугольник --> */}
                        <polygon id="rect" points={`200,${200 - r * BASE_RADIUS} ${200 + r / 2 * BASE_RADIUS},${200 - r * BASE_RADIUS} ${200 + r / 2 * BASE_RADIUS},200 200,200`} fill="cyan" stroke="black" />

                        {/* <!-- Треугольник --> */}
                        <polygon id="triangle" points={`${200 - r / 2 * BASE_RADIUS},200 200,200 200,${200 - r / 2 * BASE_RADIUS}`} fill="cyan" stroke="black" />

                        {/* <!-- Оси --> */}
                        <line x1="200" y1="0" x2="200" y2="400" stroke="black" />
                        <line x1="200" y1="0" x2="210" y2="10" stroke="black" />
                        <line x1="200" y1="0" x2="190" y2="10" stroke="black" />

                        <line x1="0" y1="200" x2="400" y2="200" stroke="black" />
                        <line x1="400" y1="200" x2="390" y2="190" stroke="black" />
                        <line x1="400" y1="200" x2="390" y2="210" stroke="black" />

                        {/* <!-- Чёрточки по X --> */}
                        <line id="tick-x-mr" x1={`${200 - r * BASE_RADIUS}`} y1="195" x2={`${200 - r * BASE_RADIUS}`} y2="205" stroke="black" />
                        <line id="tick-x-mr2" x1={`${200 - r * BASE_RADIUS / 2}`} y1="195" x2={`${200 - r * BASE_RADIUS / 2}`} y2="205" stroke="black" />
                        <line id="tick-x-r2" x1={`${200 + r * BASE_RADIUS / 2}`} y1="195" x2={`${200 + r * BASE_RADIUS / 2}`} y2="205" stroke="black" />
                        <line id="tick-x-r" x1={`${200 + r * BASE_RADIUS}`} y1="195" x2={`${200 + r * BASE_RADIUS}`} y2="205" stroke="black" />

                        {/* <!-- Чёрточки по Y --> */}
                        <line id="tick-y-mr" x1="195" y1={`${200 - r * BASE_RADIUS}`} x2="205" y2={`${200 - r * BASE_RADIUS}`} stroke="black" />
                        <line id="tick-y-mr2" x1="195" y1={`${200 - r * BASE_RADIUS / 2}`} x2="205" y2={`${200 - r * BASE_RADIUS / 2}`} stroke="black" />
                        <line id="tick-y-r2" x1="195" y1={`${200 + r * BASE_RADIUS / 2}`} x2="205" y2={`${200 + r * BASE_RADIUS / 2}`} stroke="black" />
                        <line id="tick-y-r" x1="195" y1={`${200 + r * BASE_RADIUS}`} x2="205" y2={`${200 + r * BASE_RADIUS}`} stroke="black" />

                        {/* <!-- Чёрточки 5 по X --> */}
                        <line x1="40" y1="190" x2="40" y2="210" stroke="black" />
                        <line x1="120" y1="190" x2="120" y2="210" stroke="black" />
                        <line x1="280" y1="190" x2="280" y2="210" stroke="black" />
                        <line x1="360" y1="190" x2="360" y2="210" stroke="black" />

                        {/* <!-- Чёрточки 5 по Y --> */}
                        <line x1="195" y1="40" x2="205" y2="40" stroke="black" />
                        <line x1="195" y1="120" x2="205" y2="120" stroke="black" />
                        <line x1="195" y1="280" x2="205" y2="280" stroke="black" />
                        <line x1="195" y1="360" x2="205" y2="360" stroke="black" />

                        {/* <!-- Подписи по X --> */}
                        <text id="label-x-mr" x={`${200 - r * BASE_RADIUS}`} y="220" font-size="10" text-anchor="middle">-R</text>
                        <text id="label-x-mr2" x={`${200 - r * BASE_RADIUS / 2}`} y="220" font-size="10" text-anchor="middle">-R/2</text>
                        <text id="label-x-r2" x={`${200 + r * BASE_RADIUS / 2}`} y="220" font-size="10" text-anchor="middle">R/2</text>
                        <text id="label-x-r" x={`${200 + r * BASE_RADIUS}`} y="220" font-size="10" text-anchor="middle">R</text>

                        {/* <!-- Подписи по Y --> */}
                        <text id="label-y-mr" x="180" y={`${200 - r * BASE_RADIUS}`} font-size="10" alignment-baseline="middle">R</text>
                        <text id="label-y-mr2" x="175" y={`${200 - r * BASE_RADIUS / 2}`} font-size="10" alignment-baseline="middle">R/2</text>
                        <text id="label-y-r2" x="180" y={`${200 + r * BASE_RADIUS / 2}`} font-size="10" alignment-baseline="middle">-R/2</text>
                        <text id="label-y-r" x="175" y={`${200 + r * BASE_RADIUS}`} font-size="10" alignment-baseline="middle">-R</text>

                        {/* <!-- Подписи 5 по X --> */}
                        <text x="40" y="185" font-size="10" text-anchor="middle">-5</text>
                        <text x="120" y="185" font-size="10" text-anchor="middle">-2.5</text>
                        <text x="280" y="185" font-size="10" text-anchor="middle">2.5</text>
                        <text x="360" y="185" font-size="10" text-anchor="middle">5</text>

                        {/* <!-- Подписи 5 по Y --> */}
                        <text x="215" y="40" font-size="10" alignment-baseline="middle">5</text>
                        <text x="215" y="120" font-size="10" alignment-baseline="middle">2.5</text>
                        <text x="215" y="280" font-size="10" alignment-baseline="middle">-2.5</text>
                        <text x="215" y="360" font-size="10" alignment-baseline="middle">-5</text>

                        {/* <-- Площадь клика --> */}
                        <polygon id="rect-click" points={`${40 + 2 * 32},${40 + 0 * 32} ${40 + 10 * 32},${40 + 0 * 32} ${40 + 10 * 32},${40 + 8 * 32} ${40 + 2 * 32},${40 + 8 * 32}`} fill="red" opacity="0.2" />

                        {results.map((point, index) => {
                            const cx = 200 + point.x * BASE_RADIUS;
                            const cy = 200 - point.y * BASE_RADIUS;

                            return (
                                <circle
                                    key={"point-" + index}
                                    cx={cx}
                                    cy={cy}
                                    r="5"
                                    fill={point.hit ? "green" : "red"}
                                />
                            );
                        })}
                    </svg>
                </div>

                {/* Форма */}
                <form className="w-full mb-8">
                    <div className="flex flex-col items-center gap-6">

                        {/* X */}
                        <div className="text-center">
                            <label className="block font-bold text-lg mb-3">Изменение X</label>
                            <div className="flex flex-wrap justify-center gap-3 tablet:gap-5 desktop:gap-5">
                                {[-3, -2, -1, 0, 1, 2, 3, 4, 5].map((value) => (
                                    <div key={"x-" + value} className="flex items-center gap-2 basis-1/4 desktop:basis-auto tablet:basis-auto">
                                        <RadioButton
                                            value={value}
                                            onChange={(e) => {
                                                dispatch(setX(Number(e.value)));
                                                setErrorX(null);
                                            }}
                                            checked={x === Number(value)}
                                        />
                                        <label className="text-md font-bold">{value}</label>
                                    </div>
                                ))}
                            </div>
                            {errorX && <p className="text-red-500 font-bold mt-2">{errorX}</p>}
                        </div>

                        {/* Y */}
                        <div className="text-center">
                            <label className="block font-bold text-lg mb-3">Изменение Y ({y})</label>
                            <Slider
                                value={y}
                                onChange={(e) => {
                                    dispatch(setY(e.value));
                                    setErrorY(null);
                                }}
                                min={-3}
                                max={5}
                                step={0.1}
                                className="w-70"
                            />
                            <div className="flex mt-2 justify-between text-md font-bold">
                                {[-3, -2, -1, 0, 1, 2, 3, 4, 5].map((value) => (
                                    <div key={"y-" + value} className="flex flex-col items-center w-0">
                                        <span className=''>|</span>
                                        <span className="whitespace-nowrap">{value}</span>
                                    </div>

                                ))}
                            </div>
                            {errorY && <p className="text-red-500 font-bold mt-2">{errorY}</p>}
                        </div>

                        {/* R */}
                        <div className="text-center">
                            <label className="block font-bold text-lg">Изменение R</label>
                            <div className="flex flex-wrap justify-center gap-3 tablet:gap-5 desktop:gap-5">
                                {[-3, -2, -1, 0, 1, 2, 3, 4, 5].map((value) => (
                                    <div key={"r-" + value} className="flex items-center gap-2 basis-1/4 desktop:basis-auto tablet:basis-auto">
                                        <RadioButton
                                            value={value}
                                            onChange={(e) => {
                                                dispatch(setR(e.value));
                                                setErrorR(null);
                                            }}
                                            checked={r === value}
                                        />
                                        <label className="text-md font-bold">{value}</label>
                                    </div>
                                ))}
                            </div>
                            {errorR && <p className="text-red-500 font-bold mt-2">{errorR}</p>}
                        </div>

                        {/* Отправка */}
                        <div className="">
                            <button type="button" onClick={() => { handleCheck(x, y, r) }} className="px-10 py-3 border border-black bg-cyan-400 rounded-lg font-bold shadow-lg hover:bg-cyan-500 hover:scale-105 active:scale-90 transition-all">
                                Проверить
                            </button>
                        </div>

                        {mainError && <p className="text-red-500 font-bold">{mainError}</p>}
                    </div>
                </form>

                {/* Результаты */}
                <div className="max-h-72 overflow-y-auto border border-gray-300 shadow-inner rounded-md">
                    <table className="w-full border-collapse">
                        <thead className="sticky top-0 bg-gray-200 shadow-sm font-bold">
                            <tr>
                                <th className="p-3 border border-gray-400">x</th>
                                <th className="p-3 border border-gray-400">y</th>
                                <th className="p-3 border border-gray-400">r</th>
                                <th className="p-3 border border-gray-400">test</th>
                                <th className="p-3 border border-gray-400">current time</th>
                                <th className="p-3 border border-gray-400">request time (ms)</th>
                            </tr>
                        </thead>
                        <tbody className="text-center">
                            {paginatedResults.map((result, index) => (
                                <tr key={"result-" + index}>
                                    <td className="p-2 border border-gray-300">{result.x}</td>
                                    <td className="p-2 border border-gray-300">{result.y}</td>
                                    <td className="p-2 border border-gray-300">{result.r}</td>
                                    <td className="p-2 border border-gray-300">{result.hit ? "Попал" : "Мимо"}</td>
                                    <td className="p-2 border border-gray-300">{result.currentTime.slice(0, 19).replace("T", " ")}</td>
                                    <td className="p-2 border border-gray-300">{result.requestTime}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>

                <div className="flex mt-2 mb-2 items-center justify-center">
                    <button type="button" onClick={() => { if (page != 1) { setPage(page - 1) } }} className={`px-5 py-2 border border-black bg-cyan-400 rounded-lg font-bold shadow-lg hover:bg-cyan-500 hover:scale-105 active:scale-90 transition-all ${page === 1 ? "hidden" : ""}`}>Назад</button>
                    <label className="block font-bold text-lg px-10">{page}</label>
                    <button type="button" onClick={() => { if (page != maxPage) { setPage(page + 1) } }} className={`px-5 py-2 border border-black bg-cyan-400 rounded-lg font-bold shadow-lg hover:bg-cyan-500 hover:scale-105 active:scale-90 transition-all ${page === maxPage ? "hidden" : ""}`}>Вперёд</button>
                </div>

                <div className="flex items-center justify-center mt-6 mb-2">
                    <button type="button" onClick={() => { logout() }} className="px-10 py-3 border border-black bg-cyan-400 rounded-lg font-bold shadow-lg hover:bg-cyan-500 hover:scale-105 active:scale-90 transition-all">
                        Выйти
                    </button>
                </div>

            </div>
        </div>
    );
}

export default MainPage;
