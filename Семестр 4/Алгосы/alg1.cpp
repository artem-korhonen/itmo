#include <bits/stdc++.h>

using namespace std;
int main() {
    int size;
    cin >> size;
    vector<int> flowers;

    for (int i = 0; i < size; i++) {
        int a;
        cin >> a;
        flowers.push_back(a);
    }

    int left = 0;
    int answer[2] = {0, 0};

    for (int right = 0; right < size; right++) {
        if (right >= 2 && flowers[right] == flowers[right-1] && flowers[right] == flowers[right-2]) {
            left = right - 1;
        }

        if (right - left > answer[1] - answer[0]) {
            answer[0] = left;
            answer[1] = right;
        }
    }

    for (int i : answer) {
        cout << i+1 << " ";
    }

    return 0;
}