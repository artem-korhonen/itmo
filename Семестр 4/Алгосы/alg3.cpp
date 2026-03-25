#include <bits/stdc++.h>

using namespace std;

int main() {
    map<string, int> main_dict;
    stack<map<string, int>> changes_stack;

    string now;
    while (cin >> now) {
        if (now == "}") {
            if (!changes_stack.empty()) {
                map<string, int> changes_dict = changes_stack.top();
                changes_stack.pop();

                for (auto &p: changes_dict) {
                    main_dict[p.first] = p.second;
                }
            }
        } else if (now == "{") {
            map<string, int> changes_dict;
            changes_stack.push(changes_dict);
        } else {
            size_t equals_pos = now.find("=");
            string part1 = now.substr(0,  equals_pos);
            string part2 = now.substr(equals_pos + 1);

            if (!changes_stack.empty()) {
                map<string, int> &changes_dict = changes_stack.top();
                
                if (!changes_dict.count(part1)) {
                    changes_dict[part1] = main_dict[part1];
                }
            }

            try {
                int part2_int = stoi(part2);
                main_dict[part1] = part2_int;
            } catch (...) {
                main_dict[part1] = main_dict[part2];
                cout << main_dict[part1] << endl;
            }
        }
    }

    return 0;
}