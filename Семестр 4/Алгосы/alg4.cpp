#include <bits/stdc++.h>

using namespace std;

int main() {
    int a, b, c, d, k;
    cin >> a >> b >> c >> d >> k;

    int last = a;
    int last_last = 0;

    for (int i = 1; i <= k; i++) {
        last_last = last;
        last *= b;
        last -= c;

        if (last <= 0) {
            last = 0;
            break;
        }

        last = min(last, d);
        
        if (last_last == last) {
            break;
        }
    }

    cout << last << endl;
}