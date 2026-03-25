import numpy as np
import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation

G = 6.67e-11

# Данные: Солнце, Земля, Юпитер
m = np.array([1.98847e30, 5.9722e24, 1.9e27])
r = np.array([[0.0, 0.0], [149.6e9, 0.0], [778.6e9, 0.0]])
v = np.array([[0.0, 0.0], [0.0, 29780.0], [0.0, 13070.0]])

# Состояния и результаты
active = [True, True, True]  # Флаг: движется ли планета
periods = [0, 0, 0]          # Время одного оборота
elapsed_time = 0             # Общий таймер симуляции
dists = [[], [], []]

dt = 100000 
n = len(m)
history = [[] for _ in range(n)]

fig, ax = plt.subplots(figsize=(8, 8))
ax.set_xlim(-9e11, 9e11)
ax.set_ylim(-9e11, 9e11)
ax.set_aspect('equal')
ax.grid(True)

colors = ['yo', 'bo', 'ro']
points = [ax.plot([], [], colors[i], markersize=[12, 5, 7][i])[0] for i in range(n)]

def update(frame):
    global r, v, elapsed_time

    # Считаем ускорения (даже если планета "замерзла", она все еще притягивает других)
    a = np.zeros_like(r)
    for i in range(n):
        for j in range(n):
            if i == j: continue
            dr = r[j] - r[i]
            dist_val = np.linalg.norm(dr)
            if dist_val < 1e7: continue
            a[i] += G * m[j] * dr / dist_val**3

    # Физика и проверка условий
    elapsed_time += dt
    
    for i in range(n):
        if i == 0: continue # Солнце не считаем
        
        if active[i]:
            r_old_y = r[i][1] # Запоминаем Y до шага
            
            # Интеграция
            v[i] += a[i] * dt
            r[i] += v[i] * dt
            
            # Условие остановки: пересечение оси X снизу вверх
            # (Y был отрицательным, стал положительным, X при этом положительный)
            if r_old_y < 0 and r[i][1] >= 0 and r[i][0] > 0:
                active[i] = False
                periods[i] = elapsed_time
                print(f"--- {['Солнце', 'Земля', 'Юпитер'][i]} завершила круг! ---")
                print(f"Время: {periods[i] / (24*3600):.2f} земных суток")
                print(f"Максимальный радиус: {max[dists[i]]} м")

        # Запись данных
        dists[i].append(np.linalg.norm(r[i] - r[0]))
        points[i].set_data([r[i][0]], [r[i][1]])

    return points

# Увеличим количество кадров, чтобы Юпитер успел долететь
ani = FuncAnimation(fig, update, frames=4000, interval=1, blit=True)

plt.show()