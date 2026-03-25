import numpy as np
import matplotlib.pyplot as plt
from tqdm import trange

k = [
    [0, 0, 0, 0.16, 0, 0],
    [0.85, 0.04, -0.04, 0.85, 0, 1.6],
    [0.2, -0.26, 0.23, 0.22, 0, 1.6],
    [-0.15, 0.28, 0.26, 0.24, 0, 0.44],
]


def f(x, y, a, b, c, d, e, f):
    return np.dot(np.array([[a, b], [c, d]]), np.array([x, y])) + np.array([e, f])


n = 2_000_000
x, y = 0, 0
xs = np.empty(n, dtype=np.float32)
ys = np.empty(n, dtype=np.float32)

for i in trange(n):
    p = np.random.random()

    if p < 0.01:
        x, y = f(x, y, *k[0])
    elif p < 0.86:
        x, y = f(x, y, *k[1])
    elif p < 0.93:
        x, y = f(x, y, *k[2])
    else:
        x, y = f(x, y, *k[3])

    xs[i] = x
    ys[i] = y


plt.figure(figsize=(6, 10))
plt.scatter(xs, ys, s=20, color="green")
plt.axis("off")
plt.show()
