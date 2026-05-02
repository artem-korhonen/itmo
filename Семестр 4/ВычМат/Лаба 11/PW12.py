# /// script
# dependencies = ["marimo", "numpy", "scipy", "sympy", "matplotlib", "diffrax", "jax"]
# ///

import marimo

__generated_with = "0.23.3"
app = marimo.App(width="medium")


@app.cell
def _():
    import marimo as mo

    return (mo,)


@app.cell(hide_code=True)
def _(mo):
    s_id = mo.ui.number(
        start=330000,
        stop=489925,
        step=1,
        label="**Введите номер студенческого билета:**"
    )
    s_id
    return (s_id,)


@app.cell
def _(s_id):
    import sys
    import numpy as np
    import matplotlib.pyplot as plt
    import matplotlib
    matplotlib.rcParams["figure.dpi"] = 110

    # Номер студента задаётся через CLI: marimo run pw12_ode.py -- --student-id 42
    # При запуске без аргументов используется значение по умолчанию.
    # cli = mo.cli_args()
    student_id = int(s_id.value)

    # Индивидуальный параметр: P_in из диапазона [10, 29.5] с шагом 0.5
    # Каждый студент получает уникальное значение по своему номеру
    _P_values = np.arange(10.0, 30.0, 0.5)
    P_in_individual = float(_P_values[student_id % len(_P_values)])

    _F0_values = np.arange(0.30, 0.80, 0.025)   
    _k_values  = np.arange(30.0, 80.0, 2.5)
    F0_individual = float(_F0_values[student_id % len(_F0_values)])
    k_individual  = float(_k_values[(student_id * 7) % len(_k_values)])
    return F0_individual, P_in_individual, k_individual, np, plt, student_id


@app.cell(hide_code=True)
def _(P_in_individual, mo, student_id):
    mo.md(f"""
    # Практическая работа 12
    ## Численные методы решения ОДУ: реализация и сравнение

    **Студент:** ID = `{student_id}` &nbsp;&nbsp; **Индивидуальный параметр:** P_in = `{P_in_individual}` Вт

    ---

    В этой работе мы будем решать два уравнения из реальной инженерной практики:

    1. **Задача 1 (ОДУ 1-го порядка)** — тепловая модель электронного компонента (CPU/силовой ключ).
       Такие уравнения используются в контроллерах вентиляторов для *предсказания* перегрева
       до того, как датчик температуры достигнет порогового значения.

    2. **Задача 2 (ОДУ 2-го порядка → система)** — модель позиционирования головки жёсткого диска (HDD).
       Точность позиционирования головки HDD напрямую определяет плотность записи дорожек.

    ---

    **Структура работы:**

    | Раздел | Содержание | Характер |
    |--------|-----------|----------|
    | 1 | Тепловая модель: постановка + аналитическое решение | Демонстрация |
    | 2 | Ручные реализации: Эйлер, Хойн, RK4 | Демонстрация |
    | 3 | SciPy solve_ivp: RK45, DOP853, Radau, LSODA | Демонстрация |
    | 4 | SymPy: символьное решение | Демонстрация |
    | 5 | Модель головки HDD: постановка | Демонстрация |
    | 6 | Многошаговые методы: Адамс-Башфорт 4, Адамс-Милтон 4 | Демонстрация |
    | **7** | **Основное задание: сравнительный анализ + экспорт результатов** | **Задание** |
    | 8* | Опциональное: diffrax (JAX-based решатели) | Дополнительно |

    > ⚠️ **P_in** в вашем варианте равен **{P_in_individual} Вт**. Используйте именно это значение
    > во всех расчётах раздела 7. В демонстрационных разделах 1–4 используется интерактивный слайдер.

    **Настоятельная просьба выполнять работу самостоятельно.** Только вы ответственны за получаемые
    знания. Заимствование работ у коллег или AI-ассистентов нарушает Кодекс студента ИТМО
    и лишает вас реального понимания материала.
    """)
    return


@app.cell(hide_code=True)
def _(mo):
    mo.md(r"""
    ## Раздел 1. Тепловая модель электронного компонента

    ### 1.1 Физическая постановка задачи

    Рассмотрим электронный компонент (CPU, силовой ключ, драйвер двигателя) как
    сосредоточенную тепловую ёмкость. Баланс тепловых потоков даёт ОДУ первого порядка:

    $$
    C_{th}\,\frac{dT}{dt} = P_{in} - \frac{T - T_{amb}}{R_{th}}
    $$

    где:
    - $T(t)$ — температура компонента, °C
    - $C_{th}$ — тепловая ёмкость, Дж/К (сколько энергии нужно, чтобы нагреть компонент на 1 К)
    - $P_{in}$ — мощность тепловыделения (рассеиваемая мощность процессора), Вт
    - $R_{th}$ — тепловое сопротивление компонент–окружающая среда, К/Вт
    - $T_{amb}$ — температура окружающей среды, °C

    Это линейное ОДУ с постоянными коэффициентами. Перепишем в стандартной форме:

    $$
    \frac{dT}{dt} = \frac{P_{in}}{C_{th}} - \frac{T - T_{amb}}{R_{th}\,C_{th}}
    $$

    ### 1.2 Аналитическое решение

    Уравнение линейное первого порядка с постоянной правой частью. Его аналитическое решение при
    начальном условии $T(0) = T_0$:

    $$
    \boxed{T(t) = T_{amb} + P_{in}\,R_{th}\!\left(1 - e^{-t/\tau}\right) + (T_0 - T_{amb})\,e^{-t/\tau}}
    $$

    где $\tau = R_{th}\,C_{th}$ — **постоянная времени** системы (время выхода на ~63% установившегося значения).

    Установившееся значение (при $t \to \infty$):
    $$
    T_{\infty} = T_{amb} + P_{in}\,R_{th}
    $$

    > **Физический смысл $\tau$:** контроллер вентилятора «знает» $\tau$ компонента и может
    > предсказать температуру через 2–3 секунды, не дожидаясь показаний датчика.
    """)
    return


@app.cell(hide_code=True)
def _(mo):
    mo.md("""
    ### 1.3 Интерактивный демонстрационный расчёт
    """)
    return


@app.cell
def _(mo):
    # Интерактивные параметры для демонстрации
    slider_Pin = mo.ui.slider(5, 40, value=15, step=0.5, label="P_in, Вт")
    slider_Rth = mo.ui.slider(0.5, 15.0, value=5.0, step=0.5, label="R_th, К/Вт")
    slider_Cth = mo.ui.slider(0.5, 10.0, value=2.5, step=0.5, label="C_th, Дж/К")
    slider_T0  = mo.ui.slider(20, 60, value=25, step=1, label="T₀, °C")
    slider_Tamb= mo.ui.slider(15, 40, value=25, step=1, label="T_amb, °C")
    mo.vstack([
        mo.md("Переместите слайдеры и наблюдайте за изменением решения:"),
        slider_Pin, slider_Rth, slider_Cth, slider_T0, slider_Tamb
    ])
    return slider_Cth, slider_Pin, slider_Rth, slider_T0, slider_Tamb


