# Импорт необходимых библиотек
from datetime import date  # Для работы с датами, хотя в данном коде не используется
import plotly.graph_objs as go  # Для создания графиков с помощью Plotly
import plotly.express as px  # Для упрощенного создания графиков, не используется в данном коде
from plotly.subplots import make_subplots  # Для создания субплотов, не используется в данном коде

import numpy as np  # Для работы с массивами и математическими операциями, не используется
import pandas as pd  # Для работы с табличными данными
import csv  # Для работы с CSV файлами

# Открываем и читаем данные из файла CSV
with open('data.csv') as file:  
    # Создаем объект для чтения CSV
    reader = csv.reader(file, delimiter=",", quotechar='"')  
    next(reader, None)  # Пропускаем первую строку, которая содержит заголовки
    # Читаем все строки и сохраняем их в список data_read
    data_read = [row for row in reader]

# Инициализируем пустые списки для хранения данных по каждой категории (Open, High, Low, Close)
d_open  = [[], [] ,[], []]  # Список для данных по открытию
d_high  = [[], [] ,[], []]  # Список для максимальных цен
d_low   = [[], [] ,[], []]  # Список для минимальных цен
d_close = [[], [] ,[], []]  # Список для данных по закрытию

# Создаем словарь с маппингом дат на индексы
dates = { '25/09/18': 0, '23/10/18': 1, '23/11/18': 2, '03/12/18': 3 }
# Создаем обратный словарь для маппинга индексов на даты
inv_dates = {v: k for k, v in dates.items()}

# Заполняем данные для каждой категории (Open, High, Low, Close) по соответствующим датам
for raw in data_read:
    id = dates[raw[2]]  # Получаем индекс даты, которая находится в колонке с индексом 2
    # Добавляем данные о ценах в соответствующие списки
    d_open[id].append(float(raw[4]))  # Цена открытия
    d_high[id].append(float(raw[5]))  # Максимальная цена
    d_low[id].append(float(raw[6]))   # Минимальная цена
    d_close[id].append(float(raw[7])) # Цена закрытия

# Создаем пустой график
fig = go.Figure()

# Добавляем данные для каждой даты
for i in range(4):
    cur_date = inv_dates[i]  # Получаем дату по индексу
    # Строим графики для каждой из категорий (Open, High, Low, Close)
    # Для каждой категории создаем box-plot с соответствующими данными
    
    # Открытие
    n = cur_date + ' - Open'  # Название категории (например, '25/09/18 - Open')
    fig.add_trace(go.Box(y=pd.DataFrame(d_open[i], columns=[n])[n], name=n))  # Добавляем box-plot для открытия

    # Максимальная цена
    n = cur_date + ' - High'  # Название категории (например, '25/09/18 - High')
    fig.add_trace(go.Box(y=pd.DataFrame(d_high[i], columns=[n])[n], name=n))  # Добавляем box-plot для максимальной цены

    # Минимальная цена
    n = cur_date + ' - Low'  # Название категории (например, '25/09/18 - Low')
    fig.add_trace(go.Box(y=pd.DataFrame(d_low[i], columns=[n])[n], name=n))  # Добавляем box-plot для минимальной цены

    # Цена закрытия
    n = cur_date + ' - Close'  # Название категории (например, '25/09/18 - Close')
    fig.add_trace(go.Box(y=pd.DataFrame(d_close[i], columns=[n])[n], name=n))  # Добавляем box-plot для цены закрытия

# Настроим макет графика:
# Изменяем расположение легенды, делаем ее горизонтальной и поднимаем наверх
fig.update_layout(legend=dict(yanchor="top", orientation="h", y=1.2))

# Поворот меток на оси X на 90 градусов, чтобы они не перекрывались, и отступ от названия оси
fig.update_xaxes(tickangle=90, title_standoff=25)

# Отображаем график
fig.show()