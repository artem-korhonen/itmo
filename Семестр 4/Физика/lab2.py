import math

from matplotlib import pyplot as plt


g = 9.81


class Answer:
    def __init__(self, x, y, Vx, Vy):
        self.x = x
        self.y = y
        self.Vx = Vx
        self.Vy = Vy


def derivatives(x, y, Vx, Vy):
    return Vx, Vy, 0, -g


def euler(V0, alpha, x0, y0, tau):
    Vx = V0 * math.cos(alpha)
    Vy = V0 * math.sin(alpha)

    x = x0
    y = y0
    t = 0
    
    xs = [x]
    ys = [y]

    while y >= 0:
        new_x, new_y, new_Vx, new_Vy = derivatives(x, y, Vx, Vy)
        x += tau * new_x
        y += tau * new_y
        Vx += tau * new_Vx
        Vy += tau * new_Vy
        
        xs.append(x)
        ys.append(y)
        
        t += tau

    return Answer(x, y, Vx, Vy), xs, ys


def rk(V0, alpha, x0, y0, tau):
    Vx = V0 * math.cos(alpha)
    Vy = V0 * math.sin(alpha)

    x = x0
    y = y0
    t = 0
    
    xs = [x]
    ys = [y]

    while y >= 0:
        new_x1, new_y1, new_Vx1, new_Vy1 = derivatives(x, y, Vx, Vy)

        new_x2, new_y2, new_Vx2, new_Vy2 = derivatives(
            x + tau/2 * new_x1,
            y + tau/2 * new_y1,
            Vx + tau/2 * new_Vx1,
            Vy + tau/2 * new_Vy1
        )

        new_x3, new_y3, new_Vx3, new_Vy3 = derivatives(
            x + tau/2 * new_x2,
            y + tau/2 * new_y2,
            Vx + tau/2 * new_Vx2,
            Vy + tau/2 * new_Vy2
        )

        new_x4, new_y4, new_Vx4, new_Vy4 = derivatives(
            x + tau * new_x3,
            y + tau * new_y3,
            Vx + tau * new_Vx3,
            Vy + tau * new_Vy3
        )

        x += tau / 6 * (new_x1 + 2*new_x2 + 2*new_x3 + new_x4)
        y += tau / 6 * (new_y1 + 2*new_y2 + 2*new_y3 + new_y4)
        Vx += tau / 6 * (new_Vx1 + 2*new_Vx2 + 2*new_Vx3 + new_Vx4)
        Vy += tau / 6 * (new_Vy1 + 2*new_Vy2 + 2*new_Vy3 + new_Vy4)
        
        xs.append(x)
        ys.append(y)

        t += tau

    return Answer(x, y, Vx, Vy), xs, ys


def bonus(V0, x0, y0, tau):
    results = {}
    for alpha in range(0, 901):
        answer, _, _ = euler(V0, math.radians(alpha/10), x0, y0, tau)
        results[alpha] = answer.x

    max_result = max(results.values())

    for alpha in results.keys():
        if results[alpha] == max_result:
            return alpha/10


task = {
    "V0": 20,
    "alpha": math.radians(60),
    "x0": 0,
    "y0": 0,
    "tau": 0.1
}

euler_answer, xs_euler, ys_euler = euler(**task)
rk_answer, xs_rk, ys_rk = rk(**task)

print(
    f"Метод Эйлера:\nx={euler_answer.x},\ny={euler_answer.y},\nVx={euler_answer.Vx},\nVy={euler_answer.Vy}\n")
print(
    f"Метод Рунге-Кутта:\nx={rk_answer.x},\ny={rk_answer.y},\nVx={rk_answer.Vx},\nVy={rk_answer.Vy}\n")


plt.figure()
plt.plot(xs_euler, ys_euler, label="Метод Эйлера")
plt.plot(xs_rk, ys_rk, label="Метод Рунге-Кутта")

plt.xlabel("x")
plt.ylabel("y")
plt.title("Траектория движения тела")
plt.legend()
plt.grid()

plt.show()


bonus_task = {
    "V0": 20,
    "x0": 0,
    "y0": 0,
    "tau": 0.2
}
bonus_answer = bonus(**bonus_task)

print(
    f"При строгом tau={bonus_task["tau"]} угол, при котором достигается наибольшее расстояние x равен {bonus_answer}°")
