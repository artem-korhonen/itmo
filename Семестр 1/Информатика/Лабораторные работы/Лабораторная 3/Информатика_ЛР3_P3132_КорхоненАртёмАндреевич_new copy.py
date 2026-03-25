import re


def exercise1(s):
    print(len(re.findall("X-{P", s)))


def exercise2(s):
    a = []

    for i in range(5):
        m = fr"\bВТ\b(?:\W*\b\w+\b){{{i}}}\s\bИТМО\b"
        s1 = s

        for _ in range(len(re.findall(r"\bВТ\b", s))):
            j = i + 1
            s2 = re.search(fr"\bВТ\b(?:\W*\b\w+\b){{{j}}}", s1)
            if s2 != None:
                if re.search(m, s2.group()) != None:
                    a.append(re.search(m, s2.group()).group())
                s1 = re.sub(r"\bВТ\b", "", s1, 1)
            else:
                break

    for i in a:
        c = " ".join(re.split(r"\W+", i))
        print(c)


def exercise3(s):
    m = r"\bBitcoin\b.[^>]*?₽.[^>]*?RUB"

    s1 = re.findall(m, s)
    m1 = r"₽.*?\s"

    print(re.findall(m1, s1[0])[0][1:-1])


exercise = int(input("Введите номер задания\n> "))

match exercise:
    case 1:
        exercise1("X-{PX-{PX-{PDPDPDX-PX-{PX-{P") # 5
        exercise1("X-{PX-{PX--{PX-{PX-{PX-{PX-{PX|-{PX-{PX-{PX-{PX-{PX|-{P") # 10
        exercise1("X-{PX-{PX-{PX-{P=-=-=-=-=X-{P=-X-{P=") # 6
        exercise1("XXXXXX-{PPPPPPPP-{XXXXXXXX-{PPPPPPPPP") # 2
        exercise1("PXPXPXPXPXPX--{-{X-{X-{X-{P") # 1
        exercise1(input())
    case 2:
        exercise2("ВТ ВТ УЖЕ ВНУТРИ ВТ ИТМО")
        # ВТ ВТ УЖЕ ВНУТРИ ВТ ИТМО
        # ВТ УЖЕ ВНУТРИ ВТ ИТМО
        # ВТ ИТМО
        print("________")
        exercise2("ВТ - ЭТО ВТ ИТМО ВТ ВТ ИТМО ОО ВТ ИТМО")
        # ВТ ЭТО ВТ ИТМО
        # ВТ ИТМО
        # ВТ ИТМО ВТ ВТ ИТМО
        # ВТ ВТ ИТМО
        # ВТ ИТМО
        # ВТ ВТ ИТМО ОО ВТ ИТМО
        # ВТ ИТМО ОО ВТ ИТМО
        # ВТ ИТМО
        print("________")
        exercise2("ВТ ВТ ВТ ВТ - ИТМО")
        # ВТ ВТ ВТ ВТ ИТМО
        # ВТ ВТ ВТ ИТМО
        # ВТ ВТ ИТМО
        # ВТ ИТМО
        print("________")
        exercise2("ВТ 2 3 4 ИТМО ВТ -=+-=-=-= ... \\\\\\////// ИТМО")
        # ВТ 2 3 4 ИТМО
        # ВТ ИТМО
        print("________")
        exercise2("ВТ В Т И ТМО ИТМО")
        # ВТ В Т И ТМО ИТМО
        print("________")
        exercise2("ВТ ВТ ВТ ИТМО ИТМО ИТМО")
        # ВТ ВТ ВТ ИТМО ИТМО ИТМО
        # ВТ ВТ ВТ ИТМО ИТМО
        # ВТ ВТ ВТ ИТМО
        # ВТ ВТ ИТМО ИТМО ИТМО
        # ВТ ВТ ИТМО ИТМО
        # ВТ ВТ ИТМО
        # ВТ ИТМО ИТМО ИТМО
        # ВТ ИТМО ИТМО
        # ВТ ИТМО
        print("________")
        exercise2(input())
    case 3:
        exercise3('<meta name="daily_volume" content="Общий объем торгов составил ₽3,945,026,450,860.12 RUB."/><meta name="daily_price" content="Обновление курса BTC к RUB производится в реальном времени."/><meta name="daily_price" content="Цена Bitcoin в данный момент составляет ₽6,123,456.78 RUB."/> <meta name="daily_price" content="Ethereum стоит около ₽187,456.23 RUB."/>')
        # 6,123,456.78
        exercise3('<meta name="daily_volume" content="В суточном объеме торгов ₽1,234,567,890,123.45 RUB."/><meta name="daily_price" content="Мы обновляем курс Bitcoin к RUB."/> <meta name="daily_price" content="Текущая цена Bitcoin составляет ₽4,876,543.21 RUB."/><meta name="daily_price" content="Ethereum оценен в ₽210,789.99 RUB."/>')
        # 4,876,543.21
        exercise3('<meta name="daily_volume" content="Суточный объем торгов ₽2,222,222,222,222.22 RUB."/> <meta name="daily_price" content="Курс Bitcoin обновляется каждые 5 минут."/><meta name="daily_price" content="Стоимость Ethereum сейчас ₽300,100.25 RUB."/><meta name="daily_price" content="Сегодняшняя цена Bitcoin равна ₽5,555,555.55 RUB."/>')
        # 5,555,555.55
        exercise3('<meta name="daily_volume" content="Объем торгов за сутки достиг ₽7,654,321,987,654.32 RUB."/><meta name="daily_price" content="Реальная стоимость Bitcoin к рублю составляет ₽8,888,888.88 RUB."/><meta name="daily_price" content="Ethereum продается за ₽180,123.45 RUB."/>')
        # 8,888,888.88
        exercise3('<meta name="daily_volume" content="Объем суточных торгов достиг ₽5,432,100,000,123.56 RUB."/><meta name="daily_price" content="Стоимость Bitcoin в данный момент составляет ₽6,765,432.10 RUB."/><meta name="daily_price" content="Ethereum оценивается в ₽200,000.50 RUB."/>')
        # 6,765,432.10
        exercise3(input())
