#include <bits/stdc++.h>

using namespace std;

struct Entity {
    int id;
    bool animal;
    char letter;
};

int main() {
    string word;
    cin >> word;

    stack<Entity> entities;
    vector<pair<int, int>> answers; 

    int animal_id = 1;
    int trap_id = 1;

    for (char i : word) {
        bool animal = (tolower(i) == i);
        int id = animal ? animal_id : trap_id;
        char letter = tolower(i);
        Entity entity = {id, animal, letter};

        if (animal) {animal_id += 1;} else {trap_id += 1;}
        
        if (entities.empty()) {
            entities.push(entity);
            continue;
        }

        Entity entity2 = entities.top();

        if (entity.letter == entity2.letter && entity.animal != entity2.animal) {
            entities.pop();
            if (entity.animal) {
                answers.push_back({entity2.id, entity.id});
            } else {
                answers.push_back({entity.id, entity2.id});
            }
            continue;
        }

        entities.push(entity);
    }

    sort(answers.begin(), answers.end(), [] (auto a, auto b) {return a.first < b.first;});

    if (entities.empty()) {
        cout << "Possible" << endl;
        for (auto answer: answers) {
            cout << answer.second << " ";
        }
    } else {
        cout << "Impossible" << endl;
    }
    

    return 0;
}