@app.cell(hide_code=True)
def _(np, plt, slider_Cth, slider_Pin, slider_Rth, slider_T0, slider_Tamb):
    _P   = slider_Pin.value
    _R   = slider_Rth.value
    _C   = slider_Cth.value
    _T0  = slider_T0.value
    _Ta  = slider_Tamb.value
    _tau = _R * _C

    _t = np.linspace(0, 5 * _tau, 500)
    _T_inf = _Ta + _P * _R
    _T_an  = _Ta + _P * _R * (1 - np.exp(-_t / _tau)) + (_T0 - _Ta) * np.exp(-_t / _tau)

    _fig, _ax = plt.subplots(figsize=(8, 4))
    _ax.plot(_t, _T_an, "b-", lw=2, label="Аналитическое решение")
    _ax.axhline(_T_inf, color="r", ls="--", lw=1, label=f"$T_{{\\infty}}$ = {_T_inf:.1f} °C")
    _ax.axvline(_tau,   color="g", ls=":",  lw=1, label=f"$\\tau$ = {_tau:.2f} с")
    _ax.set_xlabel("t, с")
    _ax.set_ylabel("T, °C")
    _ax.set_title(f"Тепловая модель: P_in={_P} Вт, R_th={_R} К/Вт, C_th={_C} Дж/К")
    _ax.legend()
    _ax.grid(True, alpha=0.3)
    plt.tight_layout()
    plt.gca()
    return


@app.cell(hide_code=True)
def _(mo):
    mo.md(r"""
    ## Раздел 2. Ручные реализации решателей

    Реализуем три метода для задачи Коши $\dot{T} = f(t, T)$:

    - **Метод Эйлера** (явный, 1-й порядок): $T_{n+1} = T_n + h\,f(t_n, T_n)$
    - **Метод Хойна** (улучшенный Эйлер, 2-й порядок):
      $k_1 = f(t_n, T_n)$, $k_2 = f(t_{n+1}, T_n + h k_1)$, $T_{n+1} = T_n + \frac{h}{2}(k_1 + k_2)$
    - **RK4** (4-й порядок): классический метод Рунге-Кутты

    Все три метода реализованы в **обобщённой форме**: принимают произвольную функцию $f(t, y)$,
    поэтому подходят для любой задачи Коши.
    """)
    return


@app.cell
def _(np):
    # ── Параметры для демонстрационного расчёта (фиксированные) ──────────────────
    DEMO_P_in  = 15.0   # Вт
    DEMO_R_th  = 5.0    # К/Вт
    DEMO_C_th  = 2.5    # Дж/К
    DEMO_T_amb = 25.0   # °C
    DEMO_T0    = 25.0   # °C  (начальная температура = температура окружающей среды)
    DEMO_t_end = 5 * DEMO_R_th * DEMO_C_th  # 5 постоянных времени

    # Сетка с "крупным" шагом, чтобы ошибки методов были заметны
    N_DEMO = 50
    t_demo = np.linspace(0, DEMO_t_end, N_DEMO)

    # Правая часть ОДУ для тепловой задачи
    def thermal_rhs(t, T, P_in=DEMO_P_in, R_th=DEMO_R_th, C_th=DEMO_C_th, T_amb=DEMO_T_amb):
        return (P_in - (T - T_amb) / R_th) / C_th

    # Аналитическое решение
    def thermal_analytical(t, P_in=DEMO_P_in, R_th=DEMO_R_th, C_th=DEMO_C_th,
                           T_amb=DEMO_T_amb, T0=DEMO_T0):
        tau = R_th * C_th
        return T_amb + P_in * R_th * (1 - np.exp(-t / tau)) + (T0 - T_amb) * np.exp(-t / tau)

    return (
        DEMO_C_th,
        DEMO_P_in,
        DEMO_R_th,
        DEMO_T0,
        DEMO_T_amb,
        DEMO_t_end,
        t_demo,
        thermal_analytical,
        thermal_rhs,
    )


@app.cell
def _(np):
    # ── Реализации методов ────────────────────────────────────────────────────────

    def euler(f, t, y0, **kwargs):
        """Явный метод Эйлера. f(t, y, **kwargs), t — массив узлов."""
        y = np.zeros(len(t))
        y[0] = y0
        for i in range(1, len(t)):
            h = t[i] - t[i - 1]
            y[i] = y[i - 1] + h * f(t[i - 1], y[i - 1], **kwargs)
        return y

    def heun(f, t, y0, **kwargs):
        """Метод Хойна (улучшенный Эйлер, 2-й порядок)."""
        y = np.zeros(len(t))
        y[0] = y0
        for i in range(1, len(t)):
            h  = t[i] - t[i - 1]
            k1 = f(t[i - 1], y[i - 1], **kwargs)
            k2 = f(t[i],     y[i - 1] + h * k1, **kwargs)
            y[i] = y[i - 1] + h * 0.5 * (k1 + k2)
        return y

    def rk4(f, t, y0, **kwargs):
        """Классический метод Рунге-Кутты 4-го порядка."""
        y = np.zeros(len(t))
        y[0] = y0
        for i in range(1, len(t)):
            h  = t[i] - t[i - 1]
            k1 = f(t[i - 1],         y[i - 1],             **kwargs)
            k2 = f(t[i - 1] + h/2,   y[i - 1] + h/2 * k1, **kwargs)
            k3 = f(t[i - 1] + h/2,   y[i - 1] + h/2 * k2, **kwargs)
            k4 = f(t[i],             y[i - 1] + h   * k3, **kwargs)
            y[i] = y[i - 1] + (h / 6) * (k1 + 2*k2 + 2*k3 + k4)
        return y

    return euler, heun, rk4


@app.cell
def _(
    DEMO_T0,
    euler,
    heun,
    np,
    plt,
    rk4,
    t_demo,
    thermal_analytical,
    thermal_rhs,
):
    _T_an   = thermal_analytical(t_demo)
    _T_eul  = euler(thermal_rhs, t_demo, DEMO_T0)
    _T_heun = heun( thermal_rhs, t_demo, DEMO_T0)
    _T_rk4  = rk4(  thermal_rhs, t_demo, DEMO_T0)

    _fig, (_ax1, _ax2) = plt.subplots(1, 2, figsize=(12, 4))

    # Левый — сами решения
    _ax1.plot(t_demo, _T_an,   "k-",  lw=2,   label="Аналитическое")
    _ax1.plot(t_demo, _T_eul,  "r--", lw=1.5, label="Эйлер")
    _ax1.plot(t_demo, _T_heun, "g--", lw=1.5, label="Хойн")
    _ax1.plot(t_demo, _T_rk4,  "b--", lw=1.5, label="RK4")
    _ax1.set_xlabel("t, с");  _ax1.set_ylabel("T, °C")
    _ax1.set_title("Решения: ручные реализации vs аналитика")
    _ax1.legend(); _ax1.grid(True, alpha=0.3)

    # Правый — абсолютные ошибки
    _ax2.semilogy(t_demo[1:], np.abs(_T_eul[1:]  - _T_an[1:]), "r-",  label="Ошибка Эйлера")
    _ax2.semilogy(t_demo[1:], np.abs(_T_heun[1:] - _T_an[1:]), "g-",  label="Ошибка Хойна")
    _ax2.semilogy(t_demo[1:], np.abs(_T_rk4[1:]  - _T_an[1:]), "b-",  label="Ошибка RK4")
    _ax2.set_xlabel("t, с"); _ax2.set_ylabel("|ошибка|, °C (лог. шкала)")
    _ax2.set_title("Абсолютная ошибка методов")
    _ax2.legend(); _ax2.grid(True, alpha=0.3)

    plt.tight_layout()
    plt.gca()
    return


@app.cell(hide_code=True)
def _(mo):
    mo.md(r"""
    ## Раздел 3. SciPy `solve_ivp`: адаптивные решатели

    `scipy.integrate.solve_ivp` предоставляет несколько методов:

    | Метод | Тип | Порядок | Когда использовать |
    |-------|-----|---------|-------------------|
    | `RK45` | явный | 4–5 | нежёсткие задачи, общее использование |
    | `DOP853` | явный | 8 | нежёсткие, нужна высокая точность |
    | `Radau` | неявный | 5 | **жёсткие** задачи (stiff ODEs) |
    | `LSODA` | авто | — | автоматическое определение жёсткости |

    Ключевое преимущество адаптивных методов: шаг $h$ меняется автоматически,
    чтобы удержать локальную ошибку ниже заданного допуска `rtol`/`atol`.
    """)
    return


