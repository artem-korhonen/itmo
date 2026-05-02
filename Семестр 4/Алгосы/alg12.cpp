#include <bits/stdc++.h>

using namespace std;

int main() {
    int n, k;
    cin >> n >> k;

    vector<int> nums;
    for (int i = 0; i < n; i++) {
        int a;
        cin >> a;
        nums.push_back(a);
    }

    deque<int> ids;

    for (int i = 0; i < n; i++) {
        if (!ids.empty() && ids.front() <= i - k) {
            ids.pop_front();
        }

        while (!ids.empty() && nums[ids.back()] >= nums[i]) {
            ids.pop_back();
        }

        ids.push_back(i);

        if (i >= k - 1) {
            cout << nums[ids.front()] << endl;
        }
    }

    return 0;
}
