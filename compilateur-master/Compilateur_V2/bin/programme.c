int main () {
    int t;
    int i;
    i = 3;
    *(t + 0) = i;
    debug t[0];
    *(t + 8) = 4;
    debug t[1];
}