@app.cell
def _(
    DEMO_C_th,
    DEMO_P_in,
    DEMO_R_th,
    DEMO_T0,
    DEMO_T_amb,
    DEMO_t_end,
    np,
    plt,
    thermal_analytical,
):
    from scipy.integrate import solve_ivp

    # solve_ivp ожидает f(t, y) где y — вектор
    def _thermal_ivp(t, y):
        T = y[0]
        return [(DEMO_P_in - (T - DEMO_T_amb) / DEMO_R_th) / DEMO_C_th]

    _t_eval = np.linspace(0, DEMO_t_end, 300)
    _T_an   = thermal_analytical(_t_eval)

    _methods = ["RK45", "DOP853", "Radau", "LSODA"]
    _colors  = ["royalblue", "darkorange", "green", "purple"]
    _styles  = ["-", "--", "-.", ":"]

    _fig, (_ax1, _ax2) = plt.subplots(1, 2, figsize=(12, 4))
    _ax1.plot(_t_eval, _T_an, "k-", lw=2.5, label="Аналитическое", zorder=5)
    _nfev_info = {}

    for _m, _c, _ls in zip(_methods, _colors, _styles):
        _sol = solve_ivp(_thermal_ivp, [0, DEMO_t_end], [DEMO_T0],
                         method=_m, t_eval=_t_eval, rtol=1e-6, atol=1e-9)
        _T_m = _sol.y[0]
        _ax1.plot(_sol.t, _T_m, color=_c, ls=_ls, lw=1.5, label=_m)
        _err = np.abs(_T_m - thermal_analytical(_sol.t))
        _ax2.semilogy(_sol.t[1:], _err[1:], color=_c, ls=_ls, label=f"{_m} (nfev={_sol.nfev})")
        _nfev_info[_m] = _sol.nfev

    _ax1.set_xlabel("t, с"); _ax1.set_ylabel("T, °C")
    _ax1.set_title("SciPy solve_ivp: сравнение методов")
    _ax1.legend(); _ax1.grid(True, alpha=0.3)

    _ax2.set_xlabel("t, с"); _ax2.set_ylabel("|ошибка|, °C")
    _ax2.set_title("Абсолютная ошибка (rtol=1e-6, atol=1e-9)")
    _ax2.legend(fontsize=8); _ax2.grid(True, alpha=0.3)

    plt.tight_layout()
    plt.gca()
    return (solve_ivp,)


@app.cell(hide_code=True)
def _(mo):
    mo.md(r"""
    ## Раздел 4. SymPy: символьное решение задачи Коши

    SymPy позволяет получить аналитическое решение в символьном виде.
    Это полезно для верификации ручных выкладок и для задач, где аналитическое решение
    не очевидно.
    """)
    return


@app.cell
def _(DEMO_C_th, DEMO_P_in, DEMO_R_th, DEMO_T0, DEMO_T_amb, plt, t_demo):
    import sympy as sp

    _t_s  = sp.Symbol("t", positive=True)
    _T_s  = sp.Function("T")(_t_s)
    _P, _R, _C, _Ta = sp.symbols("P_in R_th C_th T_amb", positive=True)

    _ode_sym = sp.Eq(
        _T_s.diff(_t_s),
        (_P - (_T_s - _Ta) / _R) / _C
    )
    print("ОДУ:", _ode_sym)

    _sol_sym = sp.dsolve(_ode_sym, _T_s,
                         ics={_T_s.subs(_t_s, 0): sp.Symbol("T_0")})
    print("Общее решение:")
    sp.pprint(_sol_sym)

    # Подставим числовые значения
    _sol_num = _sol_sym.subs({
        _P: DEMO_P_in, _R: DEMO_R_th, _C: DEMO_C_th,
        _Ta: DEMO_T_amb, sp.Symbol("T_0"): DEMO_T0
    })
    _T_func = sp.lambdify(_t_s, _sol_num.rhs, "numpy")
    _T_sympy = _T_func(t_demo)

    _fig, _ax = plt.subplots(figsize=(8, 4))
    _ax.plot(t_demo, _T_sympy, "m-", lw=2, label="SymPy (символьное → численное)")
    _ax.set_xlabel("t, с"); _ax.set_ylabel("T, °C")
    _ax.set_title("Аналитическое решение через SymPy")
    _ax.legend(); _ax.grid(True, alpha=0.3)
    plt.tight_layout()
    plt.gca()
    return


@app.cell(hide_code=True)
def _(mo):
    mo.md(r"""
    ## Раздел 5. Модель позиционирования головки HDD

    ### 5.1 Физическая постановка

    Рычаг головки жёсткого диска — это механическая система с упругостью и вязким
    демпфированием (Voice Coil Motor). Уравнение движения:

    $$
    m\,\ddot{x} + c\,\dot{x} + k\,x = F(t)
    $$

    где:
    - $x(t)$ — линейное отклонение головки от целевой дорожки, мкм
    - $m$ — приведённая масса рычага, кг
    - $c$ — коэффициент вязкого демпфирования, Н·с/м
    - $k$ — жёсткость упругой подвески, Н/м
    - $F(t)$ — управляющее усилие (ступенчатое при команде перемещения), Н

    ### 5.2 Приведение к системе первого порядка

    Вводим $x_1 = x$, $x_2 = \dot{x}$:

    $$
    \begin{cases}
    \dot{x}_1 = x_2 \\
    \dot{x}_2 = \dfrac{1}{m}\!\left(F(t) - c\,x_2 - k\,x_1\right)
    \end{cases}
    $$

    > **Важно:** `solve_ivp` и все ручные реализации работают только с системами
    > первого порядка. ОДУ 2-го (и выше) порядка **необходимо приводить к системе** — именно так.

    ### 5.3 Характеристики реальной системы

    Параметры типичного 3.5" HDD (упрощённые):
    - $m = 5 \cdot 10^{-3}$ кг, $c = 0.1$ Н·с/м, $k = 50$ Н/м
    - $F_0 = 0.5$ Н (ступенчатое управляющее воздействие)
    - Целевое смещение: $x^* = F_0 / k = 0.01$ м = 10 мм

    Коэффициент демпфирования: $\zeta = c / (2\sqrt{mk})$. При $\zeta < 1$ система
    **колебательная** (underdamped) — будет перерегулирование.
    """)
    return


