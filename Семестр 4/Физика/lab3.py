import math
import matplotlib.pyplot as plt

g = 9.81


def euler_drag(V0, alpha, k, m, tau, t_explosion=None):
    x, y = 0, 0
    Vx = V0 * math.cos(alpha)
    Vy = V0 * math.sin(alpha)
    t = 0

    x_vals = []
    y_vals = []

    while y >= 0:
        if not t_explosion is None:
            if t >= t_explosion:
                break
        x_vals.append(x)
        y_vals.append(y)

        ax = -(k/m) * Vx
        ay = -(k/m) * Vy - g

        x += Vx * tau
        y += Vy * tau

        Vx += ax * tau
        Vy += ay * tau

        t += tau

        print(f"t={t}, x={x}, y={y}")
    return x_vals, y_vals


def bonus(V0, k, m, tau, t_explosion):
    best_alpha = 0
    best_distance = 0
    max_x_vals = []
    max_y_vals = []
    
    for alpha in range(0, 901):
        x_vals, y_vals = euler_drag(V0, math.radians(alpha / 10), k, m, tau, t_explosion)
        
        distance = math.sqrt(x_vals[-1]**2 * y_vals[-1]**2)
        if distance > best_distance:
            best_distance = distance
            best_alpha = alpha / 10
            max_x_vals = x_vals
            max_y_vals = y_vals
        
    return best_alpha, best_distance, max_x_vals, max_y_vals
    

task = {
    "V0": 20,
    "alpha": math.radians(60),
    "k": 1,
    "m": 1,
    "tau": 0.01
}

x_vals, y_vals = euler_drag(**task)

bonus_task = {
    "V0": 20,
    "k": 1,
    "m": 1,
    "tau": 0.01,
    "t_explosion": 0.1
}

best_angle, best_distance, max_x_vals, max_y_vals = bonus(**bonus_task)
print(f"Лучший угол - {best_angle}, лучшая дистанция от точки броска - {best_distance}")

plt.plot(x_vals, y_vals, label="task")
plt.plot(max_x_vals, max_y_vals, label="bonus")
plt.xlabel("x")
plt.ylabel("y")
plt.grid()
plt.show()

