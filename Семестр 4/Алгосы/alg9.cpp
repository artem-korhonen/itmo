#include <bits/stdc++.h>

using namespace std;

int main() {
    int n, k, p;
    cin >> n >> k >> p;

    vector<int> queue;
    for (int i = 0; i < p; ++i) {
        int a;
        cin >> a;
        queue.push_back(a);
    }

    vector<int> next_use(p);
    vector<int> last_use(n+1, p+1);

    for (int i = p - 1; i >= 0; i--) {
        next_use[i] = last_use[queue[i]];
        last_use[queue[i]] = i;
    }
    
    set<pair<int, int>> floor_set;
    vector<bool> on_floor(n+1, false);
    int answer = 0;

    for (int i = 0; i < p; i++) {
        int car = queue[i];

        if (on_floor[car]) {
            floor_set.erase({i, car});
            floor_set.insert({next_use[i], car});
        } else {
            answer++;

            if (floor_set.size() == k) {
                auto it = prev(floor_set.end());
                pair<int, int> x = *it;
                int removed_car = x.second;

                on_floor[removed_car] = false;
                floor_set.erase(it);
            }
            
            on_floor[car] = true;
            floor_set.insert({next_use[i], car});
        }
    }

    cout << answer;

    return 0;
}