@app.cell
def _(F0_individual, k_individual, np, plt):
    # ── Параметры HDD ──────────────────────────────────────────────────────────────
    HDD_m   = 5e-3     # кг
    HDD_c   = 0.1      # Н·с/м
    HDD_k   = k_individual     # Н/м
    HDD_F0  = F0_individual      # Н  (ступенчатое усилие)
    HDD_x0  = 0.0      # начальное положение, м
    HDD_v0  = 0.0      # начальная скорость, м/с
    HDD_t_end = 1.0    # с

    _zeta = HDD_c / (2 * np.sqrt(HDD_m * HDD_k))
    print(f"Коэффициент демпфирования ζ = {_zeta:.4f} ({'колебательный' if _zeta < 1 else 'апериодический'})")
    print(f"Целевое смещение x* = {HDD_F0/HDD_k*1000:.1f} мм")

    # Правая часть системы (y = [x, v])
    def hdd_rhs(t, y, m=HDD_m, c=HDD_c, k=HDD_k, F0=HDD_F0):
        x, v = y
        F = F0  # ступенчатое воздействие (включается в t=0)
        return [v, (F - c * v - k * x) / m]

    # Аналитическое решение для underdamped (ζ < 1)
    def hdd_analytical(t):
        omega_n = np.sqrt(HDD_k / HDD_m)        # собственная частота
        zeta    = HDD_c / (2 * np.sqrt(HDD_m * HDD_k))
        omega_d = omega_n * np.sqrt(1 - zeta**2) # собственная частота с демпфированием
        x_inf   = HDD_F0 / HDD_k
        # x(t) = x_inf * (1 - e^{-ζω_n t} * (cos(ω_d t) + (ζ/√(1-ζ²)) sin(ω_d t)))
        envelope = np.exp(-zeta * omega_n * t)
        x = x_inf * (1 - envelope * (np.cos(omega_d * t) +
                                      zeta / np.sqrt(1 - zeta**2) * np.sin(omega_d * t)))
        return x

    _t_plot = np.linspace(0, HDD_t_end, 500)
    _x_an   = hdd_analytical(_t_plot)
    _x_inf  = HDD_F0 / HDD_k

    _fig, _ax = plt.subplots(figsize=(9, 4))
    _ax.plot(_t_plot, _x_an * 1000, "k-", lw=2, label="Аналитическое")
    _ax.axhline(_x_inf * 1000, color="r", ls="--", lw=1, label=f"$x^*$ = {_x_inf*1000:.1f} мм")
    _ax.axhline(_x_inf * 1000 * 1.02, color="gray", ls=":", lw=1)
    _ax.axhline(_x_inf * 1000 * 0.98, color="gray", ls=":", lw=1, label="±2% коридор")
    _ax.set_xlabel("t, с"); _ax.set_ylabel("x, мм")
    _ax.set_title("Модель позиционирования головки HDD")
    _ax.legend(); _ax.grid(True, alpha=0.3)
    plt.tight_layout()
    plt.gca()
    return (
        HDD_F0,
        HDD_c,
        HDD_k,
        HDD_m,
        HDD_t_end,
        HDD_v0,
        HDD_x0,
        hdd_analytical,
        hdd_rhs,
    )


@app.cell(hide_code=True)
def _(mo):
    mo.md(r"""
    ## Раздел 6. Многошаговые методы: Адамс-Башфорт 4 и Адамс-Милтон 4

    Многошаговые методы используют **несколько предыдущих шагов** для вычисления следующего,
    что позволяет достичь высокого порядка точности без вычисления нескольких правых частей
    на одном шаге (как в RK4).

    **Адамс-Башфорт 4 (AB4)** — явный, 4-й порядок:
    $$
    y_{n+1} = y_n + \frac{h}{24}\bigl(55f_n - 59f_{n-1} + 37f_{n-2} - 9f_{n-3}\bigr)
    $$

    **Адамс-Милтон 4 (AM4)** — неявный предиктор-корректор:
    $$
    y_{n+1}^* = \text{AB4 (предиктор)}
    $$
    $$
    y_{n+1} = y_n + \frac{h}{24}\bigl(9f_{n+1}^* + 19f_n - 5f_{n-1} + f_{n-2}\bigr)
    $$

    > **Инициализация:** первые 3 шага (n=0,1,2) нельзя вычислить по формуле AB4 —
    > не хватает предыстории. Их необходимо инициализировать методом **RK4**.
    > Использование Эйлера для инициализации приводит к значительной потере точности.
    """)
    return


@app.cell
def _(
    HDD_F0,
    HDD_k,
    HDD_t_end,
    HDD_v0,
    HDD_x0,
    hdd_analytical,
    hdd_rhs,
    np,
    plt,
):
    # ── Реализация AB4 и AB4-AM4 для системы ОДУ ─────────────────────────────────

    def adams_bashforth4_system(f, t, y0_vec):
        """
        Адамс-Башфорт 4-го порядка для системы ОДУ.
        y0_vec — начальное условие (вектор).
        Первые 3 шага инициализируются RK4.
        """
        n = len(t)
        d = len(y0_vec)
        y = np.zeros((n, d))
        y[0] = y0_vec

        # Инициализация: первые 3 шага методом RK4
        for i in range(1, min(4, n)):
            h  = t[i] - t[i - 1]
            k1 = np.array(f(t[i-1],       y[i-1]))
            k2 = np.array(f(t[i-1] + h/2, y[i-1] + h/2 * k1))
            k3 = np.array(f(t[i-1] + h/2, y[i-1] + h/2 * k2))
            k4 = np.array(f(t[i],         y[i-1] + h   * k3))
            y[i] = y[i-1] + (h / 6) * (k1 + 2*k2 + 2*k3 + k4)

        # Основные шаги AB4
        for i in range(4, n):
            h   = t[i] - t[i - 1]
            fn  = np.array(f(t[i-1], y[i-1]))
            fn1 = np.array(f(t[i-2], y[i-2]))
            fn2 = np.array(f(t[i-3], y[i-3]))
            fn3 = np.array(f(t[i-4], y[i-4]))
            y[i] = y[i-1] + (h / 24) * (55*fn - 59*fn1 + 37*fn2 - 9*fn3)

        return y

    def adams_moulton4_system(f, t, y0_vec):
        """
        Предиктор-корректор AB4-AM4 (Адамс-Милтон 4) для системы ОДУ.
        """
        n = len(t)
        d = len(y0_vec)
        y = np.zeros((n, d))
        y[0] = y0_vec

        # Инициализация: первые 3 шага методом RK4
        for i in range(1, min(4, n)):
            h  = t[i] - t[i - 1]
            k1 = np.array(f(t[i-1],       y[i-1]))
            k2 = np.array(f(t[i-1] + h/2, y[i-1] + h/2 * k1))
            k3 = np.array(f(t[i-1] + h/2, y[i-1] + h/2 * k2))
            k4 = np.array(f(t[i],         y[i-1] + h   * k3))
            y[i] = y[i-1] + (h / 6) * (k1 + 2*k2 + 2*k3 + k4)

        # Предиктор-корректор
        for i in range(4, n):
            h   = t[i] - t[i - 1]
            fn  = np.array(f(t[i-1], y[i-1]))
            fn1 = np.array(f(t[i-2], y[i-2]))
            fn2 = np.array(f(t[i-3], y[i-3]))
            fn3 = np.array(f(t[i-4], y[i-4]))
            # Предиктор (AB4)
            y_pred = y[i-1] + (h / 24) * (55*fn - 59*fn1 + 37*fn2 - 9*fn3)
            # Корректор (AM4)
            fn_pred = np.array(f(t[i], y_pred))
            y[i] = y[i-1] + (h / 24) * (9*fn_pred + 19*fn - 5*fn1 + fn2)

        return y

    # ── Расчёт и сравнение ────────────────────────────────────────────────────────
    _N    = 200
    _t_hdd = np.linspace(0, HDD_t_end, _N)
    _y0   = np.array([HDD_x0, HDD_v0])

    _y_ab4  = adams_bashforth4_system(hdd_rhs, _t_hdd, _y0)
    _y_am4  = adams_moulton4_system(  hdd_rhs, _t_hdd, _y0)
    _x_an   = hdd_analytical(_t_hdd)
    _x_inf  = HDD_F0 / HDD_k

    _fig, (_ax1, _ax2) = plt.subplots(1, 2, figsize=(12, 4))

    _ax1.plot(_t_hdd, _x_an * 1000,         "k-",  lw=2,   label="Аналитическое")
    _ax1.plot(_t_hdd, _y_ab4[:, 0] * 1000,  "r--", lw=1.5, label="AB4")
    _ax1.plot(_t_hdd, _y_am4[:, 0] * 1000,  "b-.", lw=1.5, label="AB4-AM4")
    _ax1.axhline(_x_inf * 1000, color="gray", ls=":", lw=1)
    _ax1.set_xlabel("t, с"); _ax1.set_ylabel("x, мм")
    _ax1.set_title("Многошаговые методы: позиционирование HDD")
    _ax1.legend(); _ax1.grid(True, alpha=0.3)

    _err_ab4 = np.abs(_y_ab4[:, 0] - _x_an)
    _err_am4 = np.abs(_y_am4[:, 0] - _x_an)
    _ax2.semilogy(_t_hdd[4:], _err_ab4[4:], "r-",  label="Ошибка AB4")
    _ax2.semilogy(_t_hdd[4:], _err_am4[4:], "b-.", label="Ошибка AB4-AM4")
    _ax2.set_xlabel("t, с"); _ax2.set_ylabel("|ошибка|, м")
    _ax2.set_title("Ошибка многошаговых методов")
    _ax2.legend(); _ax2.grid(True, alpha=0.3)

    plt.tight_layout()
    plt.gca()
    return adams_bashforth4_system, adams_moulton4_system


