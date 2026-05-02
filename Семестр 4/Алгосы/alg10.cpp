#include <bits/stdc++.h>

using namespace std;

int main() {
    int n;
    cin >> n;

    deque<int> left, right;
    
    for (int i = 0; i < n; i++) {
        char symbol;
        cin >> symbol;
        
        if (symbol == '-') {
            cout << left.front() << endl;
            left.pop_front();
        } else {
            int goblin;
            cin >> goblin;
            
            if (symbol == '+') {
                right.push_back(goblin);
            } else {
                right.push_front(goblin);
            }
        }

        if (left.size() < right.size()) {
            left.push_back(right.front());
            right.pop_front();
        } else if (left.size() > right.size() + 1) {
            right.push_front(left.back());
            left.pop_back();
        }
    }

    return 0;
}
