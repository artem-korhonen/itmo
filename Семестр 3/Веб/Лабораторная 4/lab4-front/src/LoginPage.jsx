import { useState } from "react"
import { useNavigate } from "react-router-dom";

import { setToken } from "./auth";


function LoginPage() {
    const navigate = useNavigate();

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [errorUsername, setErrorUsername] = useState();
    const [errorPassword, setErrorPassword] = useState();
    const [mainError, setMainError] = useState();

    const hashText = async (text) => {
        const encoder = new TextEncoder();
        const data = encoder.encode(text);
        const hashBuffer = await crypto.subtle.digest('SHA-256', data);
        const hashArray = Array.from(new Uint8Array(hashBuffer));
        return hashArray.map(b => b.toString(16).padStart(2, '0')).join('');
    }

    const handleAuth = async (url) => {
        setMainError(null);
        if (!validateForm()) return;

        const hashPassword = await hashText(password);

        try {
            const response = await fetch(url, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ username, password: hashPassword })
            });

            const data = await response.json().catch(() => null);

            if (!response.ok) {
                const errorMessage = data?.error;
                throw Error(errorMessage);
            }

            const token = response.headers.get("Authorization");
            if (!token) { throw Error("Токен не получен"); }

            setToken(token);
            navigate("/main");
        } catch (e) {
            if (e.message === "Failed to fetch") {
                setMainError("Сервер недоступен. Проверьте соединение.");
            } else {
                setMainError(e.message);
            }
        }
    };

    const login = async () => {
        handleAuth("/api/auth/login", "Ошибка входа")
    };

    const register = async () => {
        handleAuth("/api/auth/register", "Ошибка регистрации")
    };

    const validateForm = () => {
        let test = true;

        if (username === "" || username === null) {
            setErrorUsername("Отсутствует имя пользователя");
            test = false;
        } else {
            setErrorUsername(null);
        }

        if (password === "" || password === null) {
            setErrorPassword("Отсутствует пароль");
            test = false;
        } else if (password.length < 8) {
            setErrorPassword("Пароль должен содержать не меньше 8 символов");
            test = false;
        } else if (!/[a-zа-я]/.test(password)) {
            setErrorPassword("Пароль должен содержать хотя бы одну строчную букву");
            test = false;
        } else if (!/[A-ZА-Я]/.test(password)) {
            setErrorPassword("Пароль должен содержать хотя бы одну заглавную букву");
            test = false;
        } else if (!/\d/.test(password)) {
            setErrorPassword("Пароль должен содержать хотя бы одну цифру");
            test = false;
        } else if (!/[!@#№$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/.test(password)) {
            setErrorPassword("Пароль должен содержать хотя бы один специальный символ");
            test = false;
        } else {
            setErrorPassword(null);
        }

        return test;
    }

    return (
        <div className="min-h-screen bg-cover bg-center px-10 py-10 bg-[url('minecraft.jpg')] font-serif">
            <div className="max-w-5xl mx-auto bg-white p-5 border rounded-xl shadow-2xl shadow-black">
                {/* Заголовок */}
                <div className="
                    grid
                    grid-cols-2
                    tablet:grid-cols-3
                    desktop:grid-cols-5
                    mb-5
                    text-center
                    font-bold">
                    <div className="p-3 border bg-gray-300">Корхонен Артём Андреевич</div>
                    <div className="p-3 border bg-gray-300">466298</div>
                    <div className="p-3 border bg-gray-300">P3232</div>
                    <div className="p-3 border bg-gray-300">Лабораторная №4</div>
                    <div className="p-3 border bg-gray-300">Вариант №52511</div>
                </div>


                {/* Контейнер формы */}
                <div className="flex justify-center py-3">
                    <form className="w-full max-w-sm flex flex-col gap-6 items-center border border-black p-8 bg-gray-50 rounded-lg shadow-lg">
                        <h2 className="text-2xl font-bold uppercase tracking-widest mb-4">Вход в систему</h2>

                        {/* Поле Логин */}
                        <div className="w-full">
                            <label className="block font-bold text-lg mb-2 text-center">Имя пользователя</label>
                            <input
                                type="text"
                                className="w-full p-2 border border-black rounded shadow-inner focus:ring-2 focus:ring-cyan-400 outline-none transition-all"
                                placeholder="Введите имя пользователя..."
                                value={username}
                                onChange={(e) => {
                                    setUsername(e.target.value);
                                    setErrorUsername(null);
                                }}
                            />
                            {errorUsername && <p className="text-red-500 font-bold mt-2">{errorUsername}</p>}
                        </div>

                        {/* Поле Пароль */}
                        <div className="w-full">
                            <label className="block font-bold text-lg mb-2 text-center">Пароль</label>
                            <input
                                type="password"
                                className="w-full p-2 border border-black rounded shadow-inner focus:ring-2 focus:ring-cyan-400 outline-none transition-all"
                                placeholder="Введите пароль..."
                                value={password}
                                onChange={(e) => {
                                    setPassword(e.target.value);
                                    setErrorPassword(null);
                                }}
                            />
                            {errorPassword && <p className="text-red-500 font-bold mt-2">{errorPassword}</p>}
                        </div>

                        {/* Кнопки */}
                        <div className="flex flex-col w-full gap-4 mt-4">
                            <button
                                type="button"
                                onClick={login}
                                className="w-full py-3 border border-black bg-cyan-400 rounded-lg font-bold shadow-lg hover:bg-cyan-500 hover:scale-105 active:scale-90 transition-all"
                            >
                                Войти
                            </button>

                            <button
                                type="button"
                                onClick={register}
                                className="w-full py-3 border border-black bg-gray-200 rounded-lg font-bold shadow-lg hover:bg-gray-300 hover:scale-105 active:scale-90 transition-all"
                            >
                                Регистрация
                            </button>

                            {mainError && <p className="text-red-500 font-bold mt-2">{mainError}</p>}
                        </div>
                    </form>
                </div>
            </div>
        </div>
    )
}


export default LoginPage;