@app.cell(hide_code=True)
def _(F0_individual, P_in_individual, k_individual, mo, student_id):
    mo.md(f"""
    ## Раздел 7. Основное задание

    **Ваши индивидуальные параметры** (student_id = `{student_id}`):

    | Параметр | Значение | Описание |
    |----------|----------|----------|
    | `P_in` | **{P_in_individual} Вт** | Мощность тепловыделения (задача 1) |
    | `F0` | **{F0_individual} Н** | Управляющее усилие головки HDD (задача 2) |
    | `k` | **{k_individual} Н/м** | Жёсткость подвески HDD (задача 2) |

    > ⚠️ Не меняйте эти значения. Они определяются вашим номером ИСУ и будут
    > проверены автоматически при сдаче.

    ### ⚠️ Ловушки — проверьте себя перед сдачей:

    1. В аналитическом решении тепловой задачи $T_{{amb}}$ участвует **дважды**: в установившемся
       значении и в начальном условии. Типичная ошибка:
       $T(t) = P_{{in}} R_{{th}}(1-e^{{-t/\\tau}}) + T_0\\,e^{{-t/\\tau}}$ — неверно при $T_0 \\ne T_{{amb}}$.
    2. HDD-система передаётся в решатель как **вектор** $[x, v]$, не как скаляр.
       Передача ОДУ 2-го порядка напрямую приведёт к ошибке размерности.
    3. При вычислении $t_s$ условие ±2% должно выполняться **для всех** последующих
       моментов, а не только в одной точке.
    """)
    return


@app.cell(hide_code=True)
def _(mo):
    mo.md(r"""
    ### Задание 7.1 — Тепловая задача: сравнение методов

    **a)** Решите задачу на интервале $[0,\; 5\tau]$ при $N = 30$ узлах четырьмя способами:
    - Эйлер, Хойн, RK4 (ручные реализации из раздела 2)
    - `solve_ivp` c методом `RK45`

    **b)** Вычислите аналитическое решение в тех же узлах. Заполните таблицу:

    | Метод | Макс. ошибка | Ошибка в конечной точке | Число вызовов f |
    |-------|-------------|------------------------|-----------------|
    | Эйлер | ? | ? | N−1 |
    | Хойн  | ? | ? | 2(N−1) |
    | RK4   | ? | ? | 4(N−1) |
    | RK45  | ? | ? | `sol.nfev` |

    **c)** Постройте два графика: решения и ошибки (логарифмическая шкала по y).

    **d)** При каком шаге $h$ метод Эйлера даёт ошибку в конечной точке менее 0.1 °C?
    Оцените аналитически и проверьте расчётом.
    """)
    return


@app.cell
def _(P_in_individual, euler, heun, np, plt, rk4, solve_ivp):
    # ── Задание 7.1: впишите ваш код здесь ───────────────────────────────────────
    # Параметры (НЕ МЕНЯТЬ)
    R_th  = 5.0
    C_th  = 2.5
    T_amb = 25.0
    T0    = 25.0
    P_in  = P_in_individual   # ← индивидуальный параметр

    tau        = R_th * C_th
    t_end_task = 5 * tau
    N_task     = 30
    t_task     = np.linspace(0, t_end_task, N_task)

    # TODO: вычислите аналитическое решение в узлах t_task
    T_analytical = T_amb + P_in * R_th * (1 - np.exp(-t_task / tau))

    # TODO: примените euler(), heun(), rk4() из раздела 2
    # Создайте thermal_rhs_task(t, T) с вашими параметрами P_in, R_th, C_th, T_amb
    def thermal_rhs_task(t, T):
        return (P_in - (T - T_amb) / R_th) / C_th

    T_euler = euler(thermal_rhs_task, t_task, T0)
    T_heun  = heun(thermal_rhs_task, t_task, T0)
    T_rk4   = rk4(thermal_rhs_task, t_task, T0)

    # TODO: solve_ivp с методом RK45
    sol_ivp = solve_ivp(thermal_rhs_task, [0, t_end_task], [T0], t_eval=t_task, method="RK45")
    T_ivp = sol_ivp.y[0]

    # TODO: постройте графики решений и ошибок
    err_euler = np.abs(T_euler - T_analytical)
    err_heun  = np.abs(T_heun  - T_analytical)
    err_rk4   = np.abs(T_rk4   - T_analytical)
    err_ivp   = np.abs(T_ivp   - T_analytical)

    plt.figure(figsize=(10, 5))

    plt.subplot(1, 2, 1)
    plt.plot(t_task, T_analytical, '-', label='Analytical')
    plt.plot(t_task, T_euler, '--', label='Euler')
    plt.plot(t_task, T_heun, '--', label='Heun')
    plt.plot(t_task, T_rk4, '--', label='RK4')
    plt.plot(t_task, T_ivp, '--', label='RK45 (ivp)')
    plt.xlabel('t')
    plt.ylabel('T')
    plt.title('Решения')
    plt.legend()
    plt.grid()

    plt.subplot(1, 2, 2)
    plt.plot(t_task, err_euler, '--', label='Euler error')
    plt.plot(t_task, err_heun, '--', label='Heun error')
    plt.plot(t_task, err_rk4, '--', label='RK4 error')
    plt.plot(t_task, err_ivp, '--', label='RK45 error')
    plt.xlabel('t')
    plt.ylabel('Ошибка')
    plt.title('Ошибки')
    plt.legend()
    plt.grid()

    plt.tight_layout()
    plt.show()


    print(f"Параметры: P_in={P_in} Вт, τ={tau:.2f} с, t_end={t_end_task:.2f} с")

    print(f"T(t_end) — Эйлер, °C - {T_euler[-1]}")
    print(f"T(t_end) — RK4, °C - {T_rk4[-1]}")
    print(f"T(t_end) — аналитическое, °C - {T_analytical[-1]}")
    print(f"Макс. ошибка Эйлера, °C - {np.max(np.abs(T_euler - T_analytical))}")
    print(f"Макс. ошибка RK4, °C - {np.max(np.abs(T_rk4 - T_analytical))}")
    return


@app.cell(hide_code=True)
def _(mo):
    mo.md("""
    **Введите результаты задания 7.1:**
    """)
    return


