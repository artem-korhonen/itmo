import numpy as np
import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation

G = 6.67e-11


class Object:
    def __init__(self, name, mass, r, v, color, size, active):
        self.name = name
        self.mass = mass
        self.r = np.array(r, dtype=float)
        self.v = np.array(v, dtype=float)
        self.color = color
        self.size = size
        self.active = active
        self.period = 0
        self.dists = []
        self.prev_y = self.r[1]
        
        self.traj_x = []
        self.traj_y = []


bodies = [
    Object("Солнце", 1.98847e30, [0, 0], [0, 0], 'yo', 12, False),

    Object("Меркурий", 3.3011e23, [57.9e9, 0], [0, 47400], 'ro', 3, True),
    Object("Венера", 4.8675e24, [108.2e9, 0], [0, 35020], 'ro', 4, True),
    Object("Земля", 5.9722e24, [149.6e9, 0], [0, 29780], 'ro', 5, True),
    Object("Марс", 6.4171e23, [227.9e9, 0], [0, 24070], 'ro', 4, True),

    Object("Юпитер", 1.898e27, [778.6e9, 0], [0, 13070], 'ro', 7, True),

    Object("Объект", 1.2e14, [400e9, 0], [0, 9000], 'go', 3, True),
]

dt = 100000
elapsed_time = 0

fig, ax = plt.subplots(figsize=(8, 8))
max_r = max(np.linalg.norm(body.r) for body in bodies)
ax.set_xlim(-1.2*max_r, 1.2*max_r)
ax.set_ylim(-1.2*max_r, 1.2*max_r)
ax.set_aspect('equal')
ax.grid(True)

points = [
    ax.plot([], [], body.color, markersize=body.size)[0]
    for body in bodies
]

lines = [
    ax.plot([], [], body.color.replace('o', '-'), linewidth=1)[0]
    for body in bodies
]

def update(frame):
    global elapsed_time
    
    n = len(bodies)
    a = [np.zeros(2) for _ in range(n)]

    for i in range(n):
        for j in range(n):
            if i == j:
                continue

            dr = bodies[j].r - bodies[i].r
            dist = np.linalg.norm(dr)

            if dist < 1e7:
                continue

            a[i] += G * bodies[j].mass * dr / dist**3

    elapsed_time += dt

    for i, body in enumerate(bodies):
        body.prev_y = body.r[1]

        body.v += a[i] * dt
        body.r += body.v * dt

        dist_to_sun = np.linalg.norm(body.r - bodies[0].r)
        body.dists.append(dist_to_sun)

        if body.prev_y < 0 and body.r[1] >= 0 and body.r[0] > 0 and body.active:
            body.active = False
            body.period = elapsed_time

            print(f"--- {body.name} завершила круг! ---")
            print(f"Время: {body.period / (24*3600):.2f} суток")

            if body.dists:
                r_max = max(body.dists)
                r_min = min(body.dists)
                a_orbit = (r_max + r_min) / 2

                print(f"Максимальный радиус: {r_max} м")
                print(f"t^2/a^3 = {body.period**2 / a_orbit**3}")

        points[i].set_data([body.r[0]], [body.r[1]])
        
        body.traj_x.append(body.r[0])
        body.traj_y.append(body.r[1])
        lines[i].set_data(body.traj_x, body.traj_y)

    return points + lines


ani = FuncAnimation(fig, update, frames=4000, interval=1, blit=True)

plt.show()