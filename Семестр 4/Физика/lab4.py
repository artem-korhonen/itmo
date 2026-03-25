import numpy as np
import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation

G = 6.67 * 10**(-11)
m1 = 1 * 10**11
m2 = 10 * 10**11

x01, y01 = 20, 0
x02, y02 = 0, 0

v01x, v01y = 0, 2
v02x, v02y = 0, -0.2

dt = 0.1

r1 = np.array([x01, y01], dtype=float)
r2 = np.array([x02, y02], dtype=float)
v1 = np.array([v01x, v01y], dtype=float)
v2 = np.array([v02x, v02y], dtype=float)

hist1 = []
hist2 = []

fig, ax = plt.subplots(figsize=(20, 20))
ax.set_xlim(-40, 60)
ax.set_ylim(-40, 60)
ax.set_aspect('equal')
ax.grid(True)

point1, = ax.plot([], [], 'bo', markersize=10 if m1 > m2 else 5)
point2, = ax.plot([], [], 'ro', markersize=10 if m2 > m1 else 5)
line1, = ax.plot([], [], 'b-', alpha=0.3)
line2, = ax.plot([], [], 'r-', alpha=0.3)

def update(frame):
    global r1, r2, v1, v2
    
    dr = r2 - r1
    dist = np.linalg.norm(dr) 
    
    if dist < 0.2:
        return point1, point2, line1, line2

    f_mag = G * m1 * m2 / dist**2
    f_vec = f_mag * (dr / dist)
    
    a1 = f_vec / m1
    a2 = -f_vec / m2
    
    r1 += v1 * dt
    r2 += v2 * dt
    
    v1 += a1 * dt
    v2 += a2 * dt
    
    hist1.append(r1.copy())
    hist2.append(r2.copy())
    
    h1 = np.array(hist1)
    h2 = np.array(hist2)
    
    point1.set_data([r1[0]], [r1[1]])
    point2.set_data([r2[0]], [r2[1]])
    line1.set_data(h1[:, 0], h1[:, 1])
    line2.set_data(h2[:, 0], h2[:, 1])
    
    # point1.set_data([r1[0] - r2[0]], [r1[1] - r2[1]])
    # point2.set_data([0], [0])
    # line1.set_data(h1[:, 0] - r2[0], h1[:, 1] - r2[1])
    # line2.set_data(h2[:, 0] - r2[0], h2[:, 1] - r2[1])
    
    return point1, point2, line1, line2

ani = FuncAnimation(fig, update, frames=1000, interval=10, blit=True)
plt.show()