@app.cell
def _(mo):
    inp_T_final_euler = mo.ui.number(
        label="T(t_end) — Эйлер, °C", start=0, stop=500, value=0.0, step=0.01)
    inp_T_final_rk4 = mo.ui.number(
        label="T(t_end) — RK4, °C", start=0, stop=500, value=0.0, step=0.01)
    inp_T_final_an = mo.ui.number(
        label="T(t_end) — аналитическое, °C", start=0, stop=500, value=0.0, step=0.01)
    inp_max_err_euler = mo.ui.number(
        label="Макс. ошибка Эйлера, °C", start=0, stop=100, value=0.0, step=0.001)
    inp_max_err_rk4 = mo.ui.number(
        label="Макс. ошибка RK4, °C", start=0, stop=10, value=0.0, step=0.0001)
    mo.vstack([
        inp_T_final_euler, inp_T_final_rk4, inp_T_final_an,
        inp_max_err_euler, inp_max_err_rk4,
    ])
    return (
        inp_T_final_an,
        inp_T_final_euler,
        inp_T_final_rk4,
        inp_max_err_euler,
        inp_max_err_rk4,
    )


@app.cell(hide_code=True)
def _(F0_individual, HDD_c, HDD_m, k_individual, mo, np):
    _zeta  = HDD_c / (2 * np.sqrt(HDD_m * k_individual))
    _x_inf = F0_individual / k_individual
    mo.md(rf"""
    ### Задание 7.2 — HDD: многошаговые методы и анализ переходного процесса

    Ваши параметры системы: $F_0 = {F0_individual}$ Н, $k = {k_individual}$ Н/м,
    $m = {HDD_m}$ кг, $c = {HDD_c}$ Н·с/м.

    Коэффициент демпфирования: $\zeta = {_zeta:.4f}$ — система
    {'**колебательная** (underdamped), ожидается перерегулирование' if _zeta < 1 else '**апериодическая** (overdamped), перерегулирования нет'}.

    Целевое смещение: $x^* = F_0/k = {_x_inf*1000:.2f}$ мм.

    **a)** Вычислите траекторию $x(t)$ методами AB4 и AB4-AM4.
    Используйте $N = 100$ равномерных узлов на $[0, 1]$ с.

    **b)** Определите характеристики переходного процесса:
    - **Перерегулирование** $\sigma = \dfrac{{x_{{\max}} - x^*}}{{x^*}} \cdot 100\%$
    - **Время установки** $t_s$ — первый момент, начиная с которого $|x(t) - x^*| \le 0.02\,x^*$ для всех последующих $t$

    **c)** Постройте фазовый портрет $(x, v)$ для обоих методов.

    **d)** Как изменятся $\sigma$ и $t_s$, если увеличить $c$ в 2 раза? Проверьте расчётом.
    """)
    return


@app.cell
def _(
    F0_individual,
    HDD_c,
    HDD_m,
    HDD_t_end,
    HDD_v0,
    HDD_x0,
    adams_bashforth4_system,
    adams_moulton4_system,
    k_individual,
    np,
    plt,
):
    # ── Задание 7.2: впишите ваш код здесь ───────────────────────────────────────
    # Параметры (НЕ МЕНЯТЬ: F0 и k — индивидуальные, m и c — общие)
    N_hdd      = 2000
    t_hdd_task = np.linspace(0, HDD_t_end, N_hdd)
    y0_hdd     = np.array([HDD_x0, HDD_v0])
    x_star     = F0_individual / k_individual

    # TODO: определите hdd_rhs_task(t, y) с вашими F0_individual, k_individual
    def hdd_rhs_task(t, y):
        x, v = y
        return [v, (F0_individual - HDD_c * v - k_individual * x) / HDD_m]

    # TODO: вычислите траектории
    y_ab4_task = adams_bashforth4_system(hdd_rhs_task, t_hdd_task, y0_hdd)
    y_am4_task = adams_moulton4_system(  hdd_rhs_task, t_hdd_task, y0_hdd)

    # TODO: вычислите sigma и t_s по траектории AB4
    x_ab4 = y_ab4_task[:, 0]
    x_max = np.max(x_ab4)
    sigma = (x_max - x_star) / x_star * 100

    tol = 0.02 * x_star

    mask = np.abs(x_ab4 - x_star) <= tol
    t_s = t_hdd_task[-1]

    for i in range(len(mask)):
        if np.all(mask[i:]):
            t_s = t_hdd_task[i]
            break

    # TODO: фазовый портрет (x vs v) для AB4 и AB4-AM4
    plt.figure()
    plt.plot(y_ab4_task[:,0], y_ab4_task[:,1], label="AB4")
    plt.plot(y_am4_task[:,0], y_am4_task[:,1], '--', label="AB4-AM4")
    plt.xlabel("x")
    plt.ylabel("v")
    plt.legend()
    plt.grid()

    plt.tight_layout()
    plt.show()

    # TODO: пункт (d): повторите расчёт с c_double = 2 * HDD_c
    c_double = 2 * HDD_c

    def hdd_rhs_task_c(t, y):
        x, v = y
        return np.array([v, (F0_individual - c_double * v - k_individual * x) / HDD_m])


    y_ab4_task_c = adams_bashforth4_system(hdd_rhs_task_c, t_hdd_task, y0_hdd)
    y_am4_task_c = adams_moulton4_system(hdd_rhs_task_c, t_hdd_task, y0_hdd)


    x_ab4_c = y_ab4_task_c[:, 0]
    x_max_c = np.max(x_ab4_c)
    sigma_c = (x_max_c - x_star) / x_star * 100

    mask_c = np.abs(x_ab4_c - x_star) <= tol
    t_s_c = t_hdd_task[-1]

    for i in range(len(mask_c)):
        if np.all(mask_c[i:]):
            t_s_c = t_hdd_task[i]
            break

    plt.figure()
    plt.plot(y_ab4_task_c[:,0], y_ab4_task_c[:,1], label="AB4")
    plt.plot(y_am4_task_c[:,0], y_am4_task_c[:,1], '--', label="AB4-AM4")
    plt.xlabel("x")
    plt.ylabel("v")
    plt.legend()
    plt.grid()

    plt.tight_layout()
    plt.show()


    print(f"Целевое смещение x* = {x_star*1000:.3f} мм")

    print(f"Перерегулирование σ (AB4), % = {sigma:.4f}")
    print(f"Время установки t_s (AB4), с = {t_s:.6f}")
    print(f"x(t_end) — AB4, мм = {x_ab4[-1]*1000:.4f}")
    print(f"σ при c×2, % = {sigma_c:.4f}")
    print(f"t_s при c×2, с = {t_s_c:.6f}")
    return (N_hdd,)


@app.cell(hide_code=True)
def _(mo):
    mo.md("""
    **Введите результаты задания 7.2:**
    """)
    return


@app.cell
def _(mo):
    inp_sigma = mo.ui.number(
        label="Перерегулирование σ (AB4), %", start=0, stop=200, value=0.0, step=0.01)
    inp_ts = mo.ui.number(
        label="Время установки t_s ±2% (AB4), с", start=0, stop=5, value=0.0, step=0.001)
    inp_x_final_ab4 = mo.ui.number(
        label="x(t_end) — AB4, мм", start=-100, stop=100, value=0.0, step=0.001)
    inp_sigma_2c = mo.ui.number(
        label="σ при c×2 (пункт d), %", start=0, stop=200, value=0.0, step=0.01)
    inp_ts_2c = mo.ui.number(
        label="t_s при c×2 (пункт d), с", start=0, stop=5, value=0.0, step=0.001)
    mo.vstack([inp_sigma, inp_ts, inp_x_final_ab4, inp_sigma_2c, inp_ts_2c])
    return inp_sigma, inp_sigma_2c, inp_ts, inp_ts_2c, inp_x_final_ab4


