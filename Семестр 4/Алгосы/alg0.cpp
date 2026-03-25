#include <bits/stdc++.h>

using namespace std;
int main() {
  int size;
  cin >> size;

  for (int i = 0; i < size; i++) {
    string word;
    cin >> word;

    int word_size = int(word.size());
    if (word_size % 2 == 0 &&
        word.substr(0, word_size / 2) == word.substr(word_size / 2, word_size - 1)) {
      cout << "YES" << endl;
    } else {
      cout << "NO" << endl;
    }
  }

  return 0;
}