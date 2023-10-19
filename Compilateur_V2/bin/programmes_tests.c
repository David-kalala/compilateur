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

int factorielle(int valeur)
{
   if (valeur == 0){
      return 1;
   }else{
      return valeur * factorielle(valeur - 1);
      }
}


int fibonacci(int n) {
    if (n <= 1) {
        return n;
    } else {
        return fibonacci(n - 1) + fibonacci(n - 2);
    }
}





int main () {
    int t;
    int i;
    i = 3;
    *(t + 0) = i;
    debug t[0];
    *(t + 8) = 4;
    debug t[1];
    RESULTAT ETRANGE
}


int main () {
    debug addition(1,1);
    debug multiplication(2,3);
    debug division(10,5);
    debug soustraction(20,5);
    debug factorielle(4); 
    debug factorielle(10);
    debug counter(10);
}

int main () {


 	int t;
 	t = malloc(5); 
    t[0] = 2;  
    t[1] = 4;
    t[2] = 5;
    t[3] = 6;
    t[4] = 8;
   
   	int b;
   	
	for(b = 0; b<5; b = b+1){debug t[b];}
}