@app.cell(hide_code=True)
def _(mo):
    mo.md(r"""
    ### Задание 7.3 — Вопросы для размышления

    Ответьте кратко в полях ниже (ключевые слова или 1–2 предложения).

    **Вопрос 1.** Почему AB4 нельзя запустить «с нуля»? Почему для инициализации
    первых шагов используется RK4, а не Эйлер?

    **Вопрос 2.** В чём преимущество схемы предиктор-корректор AB4-AM4 перед чисто явным AB4?

    **Вопрос 3.** Адаптивный `RK45` уменьшает шаг вблизи $t = 0$ для тепловой задачи.
    Почему именно там? *(Подсказка: вычислите $T'(0)$.)*
    """)
    return


@app.cell
def _(mo):
    ans_q1 = mo.ui.text(
        label="Ответ на вопрос 1:",
        placeholder="предыстория, 4 шага, порядок инициализации...",
        full_width=True,
    )
    ans_q2 = mo.ui.text(
        label="Ответ на вопрос 2:",
        placeholder="неявный корректор, уменьшение ошибки предиктора...",
        full_width=True,
    )
    ans_q3 = mo.ui.text(
        label="Ответ на вопрос 3:",
        placeholder="максимальная производная, быстрое изменение решения...",
        full_width=True,
    )
    mo.vstack([ans_q1, ans_q2, ans_q3])
    return ans_q1, ans_q2, ans_q3


@app.cell(hide_code=True)
def _(mo):
    mo.md(r"""
    ### Экспорт результатов

    Нажмите кнопку ниже — файл `pw12_<your_id>.json` нужно загрузить в LMS.

    > Числовые значения проверяются автоматически относительно эталонного решения
    > для вашего варианта. Скопированные или сгенерированные без расчёта значения
    > будут обнаружены при кросс-проверке.
    """)
    return


@app.cell
def _(
    F0_individual,
    N_hdd,
    P_in_individual,
    ans_q1,
    ans_q2,
    ans_q3,
    inp_T_final_an,
    inp_T_final_euler,
    inp_T_final_rk4,
    inp_max_err_euler,
    inp_max_err_rk4,
    inp_sigma,
    inp_sigma_2c,
    inp_ts,
    inp_ts_2c,
    inp_x_final_ab4,
    k_individual,
    mo,
    student_id,
):
    import json

    _results = {
        "student_id": student_id,
        "params": {
            "P_in":  P_in_individual,
            "F0":    F0_individual,
            "k":     k_individual,
        },
        "task1": {
            "T_final_euler":      inp_T_final_euler.value,
            "T_final_rk4":        inp_T_final_rk4.value,
            "T_final_analytical": inp_T_final_an.value,
            "max_error_euler":    inp_max_err_euler.value,
            "max_error_rk4":      inp_max_err_rk4.value,
        },
        "task2": {
            "N_hdd": N_hdd,
            "overshoot_pct":      inp_sigma.value,
            "settling_time_s":    inp_ts.value,
            "x_final_ab4_mm":     inp_x_final_ab4.value,
            "overshoot_pct_2c":   inp_sigma_2c.value,
            "settling_time_s_2c": inp_ts_2c.value,
        },
        "reflection": {
            "q1": ans_q1.value,
            "q2": ans_q2.value,
            "q3": ans_q3.value,
        },
    }

    _json_bytes = json.dumps(_results, ensure_ascii=False, indent=2).encode("utf-8")
    _filename   = f"pw12_{student_id}.json"

    mo.download(_json_bytes, filename=_filename, label=f"⬇ Скачать результаты ({_filename})")
    return


@app.cell(hide_code=True)
def _(mo):
    mo.md("""
    ---
    ## 🔑 Эталонные ответы (только для преподавателя)

    > **Удалить этот раздел и ячейку ниже перед выдачей студентам.**

    Функция `generate_reference(sid)` вычисляет правильные ответы для любого `student_id`.
    Используется для автоматической проверки сданных JSON-файлов.
    """)
    return


@app.cell
def _(np):
    from scipy.integrate import solve_ivp as _solve_ivp

    def generate_reference(sid: int) -> dict:
        """
        Вычисляет эталонные числовые ответы для студента с номером sid.
        Возвращает словарь той же структуры, что и студенческий JSON.
        """
        # ── Параметры (должны совпадать с блоком 0) ───────────────────────────────
        _P_values  = np.arange(10.0, 30.0, 0.5)
        _F0_values = np.arange(0.30, 0.80, 0.025)
        _k_values  = np.arange(30.0, 80.0, 2.5)

        P_in = float(_P_values[sid % len(_P_values)])
        F0   = float(_F0_values[sid % len(_F0_values)])
        k    = float(_k_values[(sid * 7) % len(_k_values)])

        # ── Задача 1: тепловая модель ─────────────────────────────────────────────
        R_th, C_th, T_amb, T0 = 5.0, 2.5, 25.0, 25.0
        tau      = R_th * C_th
        t_end    = 5 * tau
        N        = 30
        t_nodes  = np.linspace(0, t_end, N)

        def _f_thermal(t, T):
            return (P_in - (T - T_amb) / R_th) / C_th

        # Аналитическое
        def _T_an(t):
            return T_amb + P_in * R_th * (1 - np.exp(-t / tau)) + (T0 - T_amb) * np.exp(-t / tau)

        T_analytical = _T_an(t_nodes)

        # Эйлер
        T_euler = np.zeros(N); T_euler[0] = T0
        for i in range(1, N):
            h = t_nodes[i] - t_nodes[i-1]
            T_euler[i] = T_euler[i-1] + h * _f_thermal(t_nodes[i-1], T_euler[i-1])

        # RK4
        T_rk4 = np.zeros(N); T_rk4[0] = T0
        for i in range(1, N):
            h  = t_nodes[i] - t_nodes[i-1]
            k1 = _f_thermal(t_nodes[i-1],       T_rk4[i-1])
            k2 = _f_thermal(t_nodes[i-1] + h/2, T_rk4[i-1] + h/2*k1)
            k3 = _f_thermal(t_nodes[i-1] + h/2, T_rk4[i-1] + h/2*k2)
            k4 = _f_thermal(t_nodes[i],          T_rk4[i-1] + h*k3)
            T_rk4[i] = T_rk4[i-1] + (h/6)*(k1 + 2*k2 + 2*k3 + k4)

        # ── Задача 2: HDD ─────────────────────────────────────────────────────────
        m, c = 5e-3, 0.1
        x_star = F0 / k
        N_hdd  = 100
        t_hdd  = np.linspace(0, 1.0, N_hdd)

        def _hdd(t, y):
            x, v = y
            return [v, (F0 - c * v - k * x) / m]

        def _hdd_2c(t, y):
            x, v = y
            return [v, (F0 - 2*c * v - k * x) / m]

        def _run_ab4(f, t, y0):
            n = len(t); d = len(y0)
            y = np.zeros((n, d)); y[0] = y0
            for i in range(1, min(4, n)):
                h  = t[i] - t[i-1]
                k1 = np.array(f(t[i-1],       y[i-1]))
                k2 = np.array(f(t[i-1]+h/2,   y[i-1]+h/2*k1))
                k3 = np.array(f(t[i-1]+h/2,   y[i-1]+h/2*k2))
                k4 = np.array(f(t[i],          y[i-1]+h*k3))
                y[i] = y[i-1] + (h/6)*(k1+2*k2+2*k3+k4)
            for i in range(4, n):
                h   = t[i] - t[i-1]
                fn  = np.array(f(t[i-1], y[i-1]))
                fn1 = np.array(f(t[i-2], y[i-2]))
                fn2 = np.array(f(t[i-3], y[i-3]))
                fn3 = np.array(f(t[i-4], y[i-4]))
                y[i] = y[i-1] + (h/24)*(55*fn - 59*fn1 + 37*fn2 - 9*fn3)
            return y

        def _settling_time(t, x, x_star, tol=0.02):
            """Первый момент, начиная с которого |x(t)-x*| ≤ tol*x* для всех последующих t."""
            band = tol * x_star
            for i in range(len(t) - 1, -1, -1):
                if abs(x[i] - x_star) > band:
                    return float(t[min(i + 1, len(t) - 1)])
            return float(t[0])

        y0_hdd = np.array([0.0, 0.0])
        y_ab4      = _run_ab4(_hdd,    t_hdd, y0_hdd)
        y_ab4_2c   = _run_ab4(_hdd_2c, t_hdd, y0_hdd)

        sigma      = (np.max(y_ab4[:, 0]) - x_star) / x_star * 100
        ts         = _settling_time(t_hdd, y_ab4[:, 0], x_star)
        sigma_2c   = (np.max(y_ab4_2c[:, 0]) - x_star) / x_star * 100
        ts_2c      = _settling_time(t_hdd, y_ab4_2c[:, 0], x_star)

        # ── Допуски для автопроверки ──────────────────────────────────────────────
        return {
            "student_id": sid,
            "params": {"P_in": P_in, "F0": F0, "k": k},
            "task1": {
                "T_final_euler":       round(float(T_euler[-1]), 4),
                "T_final_rk4":         round(float(T_rk4[-1]), 4),
                "T_final_analytical":  round(float(T_analytical[-1]), 4),
                "max_error_euler":     round(float(np.max(np.abs(T_euler - T_analytical))), 5),
                "max_error_rk4":       round(float(np.max(np.abs(T_rk4  - T_analytical))), 6),
                # Допуски: абсолютные (°C)
                "_tol_T_final":        0.5,    # ±0.5 °C для конечных значений
                "_tol_max_err_euler":  0.3,    # ±0.3 °C по максимальной ошибке
                "_tol_max_err_rk4":    1e-3,   # ±0.001 °C
            },
            "task2": {
                "N_hdd": N_hdd,
                "overshoot_pct":       round(sigma, 2),
                "settling_time_s":     round(ts, 4),
                "x_final_ab4_mm":      round(float(y_ab4[-1, 0]) * 1000, 4),
                "overshoot_pct_2c":    round(sigma_2c, 2),
                "settling_time_s_2c":  round(ts_2c, 4),
                # Допуски
                "_tol_sigma":          1.0,    # ±1%
                "_tol_ts":             0.02,   # ±20 мс
                "_tol_x_final_mm":     0.05,   # ±0.05 мм
            },
        }

    # ── Демонстрация для нескольких студентов ──────────────────────────────────
    import pandas as _pd

    _rows = []
    for _sid in [408097,
    464902,
    471658,
    332425,
    413731]:
        _ref = generate_reference(_sid)
        _rows.append({
            "sid":       _sid,
            "P_in":      _ref["params"]["P_in"],
            "F0":        _ref["params"]["F0"],
            "k":         _ref["params"]["k"],
            "T_an_end":  _ref["task1"]["T_final_analytical"],
            "err_euler": _ref["task1"]["max_error_euler"],
            "err_rk4":   _ref["task1"]["max_error_rk4"],
            "σ, %":      _ref["task2"]["overshoot_pct"],
            "t_s, с":    _ref["task2"]["settling_time_s"],
            "σ(2c), %":  _ref["task2"]["overshoot_pct_2c"],
        })

    _df = _pd.DataFrame(_rows).set_index("sid")
    print("Эталонные ответы для выборки студентов:\n")
    print(_df.to_string())
    return


