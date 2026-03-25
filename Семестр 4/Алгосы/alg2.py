word = input()
stack: list = []
answer = []

animal_count = 1
trap_count = 1

for i in word:
    animal = i.lower() == i
    
    if (animal):
        entity = [animal_count, animal, i]
        animal_count += 1
    else:
        entity = [trap_count, animal, i.lower()]
        trap_count += 1
    
    if (len(stack) == 0):
        stack.append(entity)
    elif (stack[-1][1] != entity[1] and stack[-1][2] == entity[2]):
        entity2 = stack.pop()
        if (entity[1]):
            answer.append([entity2[0], entity[0]])
        else:
            answer.append([entity[0], entity2[0]])
    else:
        stack.append(entity)

if (len(stack) != 0):
    print("Impossible")
else:
    print("Possible")
    for i in sorted(answer):
        print(i[1])
