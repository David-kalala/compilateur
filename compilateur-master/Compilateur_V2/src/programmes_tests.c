int main () {
    int i;
    for (i = 0; i < 3; i = i + 1) {
        debug 1;
    }
}

int main () {
    int a;
    a = 0;
    while (a < 4) {
        debug 1;
        a = a + 1;
    }
}

int addition(int aAdd, int bAdd) {
    int cAdd;
    cAdd = aAdd+bAdd;
    return cAdd;
}

int multiplication(int aMul, int bMul) {
    int cMul;
    cMul = aMul*bMul;
    return cMul;
}

int division(int aDiv, int bDiv) {
    int cDiv;
    cDiv = aDiv/bDiv;
    return cDiv;
}

int soustraction(int aSous, int bSous) {
    int cSous;
    cSous = aSous-bSous;
    return cSous;
}

int main () {
    debug addition(1,1);
    debug multiplication(2,3);
    debug division(10,5);
    debug soustraction(20,5);
}