@app.cell(hide_code=True)
def _(mo):
    mo.md(r"""
    ## Раздел 8* (дополнительно). diffrax — JAX-based решатели ОДУ

    `diffrax` (Patrick Kidger, 2022) — библиотека решателей ОДУ/СДУ/ДАУ на базе JAX.
    Преимущества перед SciPy:

    - Работает на GPU/TPU без изменений кода
    - Совместима с `jax.jit` (компиляция), `jax.grad` (дифференцирование через решатель)
    - Доступны методы: Tsit5, Dopri5 (аналог RK45), Kvaerno3 (жёсткие), ShARK и др.

    Используется в задачах нейронных ОДУ (Neural ODEs) и байесовской идентификации параметров.

    Установка: `pip install diffrax`
    """)
    return


@app.cell
def _(
    DEMO_C_th,
    DEMO_P_in,
    DEMO_R_th,
    DEMO_T0,
    DEMO_T_amb,
    DEMO_t_end,
    np,
    plt,
    thermal_analytical,
):
    try:
        import jax.numpy as jnp
        import diffrax

        def _thermal_diffrax(t, y, args):
            P, R, C, Ta = args
            return jnp.array([(P - (y[0] - Ta) / R) / C])

        _args = (DEMO_P_in, DEMO_R_th, DEMO_C_th, DEMO_T_amb)
        _y0   = jnp.array([float(DEMO_T0)])
        _t0, _t1 = 0.0, float(DEMO_t_end)

        _term    = diffrax.ODETerm(_thermal_diffrax)
        _solver  = diffrax.Tsit5()          # метод Цитоновиса 5-го порядка (аналог RK45, но точнее)
        _saveat  = diffrax.SaveAt(ts=jnp.linspace(_t0, _t1, 300))
        _stepctrl= diffrax.PIDController(rtol=1e-6, atol=1e-9)

        _sol_dfx = diffrax.diffeqsolve(
            _term, _solver,
            t0=_t0, t1=_t1, dt0=0.01,
            y0=_y0, args=_args,
            saveat=_saveat,
            stepsize_controller=_stepctrl,
        )

        _t_dfx = np.array(_sol_dfx.ts)
        _T_dfx = np.array(_sol_dfx.ys[:, 0])
        _T_an  = thermal_analytical(_t_dfx)

        _fig, _ax = plt.subplots(figsize=(9, 4))
        _ax.plot(_t_dfx, _T_an,  "k-",  lw=2,   label="Аналитическое")
        _ax.plot(_t_dfx, _T_dfx, "m--", lw=1.5, label="diffrax Tsit5")
        _ax.set_xlabel("t, с"); _ax.set_ylabel("T, °C")
        _ax.set_title(f"diffrax (Tsit5): макс. ошибка = {np.max(np.abs(_T_dfx - _T_an)):.2e} °C")
        _ax.legend(); _ax.grid(True, alpha=0.3)
        plt.tight_layout()
        plt.gca()

    except ImportError:
        print("diffrax не установлен. Установите командой: pip install diffrax jax")
        print("После установки перезапустите ноутбук.")
    return


@app.cell(hide_code=True)
def _(mo):
    mo.md(r"""
    ## Раздел 9. Дальнейшие упражнения

    1. **Жёсткость (stiffness):** Уменьшите $C_{th}$ до 0.01 Дж/К. Сравните число шагов
       RK45 и Radau. Объясните, почему RK45 резко увеличивает `nfev`.

    2. **Метод Милна:** Реализуйте метод Милна (4-й порядок, предиктор-корректор) и
       сравните с AB4-AM4 на задаче HDD.

    3. **Идентификация параметров:** Дан «зашумленный» сигнал температуры CPU.
       Оцените $R_{th}$ и $C_{th}$ методом наименьших квадратов, используя аналитическое решение
       как модель.

    4. **Система с нелинейным охлаждением:** Замените линейный теплообмен на закон Ньютона-Кирхгофа:
       $\frac{dT}{dt} = \frac{1}{C_{th}}\!\left(P_{in} - \varepsilon\,\sigma\,(T^4 - T_{amb}^4)\right)$.
       Есть ли аналитическое решение? Сравните методы.
    """)
    return


if __name__ == "__main__":
    app.run()
