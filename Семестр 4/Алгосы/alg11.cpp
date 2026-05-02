#include <bits/stdc++.h>

using namespace std;


void add_block(set<pair<long long, long long>>& free_cells,
               map<long long, long long>& blocks,
               long long start,
               long long size) {
    long long new_start = start;
    long long new_size = size;
    long long new_end = start + size;

    auto right_it = blocks.find(new_end);
    
    if (right_it != blocks.end()) {
        auto right_block = *right_it;
        long long right_start = right_block.first;
        long long right_size = right_block.second;

        free_cells.erase({-right_size, right_start});
        new_size += right_size;
        blocks.erase(right_it);
    }

    auto it = blocks.lower_bound(new_start);

    if (it != blocks.begin()) {
        --it;
        auto left_block = *it;
        long long left_start = left_block.first;
        long long left_size = left_block.second;
        long long left_end = left_start + left_size;

        if (left_end == new_start) {
            free_cells.erase({-left_size, left_start});
            new_start = left_start;
            new_size += left_size;
            blocks.erase(it);
        }
    }

    blocks[new_start] = new_size;
    free_cells.insert({-new_size, new_start});
}


int main() {
    long long n;
    int m;
    cin >> n >> m;
    
    set<pair<long long, long long>> free_cells; // -размер, начало
    vector<pair<long long, long long>> requests; // начало, размер
    map<long long, long long> blocks; // начало -> размер
    
    free_cells.insert({-n, 1}); 
    blocks[1] = n;
    requests.push_back({0, 0});
    
    for (int i = 1; i <= m; i++) {
        long long request;
        cin >> request;
        
        if (request > 0) {
            if (free_cells.empty()) {
                cout << -1 << endl;
                requests.push_back({0, 0});
                continue;
            }

            auto it = free_cells.begin();
            auto cell = *it;
            long long max_size = -cell.first;
            long long start = cell.second;
            
            if (request > max_size) {
                cout << -1 << endl;
                requests.push_back({0, 0});
            } else {
                cout << start << endl;
                requests.push_back({start, request});
                
                free_cells.erase(it);
                blocks.erase(start);
                
                if (max_size > request) {
                    long long new_start = start + request;
                    long long new_size = max_size - request;
                    free_cells.insert({-new_size, new_start});
                    blocks[new_start] = new_size;
                }
            }
        } else {
            int id = -request;
            auto [start, size] = requests[id];
            
            if (size == 0) {
                requests.push_back({0, 0});
                continue;
            }
            
            add_block(free_cells, blocks, start, size);
            
            requests[id] = {0, 0};
            requests.push_back({0, 0});
        }
    }
    
    return 0;
}