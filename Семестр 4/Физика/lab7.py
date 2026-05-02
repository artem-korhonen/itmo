import numpy as np
import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation

m1, m2 = 2, 2

p1_0 = np.array([0.0, 0.0])
p2_0 = np.array([8.0, 1.2])

v1_0 = np.array([9.0, 0.0])
v2_0 = np.array([-1.0, 0.0])

r1, r2 = 0.7, 0.7

dt = 0.02
t = 0


def compute_all(p1, p2, v1, v2, m1, m2, R):
    r = p1 - p2
    v = v1 - v2

    a = np.dot(v, v)
    b = 2 * np.dot(r, v)
    c = np.dot(r, r) - R**2

    D = b*b - 4*a*c

    if D < 0:
        return None

    t1 = (-b - np.sqrt(D)) / (2*a)
    t2 = (-b + np.sqrt(D)) / (2*a)

    times = [t for t in (t1, t2) if t > 0]
    if not times:
        return None

    t_coll = min(times)

    p1_coll = p1 + v1 * t_coll
    p2_coll = p2 + v2 * t_coll

    n = p1_coll - p2_coll
    n = n / np.linalg.norm(n)

    v_rel = v1 - v2
    v_rel_n = np.dot(v_rel, n)

    if v_rel_n > 0:
        return None

    v1_after = v1 - (2 * v_rel_n * m2 * n) / (m1 + m2)
    v2_after = v2 + (2 * v_rel_n * m1 * n) / (m1 + m2) 

    return t_coll, p1_coll, p2_coll, v1_after, v2_after


result = compute_all(p1_0, p2_0, v1_0, v2_0, m1, m2, r1 + r2)

if result is not None:
    t_coll, p1_coll, p2_coll, v1_after, v2_after = result
    print("Collision time:", t_coll)
else:
    t_coll = None


fig, ax = plt.subplots()
ax.set_xlim(-2, 12)
ax.set_ylim(-5, 5)

circle1 = plt.Circle(p1_0, r1, color='blue')
circle2 = plt.Circle(p2_0, r2, color='red')

ax.add_patch(circle1)
ax.add_patch(circle2)



def update(frame):
    global t

    t += dt

    if t_coll is not None and t < t_coll:
        p1 = p1_0 + v1_0 * t
        p2 = p2_0 + v2_0 * t
    elif t_coll is not None and t >= t_coll:
        time_after = t - t_coll
        p1 = p1_coll + v1_after * time_after
        p2 = p2_coll + v2_after * time_after
    else:
        p1 = p1_0 + v1_0 * t
        p2 = p2_0 + v2_0 * t

    circle1.center = p1
    circle2.center = p2

    return circle1, circle2


anim = FuncAnimation(fig, update, frames=400, interval=30)

plt.gca().set_aspect('equal')
plt.show()